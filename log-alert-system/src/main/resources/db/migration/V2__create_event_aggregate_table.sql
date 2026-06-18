CREATE TABLE event_aggregate (
    id           BIGINT          NOT NULL AUTO_INCREMENT,
    pattern      VARCHAR(100)    NOT NULL,
    severity     VARCHAR(10)     NOT NULL,
    count        BIGINT          NOT NULL DEFAULT 0,
    window_start DATETIME(3)     NOT NULL,
    window_end   DATETIME(3)     NOT NULL,

    PRIMARY KEY (id)
);
