package com.example.servicestation.kafka.consumer;

import com.example.servicestation.domain.Request;
import com.example.servicestation.domain.enumeration.RequestStatusType;
import com.example.servicestation.errors.ObjectNotFoundException;
import com.example.servicestation.kafka.event.RequestStatusEvent;
import com.example.servicestation.repository.RequestRepository;
import com.example.servicestation.service.NotificationService;
import com.example.servicestation.service.StatusHistoryService;
import com.example.servicestation.service.dto.StatusHistoryDTO;
import com.example.servicestation.service.mapper.RequestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaRequestConsumer {

    private final RequestMapper requestMapper;
    private final RequestRepository requestRepository;
    private final NotificationService notificationService;
    private final StatusHistoryService statusHistoryService;

    @Transactional
    @KafkaListener(topics = "request-status-events", groupId = "service-station")
    public void handleStatusChange(RequestStatusEvent event) {

        log.info("Received Kafka event for request {}: new status={}",
                event.getRequestId(), event.getStatusType());

        Request request = requestRepository.findById(event.getRequestId())
                .orElseThrow(() -> {
                    log.error("Request {} not found in DB", event.getRequestId());
                    return new ObjectNotFoundException("Request not found with ID: " + event.getRequestId());
                });

        request.setStatusType(event.getStatusType());
        requestRepository.save(request);

        statusHistoryService.save(
                StatusHistoryDTO.builder()
                        .request(requestMapper.toDTO(request))
                        .changedBy(event.getAppUserDTO())
                        .newStatusType(event.getStatusType())
                        .reason(event.getReason())
                        .build()
        );

        log.info("Request ID {} updated to status {}", request.getId(), event.getStatusType());

        if (event.getStatusType() == RequestStatusType.DONE) {
            try {
                notificationService.notifyClient(
                        request.getAppUser(),
                        "Thank you for contacting our service station. Your car is ready!"
                );
            } catch (Exception e) {
                log.error("Failed to send notification for request {}", request.getId(), e);
            }
        }
    }
}
