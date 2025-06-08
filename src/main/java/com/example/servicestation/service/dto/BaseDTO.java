package com.example.servicestation.service.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class BaseDTO implements Serializable {

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ZonedDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ZonedDateTime updatedAt;
}
