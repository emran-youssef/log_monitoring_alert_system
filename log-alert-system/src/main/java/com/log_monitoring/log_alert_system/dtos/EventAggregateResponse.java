package com.log_monitoring.log_alert_system.dtos;

import com.log_monitoring.log_alert_system.domain.EventAggregate;

import java.time.LocalDateTime;

public record EventAggregateResponse(
        String pattern,
        Long totalCount,
        LocalDateTime windowStart
) {
    public static EventAggregateResponse toDto(EventAggregate eventAggregate){
        return new EventAggregateResponse(
        eventAggregate.getPattern(),
        eventAggregate.getEventCount(),
        eventAggregate.getWindowStart()
        );
    }
}
