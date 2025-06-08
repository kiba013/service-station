package com.example.servicestation.service.mapper;

import com.example.servicestation.domain.Request;
import com.example.servicestation.service.dto.RequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestMapper extends EntityMapper<RequestDTO, Request> {
}
