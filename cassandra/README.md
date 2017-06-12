# Contributors
## Swatantra Agrawal
### Email: swattu@gmail.com
### LinkedIn: https://in.linkedin.com/in/aazad

# Install Cassandra
# Login to the csqlh
# Execute following
## Create Keyspace
    CREATE  KEYSPACE IF NOT EXISTS testKeySpace WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 } AND DURABLE_WRITES =  true;
## Create Table
    CREATE TABLE IF NOT EXISTS testKeySpace.PERSON ( id text PRIMARY KEY, name text, age int);
