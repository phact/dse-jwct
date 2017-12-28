CREATE KEYSPACE jwct WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}  AND durable_writes = true;

CREATE TABLE jwct.meta (
    data_key text,
    data_source text,
    key_id int,
    meta_data text,
    target_table int,
    type int,
    PRIMARY KEY (data_key, data_source)
) WITH CLUSTERING ORDER BY (data_source ASC)

CREATE TABLE jwct.events (
    key_id int,
    time_part bigint,
    time bigint,
    seq_num bigint,
    recieve_time bigint,
    update_time bigint,
    value text,
    PRIMARY KEY ((key_id, time_part), time, seq_num)
) WITH CLUSTERING ORDER BY (time DESC, seq_num ASC)
