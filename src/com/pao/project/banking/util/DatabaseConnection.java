package com.pao.project.banking.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() throws IOException, SQLException {
        Properties props = new Properties();
        
        String caleaProiectului = "resources/db.properties";
        
        try (java.io.FileInputStream fis = new java.io.FileInputStream(caleaProiectului)) {
            props.load(fis);
        } catch (IOException e) {
            throw new IOException("VS Code nu gaseste fisierul la calea: " + caleaProiectului + ". Verifica daca folderul 'resources' este direct in radacina proiectului.", e);
        }
        
        String url  = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String pass = props.getProperty("db.password");

        this.connection = DriverManager.getConnection(url, user, pass);

        try (var stmt = connection.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON");
        } catch (SQLException ignored) {
            // Ignorat pe MySQL
        }
    }

    public static synchronized DatabaseConnection getInstance()
            throws IOException, SQLException {
        if (instance == null || instance.connection.isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}