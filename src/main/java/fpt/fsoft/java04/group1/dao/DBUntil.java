package fpt.fsoft.java04.group1.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUntil {
    private String host = "localhost";
    private String port = "1433";
    private String user = "thanhhai";
    private String pass = "1234567";

    private String databaseDefault = "present";

    public DBUntil() {
    }

    public DBUntil(String host, String port, String user, String pass, String databaseDefault) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.pass = pass;
        this.databaseDefault = databaseDefault;
    }

    public Connection getCon() {
        String connectionUrl = String.format("jdbc:sqlserver://%s:%s;databaseName=%s;encrypt=true;trustServerCertificate=true",
                this.host, this.port, this.databaseDefault);
//        System.out.println(connectionUrl);
        Connection connection = null;
//        try {
//            connection = DriverManager.getConnection(connectionUrl, this.user, this.pass);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//        }
        try {
            connection = DriverManager.getConnection(connectionUrl, this.user, this.pass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static void main(String[] args) {
        DBUntil dbUntil = new DBUntil("localhost","1433","quangnguyen", "minhquang2908", "present");
        System.out.println(dbUntil.getCon());
    }
}
