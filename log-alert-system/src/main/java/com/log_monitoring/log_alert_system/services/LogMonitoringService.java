package com.log_monitoring.log_alert_system.services;

import com.log_monitoring.log_alert_system.domain.LogEntry;
import com.log_monitoring.log_alert_system.enums.LogLevelEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.log_monitoring.log_alert_system.repositories.LogEntryRepository;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogMonitoringService {

    private final LogEntryRepository logEntryRepository;
    private final ClassificationService classificationService;
    private final EventAggregateService aggregateService;


    /// Log pattern 4 groups: timestamp, level, service (logger name), message
    private static final Pattern LOG_PATTERN = Pattern
            .compile("^(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}(?:\\.\\d{3})?) \\[.+?] (INFO|WARN|ERROR)\\s+(\\S+)\\s+-\\s+(.+)$");

    private static final DateTimeFormatter TIMESTAMP_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");


    public LogEntry parseLogEntry(String line){

        // Remove trailing \r\n or \n
        line = line.replaceAll("\\r?\\n$", "");

        Matcher matcher = LOG_PATTERN.matcher(line);

        if(!matcher.matches()) {
            log.warn("Skipping unparseable log line: {}", line);
            return null;
        }

        String rawTimestamp = matcher.group(1);
        if (!rawTimestamp.contains(".")) {
            rawTimestamp = rawTimestamp + ".000";   // pad missing milliseconds so formatter still parses
        }

        // need to parse because the log file gives us plain text, but the database needs structured data.
        LocalDateTime timestamp = LocalDateTime.parse(rawTimestamp, TIMESTAMP_FORMAT);
        LogLevelEnum level = LogLevelEnum.valueOf(matcher.group(2));
        String service = matcher.group(3);
        String message = matcher.group(4);

        return LogEntry.builder()
                .sourceService(service)
                .logLevel(level)
                .message(message)
                .timestamp(timestamp)
                .build();
    }

    // parse then save
    public void handleLine(String line) {
        LogEntry entry = parseLogEntry(line);
        if(entry != null) {
            //classify the line to what category
            String pattern = classificationService.classify(entry.getMessage());
            entry.setPattern(pattern);
            logEntryRepository.save(entry);
            aggregateService.aggregate(entry);

        }
    }



    /* this was for testing

    @Value("${log.file.path}")
    private String filePath;


    private Long lastPosition = 0L;
    public void tailLogFile() {
        try {
            long currentLength = Files.size(Paths.get(filePath));

            if (lastPosition > currentLength)
                lastPosition = 0L;

            try (RandomAccessFile reader = new RandomAccessFile(filePath, "r")) {
                reader.seek(lastPosition);  //skips lines we already read, and jump to where we stopped last time

                String line;
                //For each new line → calls handleLine()
                while ((line = reader.readLine()) != null) {
                    handleLine(line);
                }

                lastPosition = reader.getFilePointer();  //getFilePointer() asks the RandomAccessFile: what byte position are you at right now
            }

        } catch (IOException e) {
            log.error("Failed to read log file: {}", filePath, e);
        }
    }

     */



}
