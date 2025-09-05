package jdbcEx01;

import java.sql.*;

public class AccountInsert {
    public static void main(String[] args) {
        String DriverName = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/bookmarketdb?serverTimezone=Asia/Seoul";
        String userName = "root";
        String password = "mysql1234";
        String sql = "i nsert into accounts(ano, owner, balance) values(?, ?, ?)";

        try {
            Class.forName(DriverName);
            System.out.println("드라이버 로드 성공");
        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로드 실패");
        }

        try (Connection con = DriverManager.getConnection(url, userName, password);
             PreparedStatement pstmt = con.prepareStatement(sql);
        ) {
            System.out.println("AutoCommit 상태: " + con.getAutoCommit());
            con.setAutoCommit(true);

            pstmt.setString(1, "1111-1212-3232-12121");
            pstmt.setString(2, "홍길동");
            pstmt.setInt(3, 100000);

            // SQL문 실행
            int result = pstmt.executeUpdate();
            System.out.println("저장된 행의 수" + result);

            if (result == 1) {
                System.out.println("입력 성공!");

                String ano = "1111-1212-3232-12121";
                String selectSql = "SELECT * " +
                        "From accounts WHERE ano = ? ";

                try (PreparedStatement selectpstmt = con.prepareStatement(selectSql)) {
                    selectpstmt.setString(1, ano);
                    try (ResultSet rs = selectpstmt.executeQuery()) {
                        System.out.println("==========결과===========");
                        if (rs.next()) {
                            System.out.println("계좌번호: " + rs.getString(1));
                            System.out.println("계좌주: " + rs.getString(2));
                            System.out.println("계좌총액: " + rs.getInt(3));
                        }
                    }
                }
            } else {
                System.out.println("입력 실패!");
            }
        } catch (SQLException e) {
            System.out.println("커넥션 실패!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
