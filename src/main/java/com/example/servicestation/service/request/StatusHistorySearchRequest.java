package com.example.servicestation.service.request;

import com.example.servicestation.domain.enumeration.RequestStatusType;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
public class StatusHistorySearchRequest {

    private Long requestId;

    private Long changedById;

    private RequestStatusType oldStatus;

    private RequestStatusType newStatus;

    private LocalDateTime start;

    private LocalDateTime end;

}
