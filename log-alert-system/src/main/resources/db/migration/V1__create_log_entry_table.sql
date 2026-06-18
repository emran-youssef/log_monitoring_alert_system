CREATE TABLE log_entry (
    id             BIGINT          NOT NULL AUTO_INCREMENT,
    source_service VARCHAR(100)    NOT NULL,
    log_level      VARCHAR(10)     NOT NULL,
    message        TEXT            NOT NULL,
    pattern        VARCHAR(100)    NULL,
    timestamp      DATETIME(3)     NOT NULL,

    PRIMARY KEY (id)
);

