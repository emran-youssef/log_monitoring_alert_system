package com.log_monitoring.log_alert_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableKafka
public class LogAlertSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogAlertSystemApplication.class, args);
	}

}
