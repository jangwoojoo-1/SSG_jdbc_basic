package jdbc_boards.model;

import jdbc_boards.vo.Board;
import util.DBUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import static java.time.LocalTime.now;

public class BoardDAO {
    private Connection conn;
    List<Board> boardList = new ArrayList<>();

    // 글 등록 기능
    public boolean createBoard(Board board){
        // 1. Connection 필요
        conn = DBUtil.getConnection();

        // 2. 쿼리 생성
        String sql = "insert into boardTable(btitle, bcontent, bwriter, bdate) values(?, ?, ?, now())";

        // 3. Connection 쿼리를 담아 DB 서버로 request 할 객체 PreparedStatement 생성
        try(PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            // 4. 값을 세팅
            pstmt.setString(1, board.getBtitle());
            pstmt.setString(2, board.getBcontent());
            pstmt.setString(3, board.getBwriter());

            // 5. 서버로 전송 후 결과 값 ( int 성공 1, 실패 0 )
            int result = pstmt.executeUpdate();
            boolean ok = result > 0;

            // 생성된 PK를 Board 객체에 반영
            // 시간도 Board 객체에 반영
            // 이후 arraylist에 저장
//            try(ResultSet rs = pstmt.getGeneratedKeys()) {
//                if(rs.next()){
//                    int newbno = rs.getInt(1);
//                    board.setBno(newbno);
//                    Date newDate = rs.getDate("bdate");
//                    board.setBdate(newDate);
//
//                    boardList.add(board);
//                }
//            }
            return ok;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }  finally{
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    // 글 전체 검색 기능
    public List<Board> searchAll(){
        // 1. Connection 필요
        conn = DBUtil.getConnection();

        // 2. 쿼리 생성
        String sql = "select * from boardTable";

        // 3. Connection 쿼리를 담아 DB 서버로 request 할 객체 PreparedStatement 생성
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            // 4. 값을 세팅
            // 생략

            // 5. 글 Board 객체에 저장 후 반환
            try(ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                    Board board = new Board();
                    board.setBno(rs.getInt(1));
                    board.setBtitle(rs.getString(2));
                    board.setBcontent(rs.getString(3));
                    board.setBwriter(rs.getString(4));
                    board.setBdate(rs.getDate(5));
                    if(!boardList.contains(board)) boardList.add(board);
                }
            }
            return boardList;

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }  finally{
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // 글 하나 검색 기능
    public Board searchOne(int bno){
        // 1. Connection 필요
        conn = DBUtil.getConnection();

        // 2. 쿼리 생성
        String sql = "select * from boardTable where bno = ?";

        // 3. Connection 쿼리를 담아 DB 서버로 request 할 객체 PreparedStatement 생성
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            // 4. 값을 세팅
            pstmt.setInt(1, bno);

            // 5. 글 Board 객체에 저장 후 반환
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    Board board = new Board();
                    board.setBno(rs.getInt(1));
                    board.setBtitle(rs.getString(2));
                    board.setBcontent(rs.getString(3));
                    board.setBwriter(rs.getString(4));
                    board.setBdate(rs.getDate(5));
                    return board;
                }
                return null;
            }

        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }  finally{
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // 글 수정 (제목, 내용만 수정)
    public boolean updateBoard(Board board) {
        conn = DBUtil.getConnection();
        String sql = "UPDATE boardTable SET btitle = ? , bcontent = ? WHERE bno = ? ";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, board.getBtitle());
            pstmt.setString(2, board.getBcontent());
            pstmt.setInt(3, board.getBno());
            int ack =  pstmt.executeUpdate();
            if (ack > 0)  return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

//    // 글 수정 기능
//    public Board updateBoard(int bno){
//        // 1. Connection 필요
//        conn = DBUtil.getConnection();
//
//        // 2. 쿼리 생성
//        String sql = "update boardTable set btitle = ?, bcontent = ?, bwriter = ?, bdate = now() where bno = ?";
//
//        // 3. Connection 쿼리를 담아 DB 서버로 request 할 객체 PreparedStatement 생성
//        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
//            // 4. 값을 세팅
//            Board board = boardDataUpdate();
//
//            pstmt.setString(1, board.getBtitle());
//            pstmt.setString(2, board.getBcontent());
//            pstmt.setString(3, board.getBwriter());
//            pstmt.setInt(4, bno);
//
//            // 5. update 후 결과 값에 따라 객체 반환
//            int result = pstmt.executeUpdate();
//            if(result > 0) {
//                boardList.set(bno-1, board);
//                return board;
//            }
//            else return null;
//
//        }catch (SQLException e){
//            e.printStackTrace();
//            return null;
//        }  finally{
//            try {
//                conn.close();
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//
//    public Board boardDataUpdate() {
//        Board board = new Board();
//        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
//            System.out.println("글 수정 입력");
//            System.out.println("제목 입력");
//            String title =br.readLine();
//            board.setBtitle(title);
//            System.out.println("내용 입력");
//            String content = br.readLine();
//            board.setBcontent(content);
//            System.out.println("작성자 입력");
//            String writer = br.readLine();
//            board.setBwriter(writer);
//            return board;
//        } catch (IOException e){
//            e.printStackTrace();
//            return null;
//        }
//    }

    // 글 삭제 기능
    public boolean deleteBoard(int bno){
        // 1. Connection 필요
        conn = DBUtil.getConnection();

        // 2. 쿼리 생성
        String sql = "delete from boardTable where bno = ?";

        // 3. Connection 쿼리를 담아 DB 서버로 request 할 객체 PreparedStatement 생성
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            // 4. 값을 세팅
            pstmt.setInt(1, bno);

            // 5. update 후 결과 값에 따라 객체 반환
            int result = pstmt.executeUpdate();

            if(result > 0) {
                boardList.remove(bno-1);
                return true;
            }
            else return false;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }  finally{
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
