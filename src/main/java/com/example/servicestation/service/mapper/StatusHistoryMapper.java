package com.example.servicestation.service.mapper;

import com.example.servicestation.domain.StatusHistory;
import com.example.servicestation.service.dto.StatusHistoryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatusHistoryMapper extends EntityMapper<StatusHistoryDTO, StatusHistory> {
}
