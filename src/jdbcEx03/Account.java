package jdbcEx03;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private String id;
    private String name;
    private int balance;

    @Override
    public String toString() {
        return "Account [계좌번호: " + id + ", 계좌주: " + name + ", 계좌금액: " + balance + "]";
    }
}
