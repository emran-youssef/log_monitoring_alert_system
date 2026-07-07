package com.log_monitoring.log_alert_system.controllers;

import com.log_monitoring.log_alert_system.dtos.IncidentResponse;
import com.log_monitoring.log_alert_system.enums.IncidentStatus;
import com.log_monitoring.log_alert_system.repositories.IncidentRepository;
import com.log_monitoring.log_alert_system.services.IncidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/incident")
public class IncidentController {

    private final IncidentRepository incidentRepository;
    private final IncidentService incidentService;

    @GetMapping
    public List<IncidentResponse> getIncidents(
            @RequestParam(required = false) IncidentStatus status,
            @RequestParam(required = false) Long alertId) {

        if (status != null) {
            return incidentRepository.findByStatus(status)
                    .stream()
                    .map(IncidentResponse::tDto)
                    .toList();
        }
        if (alertId != null) {
            return incidentRepository.findByAlertId(alertId)
                    .stream()
                    .map(IncidentResponse::tDto)
                    .toList();
        }
        return incidentRepository.findAll()
                .stream()
                .map(IncidentResponse::tDto)
                .toList();
    }

    @PostMapping("/{id}/close")
    public ResponseEntity<IncidentResponse> closeIncident(@PathVariable Long id) {
        return incidentService.closeIncident(id)
                .map(IncidentResponse::tDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
