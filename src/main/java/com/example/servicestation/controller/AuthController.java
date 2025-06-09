package com.example.servicestation.controller;

import com.example.servicestation.service.AuthenticationService;
import com.example.servicestation.service.dto.AppUserDTO;
import com.example.servicestation.service.request.AuthenticationRequest;
import com.example.servicestation.service.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> sigIn(@RequestBody @Validated AuthenticationRequest authRequest) {
        return ResponseEntity.ok(authenticationService.login(authRequest));
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppUserDTO> signUp(@RequestBody AppUserDTO dto) {
        return ResponseEntity.ok(authenticationService.signUp(dto));
    }
}
