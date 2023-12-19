CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS routes(
    id UUID PRIMARY KEY,
    route_id UUID NOT NULL,
    courier_id UUID NOT NULL,
    origin_id UUID NOT NULL,
    destination_id UUID NOT NULL,
    status TEXT NOT NULL,
    event_time TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT now(),
    CONSTRAINT uq_event UNIQUE (route_id, status)
);
