package com.example.servicestation.controller;

import com.example.servicestation.service.StatusHistoryService;
import com.example.servicestation.service.dto.StatusHistoryDTO;
import com.example.servicestation.service.request.StatusHistorySearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StatusHistoryController {

    private final StatusHistoryService statusHistoryService;

    @GetMapping(value = "/status-history/get-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<StatusHistoryDTO> getAll(StatusHistorySearchRequest request, Pageable pageable) {
        return statusHistoryService.findAll(request, pageable);
    }
}
