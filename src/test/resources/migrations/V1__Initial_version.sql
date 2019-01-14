CREATE TABLE IF NOT EXISTS parking (
    id uuid NOT NULL,
    driver_type character varying(255) NOT NULL,
    finished_at timestamp without time zone,
    parking_status character varying(255) NOT NULL,
    price numeric(10,2),
    registration_number bytea NOT NULL,
    started_at timestamp without time zone NOT NULL,
    CONSTRAINT parking_pkey PRIMARY KEY (id)
);
