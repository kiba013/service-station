package com.example.servicestation.service;

import com.example.servicestation.domain.Request;
import com.example.servicestation.domain.enumeration.RequestStatusType;
import com.example.servicestation.errors.AccessDeniedException;
import com.example.servicestation.errors.ObjectNotFoundException;
import com.example.servicestation.kafka.event.RequestStatusEvent;
import com.example.servicestation.kafka.producer.KafkaRequestProducer;
import com.example.servicestation.repository.RequestRepository;
import com.example.servicestation.service.dto.AppUserDTO;
import com.example.servicestation.service.dto.RequestDTO;
import com.example.servicestation.service.dto.StatusHistoryDTO;
import com.example.servicestation.service.mapper.RequestMapper;
import com.example.servicestation.service.request.RequestStatusUpdateRequest;
import com.example.servicestation.service.specification.RequestSpecification;
import com.example.servicestation.util.StatusTransitionValidator;
import com.example.servicestation.util.UserUtils;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestService {

    private final UserUtils userUtils;
    private final RequestMapper requestMapper;
    private final RequestRepository requestRepository;
    private final StatusHistoryService statusHistoryService;
    private final KafkaRequestProducer kafkaRequestProducer;
    private final StatusTransitionValidator statusTransitionValidator;


    @Transactional
    public RequestDTO save(RequestDTO dto) {

        AppUserDTO currentUser = getCurrentUser();
        dto.setAppUser(currentUser);
        dto.setStatusType(RequestStatusType.NEW);
        dto.setCreatedAt(ZonedDateTime.now());

        Request entity = requestMapper.toEntity(dto);
        entity = requestRepository.save(entity);


        RequestDTO requestMapperDTO = requestMapper.toDTO(entity);
        statusHistoryService.save(buildStatusHistory(currentUser,
                requestMapperDTO,
                entity.getDescription(),
                RequestStatusType.NEW));

        log.info("Created request with ID= {} by USER={}", entity.getId(), currentUser.getName());

        return requestMapperDTO;
    }

    @Transactional
    public RequestDTO update(Long requestId, RequestStatusUpdateRequest updateRequest) {
        AppUserDTO currentUser = getCurrentUser();

        validateUserPermissions(currentUser);

        Request request = getRequestById(requestId);

        statusTransitionValidator.validateTransition(request.getStatusType(), updateRequest.getStatus());

        kafkaRequestProducer.sendStatusChangeEvent(
                new RequestStatusEvent(
                        requestId,
                        updateRequest.getStatus(),
                        updateRequest.getReason(),
                        currentUser
                )
        );

        return requestMapper.toDTO(request);
    }

    @Transactional(readOnly = true)
    public RequestDTO findOne(Long id) {
        log.info("Getting request with ID: {}", id);
        return requestRepository.findById(id)
                .map(requestMapper::toDTO)
                .orElseThrow(() -> new ObjectNotFoundException("Request not found with ID: " + id));
    }

    @Transactional(readOnly = true)
    public Page<RequestDTO> findAll(@Nullable Long clientId, @Nullable RequestStatusType statusType, Pageable pageable) {
        AppUserDTO currentUser = getCurrentUser();
        Long specClientId = currentUser.isNotClient() ? clientId : currentUser.getId();

        Specification<Request> specification = Specification
                .where(specClientId == null ? null : RequestSpecification.byClient(specClientId))
                .and(statusType == null ? null : RequestSpecification.withStatus(statusType));

        log.info("Getting Requests with status: {} for Client ID: {}",
                statusType != null ? statusType : "ALL",
                specClientId != null ? specClientId : "ALL CLIENTS");

        return requestRepository.findAll(specification, pageable)
                .map(requestMapper::toDTO);
    }

    @Transactional
    public void delete(Long id) {
        validateUserPermissions(getCurrentUser());
        getRequestById(id);
        requestRepository.deleteById(id);
        log.info("Request by ID: {} deleted successfully!", id);
    }

    private AppUserDTO getCurrentUser() {
        return userUtils.getCurrentUser();
    }

    private StatusHistoryDTO buildStatusHistory(AppUserDTO appUserDTO,
                                                RequestDTO requestDTO,
                                                String reason,
                                                RequestStatusType statusType) {
        return StatusHistoryDTO.builder()
                .request(requestDTO)
                .changedBy(appUserDTO)
                .newStatusType(statusType)
                .reason(reason)
                .createdAt(ZonedDateTime.now())
                .build();
    }


    private void validateUserPermissions(AppUserDTO userDTO) {
        if (userDTO.isClient()) {
            throw new AccessDeniedException("You don't have permission to change request status");
        }
    }

    private Request getRequestById(Long id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Request not found with ID: " + id));
    }
}
