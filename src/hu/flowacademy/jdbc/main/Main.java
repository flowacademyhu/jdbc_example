package hu.flowacademy.jdbc.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

  public static void main(String[] args) {
    System.out.println("Database connection app");

    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      System.err.println("Database connector not found");
      System.exit(0);
    }

    try (Connection connection = DriverManager
        .getConnection("jdbc:postgresql://127.0.0.1:5432/java_test", "postgres", "123456");) {
      try (PreparedStatement preparedStatement = connection
          .prepareStatement("select id, name, value from test1;");) {

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
          int id = resultSet.getInt("id");
          String name = resultSet.getString("name");
          int value = resultSet.getInt("value");
          System.out.printf("Value is: %d %s %d\n", id, name, value);
        }

      }

      try (PreparedStatement preparedStatement = connection
          .prepareStatement("insert into test1 (id, name, value) values (?, ?, ?);");) {
        preparedStatement.setInt(1, 1);
        preparedStatement.setString(2, "Hello");
        preparedStatement.setInt(3, 55);
        preparedStatement.execute();
      }
    } catch (SQLException e) {
      System.err.println("Connection failed! " + e.getMessage());
    }

  }

}
