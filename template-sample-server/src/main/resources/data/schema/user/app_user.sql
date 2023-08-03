CREATE TABLE app_user
(
    id UUID PRIMARY KEY,
    mail VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(60) NOT NULL,
-- [doc] username is at least useful for development
    username VARCHAR(255) UNIQUE,
    display_name VARCHAR(255) NOT NULL,
    language VARCHAR(2) NOT NULL,
    roles VARCHAR(255)[] NOT NULL,
    signup_date TIMESTAMPTZ NOT NULL,
    last_update_date TIMESTAMPTZ NOT NULL
);

CREATE INDEX ON app_user (mail);
CREATE INDEX ON app_user (username);
