CREATE TABLE sql_script_log
(
    id UUID PRIMARY KEY,
    script TEXT NOT NULL,
    comment VARCHAR(255) NOT NULL,
    run_date TIMESTAMPTZ,
    insert_date TIMESTAMPTZ NOT NULL
);
