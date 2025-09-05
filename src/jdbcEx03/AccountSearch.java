package jdbcEx03;

import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountSearch {
    public static void main(String[] args) {
        Connection con = DBUtil.getConnection();

        String selectSql = "select * from accounts where ano = ?";
        try(PreparedStatement pstmt = con.prepareStatement(selectSql)){
            pstmt.setString(1, "1111-2222-3333-4444");
            try(ResultSet rs = pstmt.executeQuery()){
                while (rs.next()){
                    Account account = new Account();
                    account.setId(rs.getString(1));
                    account.setName(rs.getString(2));
                    account.setBalance(rs.getInt(3));
                    System.out.println(account.toString());
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
