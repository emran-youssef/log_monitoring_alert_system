package com.log_monitoring.log_alert_system.repositories;

import com.log_monitoring.log_alert_system.domain.Incident;
import com.log_monitoring.log_alert_system.enums.IncidentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {
    List<Incident> findByStatus(IncidentStatus status);
    List<Incident> findByAlertId(Long alertId);
}
