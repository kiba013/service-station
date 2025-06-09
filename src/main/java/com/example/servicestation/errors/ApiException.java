package com.example.servicestation.errors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ApiException {

    private Integer status;

    private String message;

    private Long timestamp;

}
