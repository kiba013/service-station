package com.example.servicestation.util;

import com.example.servicestation.domain.enumeration.RequestStatusType;
import com.example.servicestation.errors.InvalidStatusTransitionException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class StatusTransitionValidator {

    private static final Map<RequestStatusType, Set<RequestStatusType>> ALLOWED_TRANSITIONS = Map.of(
            RequestStatusType.NEW, Set.of(RequestStatusType.ACCEPTED),
            RequestStatusType.ACCEPTED, Set.of(RequestStatusType.PROCESSING),
            RequestStatusType.PROCESSING, Set.of(RequestStatusType.REPAIRING),
            RequestStatusType.REPAIRING, Set.of(RequestStatusType.DONE),
            RequestStatusType.DONE, Set.of()
    );

    public void validateTransition(RequestStatusType currentStatus, RequestStatusType newStatus) {
        Set<RequestStatusType> allowed = ALLOWED_TRANSITIONS.getOrDefault(currentStatus, Set.of());
        if (!allowed.contains(newStatus)) {
            throw new InvalidStatusTransitionException(currentStatus, newStatus);
        }
    }
}
