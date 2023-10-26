CREATE TABLE mailing_log
(
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    sender_name VARCHAR(255) NOT NULL,
    sender_mail VARCHAR(255) NOT NULL,
    recipient_name VARCHAR(255) NOT NULL,
    recipient_mail VARCHAR(255) NOT NULL,
    subject TEXT NOT NULL,
    content TEXT NOT NULL,
    data TEXT NOT NULL,
    log_date TIMESTAMPTZ NOT NULL,
    FOREIGN KEY (user_id) REFERENCES app_user (id)
);
