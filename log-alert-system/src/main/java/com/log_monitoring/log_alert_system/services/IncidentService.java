package com.log_monitoring.log_alert_system.services;


import com.log_monitoring.log_alert_system.domain.Alert;
import com.log_monitoring.log_alert_system.domain.Incident;
import com.log_monitoring.log_alert_system.enums.IncidentStatus;
import com.log_monitoring.log_alert_system.repositories.IncidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class IncidentService {

    private final IncidentRepository incidentRepository;
    public void openIncident(Alert alert){

        var incident = Incident.builder()
                .status(IncidentStatus.OPEN)
                .openedAt(LocalDateTime.now())
                .alert(alert)
                .summary("Alert triggered for pattern:" + alert.getPattern() + " with event count: "+ alert.getEventCount())
                .build();

        incidentRepository.save(incident);

    }
}
