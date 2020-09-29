# Hive Compactor

A simple Hive table compactor utility which fetches all the partitions of a table
and runs compactions on each of them.

# Usage
Builds on Maven 3+ and Runs on JDK 8+
```java
 ~hive-compactor> make build
 ~hive-compactor> java -jar hive-compactor-<version>.jar jdbc:hive2://localhost:10000/db user db.table

SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
18:33:00.558 [main] INFO  main - Running query: SHOW PARTITIONS db.table
18:33:01.837 [main] INFO  main - Running query: ALTER TABLE db.table PARTITION(year='2018',month='09') COMPACT 'major'
18:33:02.591 [main] INFO  main - Alter query status: Success!
18:33:02.591 [main] INFO  main - Running query: ALTER TABLE db.table PARTITION(year='2018',month='10') COMPACT 'major'
18:33:03.589 [main] INFO  main - Alter query status: Success!
18:33:03.590 [main] INFO  main - Running query: ALTER TABLE db.table PARTITION(year='2018',month='11') COMPACT 'major'
18:33:04.536 [main] INFO  main - Alter query status: Success!
18:33:04.537 [main] INFO  main - Running query: ALTER TABLE db.table PARTITION(year='2018',month='12') COMPACT 'major'
18:33:05.538 [main] INFO  main - Alter query status: Success!
18:33:05.538 [main] INFO  main - Running query: ALTER TABLE db.table PARTITION(year='2019',month='01') COMPACT 'major'
18:33:06.467 [main] INFO  main - Alter query status: Success!
18:33:06.468 [main] INFO  main - Running query: ALTER TABLE db.table PARTITION(year='2019',month='02') COMPACT 'major'
18:33:07.402 [main] INFO  main - Alter query status: Success!
18:33:07.403 [main] INFO  main - Running query: ALTER TABLE db.table PARTITION(year='2019',month='03') COMPACT 'major'
18:33:08.296 [main] INFO  main - Alter query status: Success!
18:33:08.297 [main] INFO  main - Running query: ALTER TABLE db.table PARTITION(year='2019',month='04') COMPACT 'major'
```

# TODO
* Add support for authentication
* Add support for HiveServer2 ZK endpoint
