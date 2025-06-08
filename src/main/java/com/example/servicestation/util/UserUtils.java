package com.example.servicestation.util;

import com.example.servicestation.service.AppUserService;
import com.example.servicestation.service.dto.AppUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserUtils {

    private final AppUserService appUserService;

    @Transactional
    public AppUserDTO getCurrentUser() {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext()
                        .getAuthentication();
        User user = (User) usernamePasswordAuthenticationToken.getPrincipal();
        return appUserService.findByLogin(user.getUsername()).orElseThrow(() ->
                new IllegalArgumentException("User not found!"));
    }

}
