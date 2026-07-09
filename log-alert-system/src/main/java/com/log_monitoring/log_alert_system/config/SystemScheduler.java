package com.log_monitoring.log_alert_system.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.log_monitoring.log_alert_system.services.LogMonitoringService;

@Slf4j
@Component
@RequiredArgsConstructor
public class SystemScheduler {
/*
    private final LogMonitoringService logMonitoringService;

    @Scheduled(fixedDelayString = "${log.monitoring.fixed-delay}") //log.monitoring.fixed-delay pulled from yaml
    public void scanLogFile() {
        log.info("Scanning log file...");
        logMonitoringService.tailLogFile();
    }

 */
}
