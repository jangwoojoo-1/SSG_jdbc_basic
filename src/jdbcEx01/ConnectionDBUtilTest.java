package jdbcEx01;

import jdbcEx01.vo.Person;
import util.DBUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectionDBUtilTest {
    public static void main(String[] args) {
        try{
            Connection con = DBUtil.getConnection();

            Statement stmt = con.createStatement();
            int result = stmt.executeUpdate("insert into person(id, name) values(1000000, '홍길동11')");
            if(result == 1){
                System.out.println("입력 성공");
            }

            String selectSql = "select * from person";
            ResultSet rs = stmt.executeQuery(selectSql);
            while(rs.next()){
                Person person = new Person();
                person.setId(rs.getInt(1));
                person.setName(rs.getString(2));
                System.out.println(person.toString());
            }
        } catch (Exception e){
            System.out.println("커넥션 실패" + e);
        }
    }
}
