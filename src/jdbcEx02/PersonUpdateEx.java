package jdbcEx02;


// UPDATE 테이블명 SET (필드 = '수정값') where num = 1;
// String sql = "UPDATE person SET id = ?, name = ? where num = ?";

// 하나씩 원하는 것만 추가하는 방식
// String sql = new String Builder()
//              .append("UPDATE person ")
//              .append(" SET id = ? ")
//              .append(", name = ? where num = ? ").toString();


import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PersonUpdateEx {
    public static void main(String[] args) {
        Connection con = DBUtil.getConnection();

        // 매개변수화된 UPDATE SQL로 작성
        //String sql = "UPDATE person SET id = ? , name = ? where num = ?";

        String sql = new StringBuilder().append("UPDATE person SET ")
                .append("id = ? ,")
                .append("name = ? ")
                .append("WHERE num = ?").toString();

        try(PreparedStatement pstmt = con.prepareStatement(sql)){
            pstmt.setInt(1, 150);
            pstmt.setString(2, "도우너");
            pstmt.setInt(3, 1);

            //select 시 executeQuery / insert,delete,update 사용 시 executeUpdate
            int rows = pstmt.executeUpdate();
            System.out.println( rows + " rows updated");

            String selectSql = "SELECT * FROM person WHERE num = ?";

            try(PreparedStatement pstmt2 = con.prepareStatement(selectSql)){
                pstmt2.setInt(1, 1);
                try(ResultSet rs = pstmt2.executeQuery()){
                    while (rs.next()) {
                        System.out.println(rs.getInt("id") + " " + rs.getString("name") + " " + rs.getString("num"));
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
