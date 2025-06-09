package com.example.servicestation.errors;

import com.example.servicestation.domain.enumeration.RequestStatusType;

public class InvalidStatusTransitionException extends RuntimeException {

    public InvalidStatusTransitionException(String message) {
        super(message);
    }

    public InvalidStatusTransitionException(RequestStatusType from, RequestStatusType to) {
        super(String.format("Transition from status %s to %s is prohibited", from, to));
    }
}
