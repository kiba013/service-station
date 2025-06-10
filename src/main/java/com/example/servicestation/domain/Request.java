package com.example.servicestation.domain;


import com.example.servicestation.domain.enumeration.RequestStatusType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "request")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SequenceGenerator(name = "sequence_generator", sequenceName = "request_sequence", allocationSize = 1)
public class Request extends BaseEntity {

    private String description;

    private String carModel;

    @Enumerated(EnumType.STRING)
    private RequestStatusType statusType;

    @ManyToOne
    @JoinColumn(name = "app_user_id", nullable = false)
    private AppUser appUser;
}
