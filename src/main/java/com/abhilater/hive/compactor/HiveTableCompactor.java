package com.abhilater.hive.compactor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This is a simple Hive table compactor utility which fetches all the partitions of a table
 * and runs compactions on each of them.
 *
 * Usage: Runs on JDK 8
 * ~hive-compactor> make build
 * ~hive-compactor> java -jar target/hive-compactor-1.0-SNAPSHOT.jar jdbc:hive2://localhost:10000/db user db.table
 *
 */
public class HiveTableCompactor {

  private static final Logger LOG = LogManager.getLogger(HiveTableCompactor.class);
  private static final String PARTITIONS_QUERY = "SHOW PARTITIONS %s";
  private static final String MAJOR_COMPACT_QUERY = "ALTER TABLE %s PARTITION(%s) COMPACT 'major'";

  static {
    try {
      Class.forName("org.apache.hive.jdbc.HiveDriver");
    } catch (ClassNotFoundException e) {
      LOG.error("Hive driver failed to load", e);
      System.exit(-1);
    }
  }

  public static void main(String[] args) throws SQLException {
    if (args.length < 2) {
      throw new IllegalArgumentException(
          "Args must be of the form jdbc:hive2://<host>:<port>/<db> <user> <table>");
    }
    String jdbcUrl = args[0];
    String user = args[1];
    String table = args[2];
    Properties props = new Properties();
    props.setProperty("user", user);

    try (Connection con = DriverManager.getConnection(jdbcUrl, props);
        Statement partitionsStmt = con.createStatement();
        Statement alterStmt = con.createStatement()) {
      String sql = String.format(PARTITIONS_QUERY, table);
      LOG.info("Running query: " + sql);
      ResultSet rs = partitionsStmt.executeQuery(sql);

      while (rs.next()) {
        runCompactForPartition(table, rs.getString(1), alterStmt);
      }
    }
  }

  private static void runCompactForPartition(String table, String partition, Statement alterStmt)
      throws SQLException {
    String partitionClause = createPartitionClause(partition);
    String sql2 = String.format(MAJOR_COMPACT_QUERY, table, partitionClause);
    LOG.info("Running query: " + sql2);
    int execute = alterStmt.executeUpdate(sql2);
    LOG.info("Alter query status: "+ (execute == 0 ? "Success!" : "Failed"));
  }

  private static String createPartitionClause(String partition) {
    return Arrays.stream(partition.split("\\/"))
        .map(p -> p.split("=")[0] + "='" + p.split("=")[1] + "'")
        .collect(Collectors.joining(","));
  }

}
