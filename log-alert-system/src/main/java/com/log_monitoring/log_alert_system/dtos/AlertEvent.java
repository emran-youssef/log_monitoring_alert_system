package com.log_monitoring.log_alert_system.dtos;

import com.log_monitoring.log_alert_system.domain.Alert;

import java.time.LocalDateTime;

public record AlertEvent(
        Long id,
        String pattern,
        String severity,
        Long eventCount,
        LocalDateTime triggeredAt
) {
    public static AlertEvent toDto(Alert alert){
        return new AlertEvent(
                alert.getId(),
                alert.getPattern(),
                alert.getSeverity(),
                alert.getEventCount(),
                alert.getTriggeredAt()
        );
    }


}
