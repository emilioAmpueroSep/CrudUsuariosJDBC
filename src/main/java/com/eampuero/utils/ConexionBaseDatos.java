package com.eampuero.utils;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConexionBaseDatos {
    private static String url = "jdbc:mysql://localhost:3306/java_curso?serverTimeZone=America/Santiago";
    private static String username = "root";
    private static String password = "qwerty";
    private static BasicDataSource pool;

    public static BasicDataSource getInstance() {
        if (pool == null) {
            pool = new BasicDataSource();
            pool.setUrl(url);
            pool.setUsername(username);
            pool.setPassword(password);
            pool.setInitialSize(2);
            pool.setMinIdle(3);
            pool.setMaxIdle(8);
            pool.setMaxTotal(10);
        }
        return pool;
    }

    public static Connection getConnection() throws SQLException {
        return getInstance().getConnection();
    }

}
