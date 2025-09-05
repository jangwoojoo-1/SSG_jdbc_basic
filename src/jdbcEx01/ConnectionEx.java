package jdbcEx01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionEx {
    public static void main(String[] args) {
        Connection con = null; //커넥션 인터페이스


        try {
            // 1. DB의 드라이버를 찾아서 로드해야 함. MYSQL JDBC 드라이버 등록
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully!");

            // 2. 드라이버 로드가 OK되었다면, 연결 Connection 객체 생성
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookmarketdb?serverTimezone=Asia/Seoul", "root", "mysql1234");
            System.out.println("Connection established! " + con);

            // 3. Connection 객체가 생성되었다면, 쿼리문을 담아 Statements 객체에 담아 DB에게 전송


            // 4. 전송한 결과를 받아서 처리


        } catch (Exception e) {
            System.out.println("Driver loaded failed!");
        } finally{
            // 5. 다 사용한 Statements와 Connection 객체를 닫는다.
            if(con != null){
                try{
                    con.close();
                    System.out.println("Connection closed!");
                } catch (SQLException e){
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
