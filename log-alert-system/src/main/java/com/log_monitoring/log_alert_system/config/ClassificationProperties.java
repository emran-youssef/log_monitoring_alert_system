package com.log_monitoring.log_alert_system.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;


@Component
@ConfigurationProperties(prefix = "classification")   //read all the classification properties in yaml
@Getter
@Setter
public class ClassificationProperties {

    private LinkedHashMap<String, String> patterns = new LinkedHashMap<>();
}
