package jdbcEx01;

import java.sql.*;

public class UserInsertTest {
    public static void main(String[] args) {
        String DriverName = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/bookmarketdb?serverTimezone=Asia/Seoul";
        String userName = "root";
        String password = "mysql1234";
        String sql1 = "insert into users(userid, username, userpassword, userage, useremail) values(?, ?, ?, ?, ?)";

        try {
            Class.forName(DriverName);
            System.out.println("드라이버 로드 성공");
        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로드 실패");
        }

        try (Connection con = DriverManager.getConnection(url, userName, password) ;
            PreparedStatement pstmt = con.prepareStatement(sql1)
        ) {
            System.out.println("AutoCommit 상태: " + con.getAutoCommit());
            con.setAutoCommit(true);

            pstmt.setString(1, "2");
            pstmt.setString(2, "주장우");
            pstmt.setString(3, "1234");
            pstmt.setInt(4, 27);
            pstmt.setString(5, "jjw@gmail.com");

            // SQL문 실행
            int result = pstmt.executeUpdate();
            System.out.println("저장된 행의 수" + result);

            if (result == 1) {
                System.out.println("입력 성공!");
            } else {
                System.out.println("입력 실패!");
            }
        } catch (SQLException e) {
            System.out.println("커넥션 실패!");
        }
    }

}
