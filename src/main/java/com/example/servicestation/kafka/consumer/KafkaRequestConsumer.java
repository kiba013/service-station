package com.example.servicestation.kafka.consumer;

import com.example.servicestation.domain.Request;
import com.example.servicestation.domain.enumeration.RequestStatusType;
import com.example.servicestation.kafka.event.RequestStatusEvent;
import com.example.servicestation.repository.RequestRepository;
import com.example.servicestation.service.NotificationService;
import com.example.servicestation.service.StatusHistoryService;
import com.example.servicestation.service.dto.StatusHistoryDTO;
import com.example.servicestation.service.mapper.RequestMapper;
import jakarta.persistence.EntityNotFoundException;
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

        log.info("Received Kafka event: {}", event);
        System.out.println("🔥 Kafka Listener triggered!");

        Request request = requestRepository.findById(event.getRequestId())
                .orElseThrow(() -> new EntityNotFoundException("Request not found!"));

        request.setStatusType(event.getStatusType());
        requestRepository.save(request);

        statusHistoryService.save(
                StatusHistoryDTO.builder()
                        .request(requestMapper.toDTO(request))
                        .changedBy(event.getAppUserDTO())
                        .newStatusType(event.getStatusType())
                        .reason(event.getReason())
                        .createdAt(ZonedDateTime.now())
                        .build()
        );

        log.info("Request ID {} updated to status {}", request.getId(), event.getStatusType());

        if (event.getStatusType() == RequestStatusType.DONE) {
            notificationService.notifyClient(request.getAppUser(),
                    "Thank you for contacting our the service station, Your car is ready!");
        }
    }
}
