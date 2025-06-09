package com.example.servicestation.service;

import com.example.servicestation.domain.AppUser;
import com.example.servicestation.domain.enumeration.AppRole;
import com.example.servicestation.repository.AppUserRepository;
import com.example.servicestation.service.dto.AppUserDTO;
import com.example.servicestation.service.mapper.AppUserMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserMapper appUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;

    @Transactional
    public AppUserDTO save(AppUserDTO dto) {
        checkLogin(dto.getLogin());
        AppUser entity = appUserMapper.toEntity(dto);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity.setAppRole(AppRole.ROLE_CLIENT);
        entity = appUserRepository.save(entity);
        return appUserMapper.toDTO(entity);
    }

    @Transactional(readOnly = true)
    public Optional<AppUserDTO> findByLogin(String login) {
        return appUserRepository.findByLogin(login).map(appUserMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public AppUserDTO findById(Long id) {
        return appUserRepository.findById(id)
                .map(appUserMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));
    }

    private void checkLogin(String login) {
        appUserRepository.findByLogin(login).ifPresent(user -> {
            throw new IllegalArgumentException("User with login: " + login + " already exists!");
        });
    }
}
