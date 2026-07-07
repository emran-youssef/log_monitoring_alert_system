package com.log_monitoring.log_alert_system.repositories;

import com.log_monitoring.log_alert_system.domain.EventAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventAggregateRepository extends JpaRepository<EventAggregate, Long> {
    Optional<EventAggregate> findByPatternAndWindowStart(String pattern, LocalDateTime windowStart);

    List<EventAggregate> findByPatternOrderByWindowStartAsc(String pattern);

}

