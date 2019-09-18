package com.loganalyzer.db;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class HSQLDBConnection {

    private static Connection connection = null;

    public static Connection getConnection(){
        if (connection == null)
            createConnection();
        return connection;
    }

    public static void createConnection() {
        try {
            Properties properties = new Properties();
            properties.load(Objects.requireNonNull(HSQLDBConnection.class.getClassLoader().getResourceAsStream("database.properties")));
            Class.forName(properties.getProperty("driverName"));
            System.out.println("HSQLDB JDBCDriver Loaded");
            connection = DriverManager.getConnection(
                    properties.getProperty("host"), properties.getProperty("user"), properties.getProperty("password"));
            System.out.println("HSQLDB Connection Created");
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
