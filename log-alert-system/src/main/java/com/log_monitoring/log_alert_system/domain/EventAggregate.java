package com.log_monitoring.log_alert_system.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "event_aggregate")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class EventAggregate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String pattern;

    @Column(name = "event_count", nullable = false)
    private Long eventCount;

    @Column(name = "window_start", nullable = false)
    private LocalDateTime windowStart;

    @Column(name = "window_end", nullable = false)
    private LocalDateTime windowEnd;



}
