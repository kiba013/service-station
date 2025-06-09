package com.example.servicestation.controller;

import com.example.servicestation.domain.enumeration.RequestStatusType;
import com.example.servicestation.service.RequestService;
import com.example.servicestation.service.dto.RequestDTO;
import com.example.servicestation.service.request.RequestStatusUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/requests")
public class RequestController {

    private final RequestService requestService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RequestDTO> createRequest(@Valid @RequestBody RequestDTO requestDTO) {
        return ResponseEntity.ok(requestService.save(requestDTO));
    }

    @PutMapping(value = "/{requestId}/status",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RequestDTO> updateRequest(@PathVariable Long requestId,
                                                    @RequestBody @Valid RequestStatusUpdateRequest request) {
        return ResponseEntity.ok(requestService.update(requestId, request));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RequestDTO>> getAllRequests(@RequestParam(required = false) Long clientId,
                                                           @RequestParam(required = false) RequestStatusType status) {
        return ResponseEntity.ok(requestService.findAll(clientId, status));
    }

    @GetMapping(value = "/{requestId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RequestDTO> getOne(@PathVariable Long requestId) {
        return ResponseEntity.ok(requestService.findOne(requestId));
    }

    @DeleteMapping("/{requestId}")
    public void delete(@PathVariable Long requestId) {
        requestService.delete(requestId);
    }
}
