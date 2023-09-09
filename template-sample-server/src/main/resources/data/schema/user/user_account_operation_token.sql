CREATE TABLE user_account_operation_token
(
    token VARCHAR(40) PRIMARY KEY,
    token_type VARCHAR(255) NOT NULL,
    user_id UUID NOT NULL,
    creation_date TIMESTAMPTZ NOT NULL,
    FOREIGN KEY (user_id) REFERENCES app_user (id)
);
