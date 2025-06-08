package com.example.servicestation.service;

import com.example.servicestation.domain.StatusHistory;
import com.example.servicestation.repository.StatusHistoryRepository;
import com.example.servicestation.service.dto.StatusHistoryDTO;
import com.example.servicestation.service.mapper.StatusHistoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatusHistoryService {

    private final StatusHistoryMapper statusHistoryMapper;
    private final StatusHistoryRepository statusHistoryRepository;

    @Transactional
    public StatusHistoryDTO save(StatusHistoryDTO dto) {
        StatusHistory entity = statusHistoryMapper.toEntity(dto);
        entity = statusHistoryRepository.save(entity);
        return statusHistoryMapper.toDTO(entity);
    }
}
