package com.example.servicestation.service.specification;

import com.example.servicestation.domain.Request;
import com.example.servicestation.domain.enumeration.RequestStatusType;
import org.springframework.data.jpa.domain.Specification;

public class RequestSpecification {

    public static Specification<Request> byClient(Long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("appUser").get("id"), id);
    }

    public static Specification<Request> withStatus(RequestStatusType requestStatusType) {
        return ((root, query, criteriaBuilder) -> {
            if (requestStatusType == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("statusType"), requestStatusType);
        });
    }
}
