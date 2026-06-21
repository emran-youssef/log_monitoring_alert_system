package com.log_monitoring.log_alert_system.domain;

import com.log_monitoring.log_alert_system.enums.LogLevelEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "log_entry")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source_service", nullable = false, length = 100)
    private String sourceService;

    @Enumerated(EnumType.STRING)
    @Column(name = "log_level", nullable = false, length = 10)
    private LogLevelEnum logLevel;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(length = 100)
    private String pattern;

    @Column(nullable = false)
    private LocalDateTime timestamp;


}
