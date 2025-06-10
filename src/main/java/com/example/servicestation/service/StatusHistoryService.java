package com.example.servicestation.service;

import com.example.servicestation.domain.StatusHistory;
import com.example.servicestation.errors.ObjectNotFoundException;
import com.example.servicestation.repository.StatusHistoryRepository;
import com.example.servicestation.service.dto.StatusHistoryDTO;
import com.example.servicestation.service.mapper.StatusHistoryMapper;
import com.example.servicestation.service.request.StatusHistorySearchRequest;
import com.example.servicestation.service.specification.StatusHistorySpecification;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatusHistoryService {

    private final StatusHistoryMapper statusHistoryMapper;
    private final StatusHistoryRepository statusHistoryRepository;

    @Transactional
    public StatusHistoryDTO save(@Valid StatusHistoryDTO dto) {
        StatusHistory entity = statusHistoryMapper.toEntity(dto);
        entity = statusHistoryRepository.save(entity);
        log.info("Saved StatusHistory with ID: {}", entity.getId());
        return statusHistoryMapper.toDTO(entity);
    }

    @Transactional(readOnly = true)
    public StatusHistoryDTO findOne(Long id) {
        return statusHistoryRepository.findById(id)
                .map(statusHistoryMapper::toDTO)
                .orElseThrow(() -> new ObjectNotFoundException("Status History not found with ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<StatusHistoryDTO> findAll() {
        return statusHistoryRepository
                .findAll()
                .stream()
                .map(statusHistoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        statusHistoryRepository.deleteById(id);
        log.info("Deleted StatusHistory with ID: {}", id);
    }

    @Transactional(readOnly = true)
    public List<StatusHistoryDTO> findAll(Long requestId) {
        return statusHistoryRepository.findAllByRequestId(requestId)
                .stream()
                .map(statusHistoryMapper::toDTO)
                .toList();

    }

    @Transactional(readOnly = true)
    public Page<StatusHistoryDTO> findAll(StatusHistorySearchRequest request, Pageable pageable) {

        Specification<StatusHistory> specification = Specification
                .where(StatusHistorySpecification.byRequest(request.getRequestId()))
                .and(StatusHistorySpecification.byChangedAppUser(request.getChangedById()))
                .and(StatusHistorySpecification.withNewStatus(request.getNewStatus()))
                .and(StatusHistorySpecification.withOldStatus(request.getOldStatus()))
                .and(StatusHistorySpecification.withCreatedAfter(request.getStart()))
                .and(StatusHistorySpecification.withCreatedBefore(request.getEnd()));

        return statusHistoryRepository.findAll(specification, pageable)
                .map(statusHistoryMapper::toDTO);
    }
}
