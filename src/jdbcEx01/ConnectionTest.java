package jdbcEx01;

import java.sql.*;

public class ConnectionTest {
    public static void main(String[] args) {
        String DriverName = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/bookmarketdb?serverTimezone=Asia/Seoul";
        String userName = "root";
        String password = "mysql1234";

        try {
            Class.forName(DriverName);
            System.out.println("드라이버 로드 성공");
        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로드 실패");
        }

        try(Connection con = DriverManager.getConnection(url, userName, password)){
            System.out.println("AutoCommit 상태: " + con.getAutoCommit());
            con.setAutoCommit(true);

            Statement stmt = con.createStatement();
            int result = stmt.executeUpdate("insert into person(id, name) values(100, '홍길동')");

            if(result == 1){
                System.out.println("입력 성공!");
            } else{
                System.out.println("입력 실패!");
            }
        }catch (SQLException e) {
            System.out.println("커넥션 실패!");
        }
    }
}
