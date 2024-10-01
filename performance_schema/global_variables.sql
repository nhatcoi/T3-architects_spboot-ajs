create table performance_schema.global_variables
(
    primary key (VARIABLE_NAME) using hash
)
    engine = PERFORMANCE_SCHEMA;

