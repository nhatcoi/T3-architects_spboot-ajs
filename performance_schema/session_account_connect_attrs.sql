create table performance_schema.session_account_connect_attrs
(
    primary key (PROCESSLIST_ID, ATTR_NAME) using hash
)
    engine = PERFORMANCE_SCHEMA
    collate = utf8mb4_bin;

