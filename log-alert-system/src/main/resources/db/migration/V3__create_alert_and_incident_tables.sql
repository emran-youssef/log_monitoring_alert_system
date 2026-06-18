CREATE TABLE alert (
    id           BIGINT          NOT NULL AUTO_INCREMENT,
    pattern      VARCHAR(100)    NOT NULL,
    severity     VARCHAR(10)     NOT NULL,
    event_count  BIGINT          NOT NULL DEFAULT 0,
    active       BOOLEAN         NOT NULL DEFAULT TRUE,
    triggered_at DATETIME(3)     NOT NULL,
    resolved_at  DATETIME(3)     NULL,

    PRIMARY KEY (id)
);

CREATE TABLE incident (
    id          BIGINT          NOT NULL AUTO_INCREMENT,
    alert_id    BIGINT          NOT NULL,
    status      VARCHAR(10)     NOT NULL,
    summary     TEXT            NOT NULL,
    opened_at   DATETIME(3)     NOT NULL,
    closed_at   DATETIME(3)     NULL,

    PRIMARY KEY (id),
    CONSTRAINT fk_incident_alert FOREIGN KEY (alert_id) REFERENCES alert (id)
);