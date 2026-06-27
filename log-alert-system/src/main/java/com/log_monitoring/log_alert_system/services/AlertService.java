package com.log_monitoring.log_alert_system.services;

import com.log_monitoring.log_alert_system.config.ClassificationProperties;
import com.log_monitoring.log_alert_system.config.PatternConfig;
import com.log_monitoring.log_alert_system.domain.Alert;
import com.log_monitoring.log_alert_system.domain.EventAggregate;
import com.log_monitoring.log_alert_system.domain.LogEntry;
import com.log_monitoring.log_alert_system.repositories.AlertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlertService {

    private final AlertRepository alertRepository;
    private final IncidentService incidentService;
    private final ClassificationProperties classificationProperties;


    public void checkAndAlert(EventAggregate event){

        //get the config object of the pattern so we can read its threshold
        PatternConfig config = classificationProperties.getPatterns().get(event.getPattern());

        if(config == null)
            return;

        if(event.getEventCount() < config.getThreshold()) {
            log.debug("Threshold not reached for pattern={} count={} threshold={}",
                    event.getPattern(), event.getEventCount(), config.getThreshold());
            return;
        }

        // deduplication — skip if an active alert already exists for this pattern
        boolean alreadyActive = alertRepository.existsByPatternAndActiveTrue(event.getPattern());
        if(alreadyActive) {
            log.debug("Alert already active for pattern={}, skipping", event.getPattern());
            return;
        }

        // alert fired
        log.warn("Alert fired: pattern={} count={} threshold={}", event.getPattern(), event.getEventCount(), config.getThreshold());
        var alert = Alert.builder()
                .pattern(event.getPattern())
                .eventCount(event.getEventCount())
                .severity("HIGH")
                .active(true)
                .triggeredAt(LocalDateTime.now())
                .build();

        alertRepository.save(alert);

        log.info("Incident opened for alert:{}", alert.getPattern());
        incidentService.openIncident(alert);

    }
}
