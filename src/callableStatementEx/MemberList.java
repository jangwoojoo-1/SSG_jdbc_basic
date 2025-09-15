package callableStatementEx;

import util.DBUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class MemberList {
    public static void main(String[] args) {
        Connection conn;

        try{
            conn = DBUtil.getConnection();
            String sql = "{call SP_MEMBER_LIST()}";

            try(CallableStatement cs = conn.prepareCall(sql)){
                System.out.println("[회원 목록]");
                System.out.println("-".repeat(30));
                System.out.println(" no | 아이디 | 비밀번호 | 이메일 | 전화번호 | 가입일 | 포인트");
                cs.execute();

                try (ResultSet rs = cs.getResultSet()){
                    while(rs.next()){
                        System.out.println(" " + rs.getInt(1) + " | " +
                                rs.getString(2) + " | " +
                                rs.getString(3) + " | " +
                                rs.getString(4) + " | " +
                                rs.getString(5) + " | " +
                                rs.getDate(6) + " | " +
                                rs.getInt(7)
                        );
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
