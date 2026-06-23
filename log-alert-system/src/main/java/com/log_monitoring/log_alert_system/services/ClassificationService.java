package com.log_monitoring.log_alert_system.services;

import com.log_monitoring.log_alert_system.config.ClassificationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ClassificationService {

    private final ClassificationProperties properties;

    public String classify(String message){
        if(message == null)
            return null;

        String lowerMessage = message.toLowerCase();

        for(Map.Entry<String, String> entry: properties.getPatterns().entrySet()){
            if(lowerMessage.contains(entry.getValue())){
                return entry.getKey();
            }
        }
        return null;

    }
}
