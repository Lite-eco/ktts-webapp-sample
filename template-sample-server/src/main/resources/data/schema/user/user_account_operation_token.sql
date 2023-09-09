CREATE TABLE user_account_operation_token
(
    token VARCHAR(40) PRIMARY KEY,
    token_type VARCHAR(255) NOT NULL,
    user_id UUID NOT NULL,
    user_mail_log_id UUID,
    creation_date TIMESTAMPTZ NOT NULL,
    FOREIGN KEY (user_id) REFERENCES app_user (id),
    FOREIGN KEY (user_mail_log_id) REFERENCES user_mail_log (id)
);
