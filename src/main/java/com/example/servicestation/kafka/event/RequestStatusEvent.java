package com.example.servicestation.kafka.event;

import com.example.servicestation.domain.enumeration.RequestStatusType;
import com.example.servicestation.service.dto.AppUserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestStatusEvent implements Serializable {

    private Long requestId;
    private RequestStatusType statusType;
    private String reason;
    private AppUserDTO appUserDTO;
}
