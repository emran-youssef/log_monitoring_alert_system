package com.log_monitoring.log_alert_system.controllers;

import com.log_monitoring.log_alert_system.dtos.EventAggregateResponse;
import com.log_monitoring.log_alert_system.repositories.EventAggregateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/event-aggregates")
public class EventAggregateController {

    private final EventAggregateRepository eventAggregateRepository;

    @GetMapping
    public List<EventAggregateResponse> getEventAggregates(
            @RequestParam(required = false) String pattern
    ){
        if(pattern != null){
            return eventAggregateRepository.findByPatternOrderByWindowStartAsc(pattern)
                    .stream()
                    .map(EventAggregateResponse::toDto)
                    .toList();
        }

        return eventAggregateRepository.findAll()
                .stream()
                .map(EventAggregateResponse::toDto)
                .toList();
    }
}
