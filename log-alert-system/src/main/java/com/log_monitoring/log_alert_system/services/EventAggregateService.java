package com.log_monitoring.log_alert_system.services;

import com.log_monitoring.log_alert_system.domain.EventAggregate;
import com.log_monitoring.log_alert_system.domain.LogEntry;
import com.log_monitoring.log_alert_system.repositories.EventAggregateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventAggregateService {
    private final EventAggregateRepository eventAggregateRepository;
    private final AlertService alertService;

    @Value("${classification.window-minutes}")
    private int windowMinutes;

    @Transactional
    public void aggregate(LogEntry entry){
        if(entry.getPattern() == null)
            return;

        LocalDateTime windowStart = calculateWindowStart(entry.getTimestamp());
        LocalDateTime windowEnd = windowStart.plusMinutes(windowMinutes);

        EventAggregate event = eventAggregateRepository.findByPatternAndWindowStart(entry.getPattern(), windowStart)   // to get counting
                .orElseGet(()-> EventAggregate.builder()
                        .pattern(entry.getPattern())
                        .windowStart(windowStart)
                        .windowEnd(windowEnd)
                        .eventCount(0L)
                        .build());

        event.setEventCount(event.getEventCount() + 1);
        log.debug("Event pattern={} count={} windowStart={}", entry.getPattern(), event.getEventCount(), windowStart);
        alertService.checkAndAlert(event);
        eventAggregateRepository.save(event);

    }

    private LocalDateTime calculateWindowStart(LocalDateTime timestamp) {
        int flooredMinute = (timestamp.getMinute() / windowMinutes) * windowMinutes;
        return timestamp.withMinute(flooredMinute).withSecond(0).withNano(0);
    }

}
