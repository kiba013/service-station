package com.example.servicestation.service;

import com.example.servicestation.domain.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {

    public void notifyClient(AppUser client, String message) {
        log.info("Sending SMS to {}: {}", client, message);
    }
}
