package callableStatementEx;

import util.DBUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class MeberInsert {
    static Connection conn = conn = DBUtil.getConnection();

    public static void main(String[] args) {
        String m_userid = "lm10";
        String m_pwd = "1010";
        String m_email = "lm10@gmail.com";
        String m_hp = "010-1010-1010";
        String sql = "{call SP_MEMBER_INSERT(?,?,?,?,?)}";

        try(CallableStatement cstmt = conn.prepareCall(sql)){
            // In 파라미터 세팅
            cstmt.setString(1, m_userid);
            cstmt.setString(2, m_pwd);
            cstmt.setString(3, m_email);
            cstmt.setString(4, m_hp);

            //OUT 파라미터 세팅
            cstmt.registerOutParameter(5, java.sql.Types.INTEGER);

            // 실행
            cstmt.execute();

            int result = cstmt.getInt(5);

            if(result == 100){
                conn.rollback();
                System.out.println("이미 가입된 사용자입니다.");
            } else{
                //conn.commit();
                System.out.println("회원 가입이 되었습니다. 감사합니다.");
            }
        }catch (SQLException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
