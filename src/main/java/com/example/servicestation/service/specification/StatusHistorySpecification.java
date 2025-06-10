package com.example.servicestation.service.specification;

import com.example.servicestation.domain.StatusHistory;
import com.example.servicestation.domain.enumeration.RequestStatusType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class StatusHistorySpecification {

    public static Specification<StatusHistory> byRequest(Long requestId) {
        return ((root, query, criteriaBuilder) ->
                requestId == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("request").get("id"), requestId));
    }

    public static Specification<StatusHistory> byChangedAppUser(Long appUserId) {
        return (root, query, criteriaBuilder) ->
                appUserId == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("changedBy").get("id"), appUserId);
    }

    public static Specification<StatusHistory> withNewStatus(RequestStatusType requestStatusType) {
        return ((root, query, criteriaBuilder) -> {
            if (requestStatusType == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("newStatusType"), requestStatusType);
        });
    }

    public static Specification<StatusHistory> withOldStatus(RequestStatusType requestStatusType) {
        return ((root, query, criteriaBuilder) -> {
            if (requestStatusType == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("oldStatusType"), requestStatusType);
        });
    }

    public static Specification<StatusHistory> withCreatedAfter(LocalDateTime start) {
        return (root, query, cb) ->
                start == null ? cb.conjunction() : cb.greaterThanOrEqualTo(root.get("createdAt"), start);
    }

    public static Specification<StatusHistory> withCreatedBefore(LocalDateTime end) {
        return (root, query, criteriaBuilder) ->
                end == null ? criteriaBuilder.conjunction() : criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), end);
    }
}
