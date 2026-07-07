package com.log_monitoring.log_alert_system.dtos;

import com.log_monitoring.log_alert_system.domain.Incident;
import com.log_monitoring.log_alert_system.enums.IncidentStatus;

import java.time.LocalDateTime;

public record IncidentResponse(
        Long id,
        IncidentStatus status,
        String summary,
        LocalDateTime openedAt,
        LocalDateTime closedAt,
        Long alertId
) {
    public static IncidentResponse tDto(Incident incident){
        return new IncidentResponse(
                incident.getId(),
                incident.getStatus(),
                incident.getSummary(),
                incident.getOpenedAt(),
                incident.getClosedAt(),
                incident.getAlert() != null ? incident.getAlert().getId() : null
        );
    }
}
