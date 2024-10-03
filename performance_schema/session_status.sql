create table performance_schema.session_status
(
    primary key (VARIABLE_NAME) using hash
)
    engine = PERFORMANCE_SCHEMA;

