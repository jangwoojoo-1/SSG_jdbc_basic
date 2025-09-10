package jdbc_boards.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBUtil {
    private static ResourceBundle bundle;

    static {
        bundle = ResourceBundle.getBundle("jdbc_boards.util.dbinfo_mysql");
        try{
            Class.forName(bundle.getString("driver"));
            System.out.println("Driver loaded");
        } catch (ClassNotFoundException e) {
            System.out.println("driver not found!");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try{
            return DriverManager.getConnection(
                    bundle.getString("url"),
                    bundle.getString("user"),
                    bundle.getString("password")
            );
        } catch (SQLException e){
            System.out.println("Connection error!");
            return null;
        }
    }
}
