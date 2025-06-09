package com.example.servicestation.service.request;

import com.example.servicestation.domain.enumeration.RequestStatusType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestStatusUpdateRequest {

    @NotNull
    private RequestStatusType status;

    @NotBlank
    private String reason;
}
