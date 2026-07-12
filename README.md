# Log Monitoring & Alert System

A Spring Boot service that consumes real-time application logs, classifies them by severity, and triggers alerts — built as part of a backend training program at Eastnets.

This service consumes logs from the [Digital Wallet App](https://github.com/emran-youssef/digital_wallet_spring) via Apache Kafka and publishes structured alert events to a downstream Kafka topic.

## Features

- **Real-Time Log Consumption** — consumes logs from the `wallet-logs` Kafka topic
- **Log Classification Pipeline** — parses and classifies log lines by severity (`DEBUG`, `INFO`, `WARN`, `ERROR`) using pattern/keyword-based matching
- **Threshold-Based Alerting** — triggers alerts when specific conditions or repeated error patterns are detected
- **Event Publishing** — publishes structured `AlertEvent` objects to the `alert-events` Kafka topic via `AlertEventPublisher`
- **Decoupled Design** — has no direct dependency on the Wallet app; the Kafka topic name is the only shared contract

## Tech Stack

- Java, Spring Boot
- Spring Kafka (consumer + producer)
- Spring Data JPA / Hibernate
- MySQL
- Flyway (schema migrations)
- Maven

## Architecture

┌──────────────────────┐   wallet-logs   ┌───────────────────────┐   alert-events   ┌─────────────┐
│  Digital Wallet App   │ ───────────────▶ │  Log Alert System      │ ─────────────▶ │  Downstream  │
│                       │                  │  (parse → classify →   │                 │  consumers   │
│                       │                  │   alert → publish)     │                 │              │
└──────────────────────┘                  └───────────────────────┘                 └─────────────┘

1. Consumes raw log lines from `wallet-logs`
2. Parses and classifies each line (severity level, keyword matching)
3. Triggers alerts based on classification rules
4. Publishes an `AlertEvent` to `alert-events` for any triggered alert

## Getting Started

### Prerequisites

- Java 17+ (or the JDK version matching your `pom.xml`)
- Maven
- MySQL
- Apache Kafka broker (KRaft mode) running locally, with the [Digital Wallet App](https://github.com/emran-youssef/digital_wallet_spring) producing to `wallet-logs`

### Kafka Setup

```bash
docker run -d --name kafka-local -p 9092:9092 apache/kafka:3.8.0
```

Verify topics and consume alerts directly via the Kafka CLI:

```bash
/opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic alert-events --from-beginning
```

### Configuration

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/log_alert_db
    username: <your-username>
    password: <your-password>
  flyway:
    enabled: true
  kafka:
    consumer:
      bootstrap-servers: localhost:9092
      group-id: log-alert-group
    producer:
      bootstrap-servers: localhost:9092
```

### Run the App

```bash
mvn clean install
mvn spring-boot:run
```

## Verified End-to-End Flow

Triggering repeated `WALLET_NOT_FOUND` events from the Wallet app (e.g., calling `GET /wallets/balance/{invalid-id}` multiple times) fires a classified alert, which is published as a JSON `AlertEvent` to the `alert-events` topic — confirmed via Kafka console consumer.

## Notes

- `LinkedHashMap` is used for classification rules to preserve first-match-wins ordering
- `ddl-auto` is set to `validate`, not `update` — Flyway owns schema migrations
- Uses Jackson 3 (`tools.jackson.*`), not Jackson 2 — a Spring Boot 4 compatibility distinction

## Related Repository

- [Digital Wallet App](https://github.com/emran-youssef/digital_wallet_spring) — produces logs consumed by this service
