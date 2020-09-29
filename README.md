# Hive Compactor

A simple Hive table compactor utility which fetches all the partitions of a table
and runs compactions on each of them.

# Usage
Runs on JDK 8+
```java
 ~hive-compactor> make build
 ~hive-compactor> java -jar target/hive-compactor-<version>.jar jdbc:hive2://localhost:10000/db user db.table
```

# TODO
* Add support for authentication
* Add support for HiveServer2 ZK endpoint
