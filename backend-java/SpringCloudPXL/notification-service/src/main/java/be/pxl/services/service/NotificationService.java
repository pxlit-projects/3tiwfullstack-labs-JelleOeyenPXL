package be.pxl.services.service;

import be.pxl.services.domain.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {
    public void sendMessage(Notification notification) {
        log.info("receiving notifications...");
        log.info("sending...{}", notification.getMessage());
        log.info("to {}", notification.getSender());
    }
}
