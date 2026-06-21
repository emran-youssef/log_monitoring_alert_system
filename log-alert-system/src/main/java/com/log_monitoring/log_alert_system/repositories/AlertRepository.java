package com.log_monitoring.log_alert_system.repositories;

import com.log_monitoring.log_alert_system.domain.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
}
