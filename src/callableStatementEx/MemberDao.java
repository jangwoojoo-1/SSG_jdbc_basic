package callableStatementEx;

import util.DBUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDao {
    Connection conn = conn = DBUtil.getConnection();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public void memberInsert() {
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

    public void memberList() {
        try{
            String sql = "{call SP_MEMBER_LIST(?)}";

            try(CallableStatement cs = conn.prepareCall(sql)){
                System.out.println("[회원 목록]");
                System.out.println("-".repeat(30));
                System.out.println(" no | 아이디 | 비밀번호 | 이메일 | 전화번호 | 가입일 | 포인트");

                //OUT 파라미터 세팅
                cs.registerOutParameter(1, java.sql.Types.INTEGER);

                // 실행
                cs.execute();

                int result = cs.getInt(1);

                if(result == 100){
                    conn.rollback();
                    System.out.println("회원 데이터가 존재하지 않습니다.");
                } else{
                    System.out.println("[회원 리스트]");
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
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void memberSearchOne() {
        try{
            String sql = "{call SP_MEMBER_SEARCH_ONE(?, ?)}";

            try(CallableStatement cs = conn.prepareCall(sql)){
                System.out.println("유저 아이디 입력 : ");
                String id = br.readLine();

                System.out.println("-".repeat(30));
                System.out.println(" no | 아이디 | 비밀번호 | 이메일 | 전화번호 | 가입일 | 포인트");
                cs.setString(1, id);

                //OUT 파라미터 세팅
                cs.registerOutParameter(1, java.sql.Types.INTEGER);

                // 실행
                cs.execute();

                int result = cs.getInt(2);

                if(result == 100){
                    conn.rollback();
                    System.out.println("회원이 존재하지 않습니다.");
                } else{
                    System.out.println("[회원]");
                    try (ResultSet rs = cs.getResultSet()) {
                        if (rs.next()){
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
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void memberUpdate() {
        try{
            String sql = "{call SP_MEMBER_Update(?,?,?,?)}";

            try(CallableStatement cs = conn.prepareCall(sql)){
                System.out.println("유저 아이디 입력 : ");
                String id = br.readLine();

                System.out.println("[1.비밀번호 | 2.이메일 | 3.연락처] : ");
                int select_num = Integer.parseInt(br.readLine());

                System.out.println("수정 메세지 : ");
                String content = br.readLine();

                cs.setString(1, id);
                cs.setInt(2, select_num);
                cs.setString(3, content);

                //OUT 파라미터 세팅
                cs.registerOutParameter(1, java.sql.Types.INTEGER);

                // 실행
                cs.execute();

                int result = cs.getInt(4);

                if(result == 100){
                    conn.rollback();
                    System.out.println("수정 실패!");
                } else {
                    System.out.println("수정 성공!");
                }
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void memberDelete() {
        try{
            String sql = "{call SP_MEMBER_DELETE(?, ?)}";

            try(CallableStatement cs = conn.prepareCall(sql)){
                System.out.println("유저 아이디 입력 : ");
                String id = br.readLine();

                System.out.println("-".repeat(30));
                cs.setString(1, id);

                //OUT 파라미터 세팅
                cs.registerOutParameter(1, java.sql.Types.INTEGER);

                // 실행
                cs.execute();

                int result = cs.getInt(2);

                if(result == 100){
                    conn.rollback();
                    System.out.println("회원 삭제 실패!");
                } else{
                    System.out.println("회원이 삭제되었습니다.");
                }
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
