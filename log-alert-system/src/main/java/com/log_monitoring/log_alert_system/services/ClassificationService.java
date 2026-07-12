package com.log_monitoring.log_alert_system.services;

import com.log_monitoring.log_alert_system.config.ClassificationProperties;
import com.log_monitoring.log_alert_system.config.PatternConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassificationService {

    private final ClassificationProperties properties;

    public String classify(String message){
        if(message == null) {
            log.warn("log line hasn't been classified!");
            return null;
        }

        String lowerMessage = message.toLowerCase();

        for(Map.Entry<String, PatternConfig> entry: properties.getPatterns().entrySet()){
            if(lowerMessage.contains(entry.getValue().getKeyword())){
                log.debug("Classified message as pattern={}", entry.getKey());
                return entry.getKey();
            }
        }
        return null;

    }
}
