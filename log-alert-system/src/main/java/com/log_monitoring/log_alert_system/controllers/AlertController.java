package com.log_monitoring.log_alert_system.controllers;

import com.log_monitoring.log_alert_system.dtos.AlertResponse;
import com.log_monitoring.log_alert_system.repositories.AlertRepository;
import com.log_monitoring.log_alert_system.services.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertRepository alertRepository;
    private final AlertService alertService;


    @GetMapping
    public List<AlertResponse> getActiveAlert(){
        return alertRepository.findByActiveTrue()
                .stream()
                .map(AlertResponse::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlertResponse> getAlert(@PathVariable Long id){
        return alertRepository.findById(id)
                .map(AlertResponse::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/resolve")
    public ResponseEntity<AlertResponse> resolveAlert(@PathVariable Long id){
        return alertService.resolveAlert(id)
                .map(AlertResponse::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

}
