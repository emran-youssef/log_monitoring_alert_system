package com.log_monitoring.log_alert_system.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatternConfig {
    private String keyword;
    private int threshold;
}
