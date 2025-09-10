package jdbc_boards.vo;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    private int bno;
    private String btitle;
    private String bcontent;
    private String bwriter;
    private Date bdate;

    @Override
    public String toString() {
        return  "bno : " + bno +
                " | btitle : " + btitle +
                " | bcontent : " + bcontent +
                " | bwriter : " + bwriter +
                " | bdate : " + bdate;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Board board = (Board) obj;
        return bno == board.bno; // 또는 필요한 필드들을 비교
    }
}
