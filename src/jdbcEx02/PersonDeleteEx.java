package jdbcEx02;

/*
* JDBC를 이용해서 데이터 삭제를 하는 DELETE 문을 실행하는 방법
* DELETE FROM 테이블명; // 해당 테이블에 있는 모든 행을 다 지움
* DELETE FROM 테이블명 WHERE id = 식별값;  // 해당 식별값의 행만 지운다.
* DELETE FROM person WHERE num = 1;   // person 테이블의 PK num = 1인 행을 삭제한다.
* String sql = "DELETE FROM person WHERE num = ?";
*
* */

import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PersonDeleteEx {
    public static void main(String[] args) {
        Connection con = DBUtil.getConnection();

        String sql = "delete from person where num = ?";
        try(PreparedStatement pstmt = con.prepareStatement(sql)){
            pstmt.setInt(1, 1);
            int rows = pstmt.executeUpdate();
            System.out.println(rows + " rows affected");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
