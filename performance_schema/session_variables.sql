create table performance_schema.session_variables
(
    primary key (VARIABLE_NAME) using hash
)
    engine = PERFORMANCE_SCHEMA;

