package com.example.servicestation.service.dto;


import com.example.servicestation.domain.enumeration.RequestStatusType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RequestDTO extends BaseDTO {

    private String description;

    private String carModel;

    private RequestStatusType statusType;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private AppUserDTO appUser;
}
