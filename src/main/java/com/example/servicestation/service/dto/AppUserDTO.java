package com.example.servicestation.service.dto;

import com.example.servicestation.domain.enumeration.AppRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AppUserDTO extends BaseDTO {

    private String login;

    private String password;

    private AppRole appRole;

    private String name;

    private String mobile;

    @JsonIgnore
    public boolean isClient() {
        return appRole != null && appRole.equals(AppRole.ROLE_CLIENT);
    }

    @JsonIgnore
    public boolean isNotClient() {
        return !isClient();
    }
}
