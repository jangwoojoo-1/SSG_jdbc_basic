package jdbcEx01;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;

public class BoardInsertTest {
    public static void main(String[] args) {
        String DriverName = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/bookmarketdb?serverTimezone=Asia/Seoul";
        String userName = "root";
        String password = "mysql1234";
        String sql = "insert into boards(btitle, bcontent, bwriter, bdate, bfilename, bfiledata) values(?, ?, ?, now(), ?, ?)";

        try {
            Class.forName(DriverName);
            System.out.println("드라이버 로드 성공");
        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로드 실패");
        }

        try (Connection con = DriverManager.getConnection(url, userName, password) ;
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            System.out.println("AutoCommit 상태: " + con.getAutoCommit());
            con.setAutoCommit(true);

            pstmt.setString(1, "삼국지");
            pstmt.setString(2, "유비, 조조, 손권");
            pstmt.setString(3, "나관중");
            pstmt.setString(4, "spring.jpg");
            //Blob 대용량 파일
            pstmt.setBlob(5, new FileInputStream("C:/Temp/spring.jpg"));


            // SQL문 실행
            int result = pstmt.executeUpdate();
            System.out.println("저장된 행의 수" + result);
            int bno = -1;

            if (result == 1) {
                try(ResultSet rs = pstmt.getGeneratedKeys()){
                    if (rs.next()) {
                        bno = rs.getInt(1);
                        System.out.println("bno = " + bno);
                    }
                    //rs.close();
                    System.out.println("입력 성공!");
                }
            } else {
                System.out.println("입력 실패!");
            }

            if(bno != -1){
                String selectSql = "SELECT bno, btitle, bcontent, bwriter, bdate, bfilename " +
                        "From boards WHERE bno = ? ";

                try (PreparedStatement selectpstmt = con.prepareStatement(selectSql)) {
                    selectpstmt.setInt(1, bno);
                    try(ResultSet rs = selectpstmt.executeQuery()) {
                        if (rs.next()) {
                            bno = rs.getInt(1);
                            System.out.println("bno = " + bno);
                            System.out.println("btitle = " + rs.getString(2));
                            System.out.println("bcontent = " + rs.getString(3));
                            System.out.println("bwriter = " + rs.getString(4));
                            System.out.println("bdate = " + rs.getTimestamp(5));
                            System.out.println("bfilename = " + rs.getString(6));
                        }
                    }
                }
            }









        } catch (SQLException e) {
            System.out.println("커넥션 실패!");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}
