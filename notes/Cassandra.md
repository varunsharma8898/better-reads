# Getting started with Cassandra

From: https://cassandra.apache.org/_/quickstart.html

### 1: Get cassandra using docker
`docker pull cassandra:latest`

### 2. Start cassandra
```
docker network create cassandra

docker run --rm -d --name cassandra --hostname cassandra --network cassandra cassandra

docker run --rm --name varun-cassandra --network cassandra-network -v /Users/sharmava/testfiles/cassandra/:/var/lib/cassandra -e CASSANDRA_BROADCAST_ADDRESS=172.19.0.1 -P -d cassandra:3

docker run --rm --name varun-cassandra --network cassandra-network -v /Users/sharmava/testfiles/cassandra/:/var/lib/cassandra -e CASSANDRA_BROADCAST_ADDRESS=172.19.0.1 -p 7001:7001 -p 7199:7199 -p 9042:9042 -p 9160:9160 -d cassandra:3

```

### 3. Create files
```
-- Create a keyspace
CREATE KEYSPACE IF NOT EXISTS betterreads WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : '1' };

-- Create a table
CREATE TABLE IF NOT EXISTS store.shopping_cart (
userid text PRIMARY KEY,
item_count int,
last_update_timestamp timestamp
);

-- Insert some data
INSERT INTO store.shopping_cart
(userid, item_count, last_update_timestamp)
VALUES ('9876', 2, toTimeStamp(now()));
INSERT INTO store.shopping_cart
(userid, item_count, last_update_timestamp)
VALUES ('1234', 5, toTimeStamp(now()));
```

### 4. Load data with cqsl
```
docker run --rm --network cassandra -v "$(pwd)/data.cql:/scripts/data.cql" -e CQLSH_HOST=cassandra -e CQLSH_PORT=9042 -e CQLVERSION=3.4.5 nuvo/docker-cqlsh
```

### 5. Interactive cqlsh
```
docker run -it --network cassandra-network --rm cassandra cqlsh varun-cassandra
```

### Clean up
```
docker kill cassandra
docker network rm cassandra
```

## Notes:
- First find out application requirements, then figure out what queries are needed for those requirements.
- Then design schema for cassandra.
- You can't do joins in cassandra. So instead of putting a foreign_key (say author_id) in your table (say book table), you duplicate the data; meaning just put author details inside the book table itself so that we dont have to do any kind of join to fetch data. You can store author_id inside the book table, this will be just a link to author table where more details can be read. 
- Key: partition key of a table (similar to primary key, but this is used for partitioning the data - this key is used for generating a hash and to decide based on the hash which node to send the data to store)
- Clustering column: ordering is done while writing the data into cassandra. Clustering column is used for that, so while reading we dont have to do much.
- Keyspace: similar to schema in rdbms, helps to keep similar tables together


Create keyspace:
```
CREATE KEYSPACE IF NOT EXISTS betterreads WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : '1' };
```
Show all keyspaces:
```
SELECT * FROM system_schema.keyspaces;
```

Use keyspace:
```
use betterreads;
```

Get tables info:
```
SELECT * FROM system_schema.tables WHERE keyspace_name = 'betterreads';
```

Get table info:
```
SELECT * FROM system_schema.columns
WHERE keyspace_name = 'betterreads' AND table_name = 'author_by_id';
```

### Queries needed for the app:
- 