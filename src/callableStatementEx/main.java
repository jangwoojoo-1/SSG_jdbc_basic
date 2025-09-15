package callableStatementEx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        MemberDao memberDao = new MemberDao();
        while(true) {
        System.out.println("1.create | 2.SearchAll | 3. SearchOne | 4.Update | 5.Delete");
        System.out.print("번호 선택 :  ");
        int num = Integer.parseInt(br.readLine());
            switch (num) {
                case 1 -> memberDao.memberInsert();
                case 2 -> memberDao.memberList();
                case 3 -> memberDao.memberSearchOne();
                case 4 -> memberDao.memberUpdate();
                case 5 -> memberDao.memberDelete();
                case 0 -> {
                    return;
                }
                default -> System.out.println("숫자 입력이 잘못되었습니다.");
            }
        }
    }
}
