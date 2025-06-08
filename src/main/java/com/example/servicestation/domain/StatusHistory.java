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
@Table(name = "status_history")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SequenceGenerator(name = "sequence_generator", sequenceName = "status_history_sequence", allocationSize = 1)
public class StatusHistory extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request request;

    @ManyToOne
    @JoinColumn(name = "changed_by_app_user_id")
    private AppUser changedBy;

    @Enumerated(EnumType.STRING)
    private RequestStatusType newStatusType;

    private String reason;

}

