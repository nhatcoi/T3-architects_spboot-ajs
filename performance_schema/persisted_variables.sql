create table performance_schema.persisted_variables
(
    primary key (VARIABLE_NAME) using hash
)
    engine = PERFORMANCE_SCHEMA;

