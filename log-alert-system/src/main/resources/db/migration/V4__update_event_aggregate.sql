ALTER TABLE event_aggregate
    DROP COLUMN severity,
    CHANGE COLUMN count event_count BIGINT NOT NULL DEFAULT 0;