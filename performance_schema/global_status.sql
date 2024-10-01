create table performance_schema.global_status
(
    primary key (VARIABLE_NAME) using hash
)
    engine = PERFORMANCE_SCHEMA;

