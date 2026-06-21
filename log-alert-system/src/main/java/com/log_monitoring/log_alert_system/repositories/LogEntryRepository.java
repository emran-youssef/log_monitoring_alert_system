package com.log_monitoring.log_alert_system.repositories;

import com.log_monitoring.log_alert_system.domain.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {

}
