package jdbc_boards.Controller;

import jdbc_boards.model.BoardDAO;
import jdbc_boards.vo.Board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BoardMenu {
    BoardDAO dao;
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    public BoardMenu(){
        dao = new BoardDAO();
    }

    public void boardMenu() throws IOException {
        while(true) {
            System.out.println("메인 메뉴: 1.Create | 2. SearchAll | 3.Search | 4.Update | 5.Delete | 0.Exit");
            System.out.print("메뉴 선택:  ");
            int choice = 0;
            try {
                choice = Integer.parseInt(input.readLine());
            } catch (IOException e) {
                System.out.println("입력도중 에러 발생");
            } catch (NumberFormatException e1) {
                System.out.println("숫자만 입력하라니까");
            } catch (Exception e2) {
                System.out.println("꿰엑 에라 모르겠다.");
            }
            switch (choice) {
                case 1:
                    //사용자에게 title,content를 입력받아서 Board 구성하여 createBoard()넘겨주자
                    Board row = boardDataInput();
                    boolean ack = dao.createBoard(row);
                    if (ack == true) System.out.println("글이 성공적으로 입력되었습니다.");
                    else {
                        System.out.println("입력 실패, 다시 시도 부탁드립니다. ");
                        //원하는 위치로 이동
                    }
                    break;
                case 2:
                    List<Board> boardList = dao.searchAll();
                    if (boardList != null) {
                        System.out.println("글 목록을 성공적으로 불러왔습니다.");
                        System.out.println("-".repeat(30));
                        System.out.println("글 목록");
                        boardList.stream().forEach(board -> {
                            System.out.println(board.toString());
                        });
                    } else {
                        System.out.println("목록 읽어오기 실패, 다시 시도 부탁드립니다. ");
                        //원하는 위치로 이동
                    }
                    break;
                case 3:
                    int no = boardSelect();
                    Board selectBoard = dao.searchOne(no);
                    if (selectBoard != null) {
                        System.out.println("글이 성공적으로 검색되었습니다.");
                        System.out.println(selectBoard.toString());
                    } else {
                        System.out.println("검색 실패, 다시 시도 부탁드립니다. ");
                        //원하는 위치로 이동
                    }
                    break;
                case 4:
//                    no = boardSelect();
//                    Board updateBoard = dao.updateBoard(no);
//                    if (updateBoard != null) {
//                        System.out.println("글이 성공적으로 수정되었습니다.");
//                        System.out.println(updateBoard.toString());
//                    } else {
//                        System.out.println("수정 실패, 다시 시도 부탁드립니다. ");
//                        //원하는 위치로 이동
//                    }
//                    break;
                    Board board = boardDataUpdate();
                    ack = dao.updateBoard(board);
                    if (ack == true) System.out.println("글이 성공적으로 수정되었습니다.");
                    else {
                        System.out.println("수정 실패, 다시 시도 부탁드립니다. ");
                        //원하는 위치로 이동
                    }
                    break;
                case 5:
                    no = boardSelect();
                    ack = dao.deleteBoard(no);
                    if (ack == true) {
                        System.out.println("글이 성공적으로 삭제되었습니다.");
                    } else {
                        System.out.println("삭제 실패, 다시 시도 부탁드립니다. ");
                        //원하는 위치로 이동
                    }
                    break;
                case 0:
                    System.out.println("프로그램이 종료됩니다.");
                    return;
                    default:
                        System.out.println("입력이 잘못되었습니다.");

            }
        }
    }

    public Board boardDataInput() throws IOException{
        Board board = new Board();
        System.out.println("[새로운 글 입력]");
        System.out.print("제목 입력 : ");
        String title =input.readLine();
        board.setBtitle(title);
        System.out.print("내용 입력\n > ");
        String content = input.readLine();
        board.setBcontent(content);
        System.out.print("작성자 입력 : ");
        String writer = input.readLine();
        board.setBwriter(writer);
        return board;
    }

    public int boardSelect() throws IOException{
        Board board = new Board();
        System.out.println("[글 선택]");
        System.out.print("글 번호 입력 :  ");
        int no = Integer.parseInt(input.readLine());
        return no;
    }

    public Board boardDataUpdate() throws IOException {
        Board board = new Board();
        System.out.println("[글 수정 입력]");
        System.out.print("글 번호 입력 :  ");
        int no = Integer.parseInt(input.readLine());
        board.setBno(no);
        System.out.print("제목 입력 :  ");
        String title =input.readLine();
        board.setBtitle(title);
        System.out.print("내용 입력\n > ");
        String content = input.readLine();
        board.setBcontent(content);
//        System.out.println("작성자 입력");
//        String writer = br.readLine();
//        board.setBwriter(writer);
        return board;
    }
}
