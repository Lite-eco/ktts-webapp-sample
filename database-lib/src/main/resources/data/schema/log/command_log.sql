CREATE TABLE command_log
(
    id UUID PRIMARY KEY,
    user_id UUID,
    affected_user_id UUID,
    deployment_log_id UUID NOT NULL,
    command_class VARCHAR(255) NOT NULL,
    json_command TEXT NOT NULL,
    ip VARCHAR(255) NOT NULL,
    user_session_id UUID,
    ids_log TEXT NOT NULL,
    json_result TEXT,
    exception_stack_trace TEXT,
    start_date TIMESTAMPTZ NOT NULL,
    end_date TIMESTAMPTZ NOT NULL,
    -- [doc] no other foreign keys because we want to log (& keep logs!) in all circumstances
    -- (even if the command transaction fails, for instance)
    FOREIGN KEY (deployment_log_id) REFERENCES deployment_log (id)
);
