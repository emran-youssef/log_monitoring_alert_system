package com.log_monitoring.log_alert_system.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LogKafkaConsumer {

    private final LogMonitoringService logMonitoringService;

    @KafkaListener(topics = "wallet-logs", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeLogMessage(String logLine){
        log.debug("Received Kafka message: {}", logLine);
        try {
            logMonitoringService.handleLine(logLine);
        } catch (Exception e) {
            log.error("Failed to process Kafka message: {}", logLine, e);
        }

    }
}
