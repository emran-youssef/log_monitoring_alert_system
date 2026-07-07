package com.log_monitoring.log_alert_system.dtos;

import com.log_monitoring.log_alert_system.domain.Alert;

import java.time.LocalDateTime;

public record AlertResponse(
        Long id,
        String pattern,
        String severity,
        Long eventCount,
        Boolean active,
        LocalDateTime triggeredAt,
        LocalDateTime resolvedAt
) {
    public static AlertResponse toDto(Alert alert){
        return new AlertResponse(
                alert.getId(),
                alert.getPattern(),
                alert.getSeverity(),
                alert.getEventCount(),
                alert.getActive(),
                alert.getTriggeredAt(),
                alert.getResolvedAt()
        );
    }

}
