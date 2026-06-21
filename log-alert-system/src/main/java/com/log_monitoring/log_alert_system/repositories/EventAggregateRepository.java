package com.log_monitoring.log_alert_system.repositories;

import com.log_monitoring.log_alert_system.domain.EventAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventAggregateRepository extends JpaRepository<EventAggregate, Long> {
}
