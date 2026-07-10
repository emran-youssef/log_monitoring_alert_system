package com.log_monitoring.log_alert_system.kafka;

import com.log_monitoring.log_alert_system.domain.Alert;
import com.log_monitoring.log_alert_system.dtos.AlertEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.core.JacksonException;

@Component
@RequiredArgsConstructor
@Slf4j
public class AlertEventPublisher {

    private static final String TOPIC = "alert-events";
    private final KafkaTemplate <String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void publish(Alert alert){
        var event = AlertEvent.toDto(alert);

        try {
            String json = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(TOPIC, json);
            log.info("Published alert event for alertId={}, pattern={} ", alert.getId(), alert.getPattern());

        } catch (JacksonException e) {
            log.error("Failed to serialize AlertEvent for alertId={}", alert.getId(), e);

        }
    }
}
