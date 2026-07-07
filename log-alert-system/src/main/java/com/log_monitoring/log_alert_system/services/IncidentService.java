package com.log_monitoring.log_alert_system.services;


import com.log_monitoring.log_alert_system.domain.Alert;
import com.log_monitoring.log_alert_system.domain.Incident;
import com.log_monitoring.log_alert_system.enums.IncidentStatus;
import com.log_monitoring.log_alert_system.repositories.IncidentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Optional;

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

    @Transactional
    public Optional<Incident> closeIncident(Long id) {
        return incidentRepository.findById(id)
                .map(incident -> {
                    incident.setStatus(IncidentStatus.RESOLVED);
                    incident.setClosedAt(LocalDateTime.now());
                    return incidentRepository.save(incident);
                });
    }
}
