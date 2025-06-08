package com.example.servicestation.service.mapper;

import com.example.servicestation.domain.AppUser;
import com.example.servicestation.service.dto.AppUserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppUserMapper extends EntityMapper<AppUserDTO, AppUser> {
}
