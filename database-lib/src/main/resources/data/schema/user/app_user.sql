CREATE TABLE app_user
(
    id UUID PRIMARY KEY,
    mail VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(60) NOT NULL,
    display_name VARCHAR(255) NOT NULL,
    language VARCHAR(2) NOT NULL,
    status VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    signup_date TIMESTAMPTZ NOT NULL,
    last_update_date TIMESTAMPTZ NOT NULL
);

CREATE INDEX ON app_user (mail);
