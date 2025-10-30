

# SSG JDBC Basic 프로젝트

이 프로젝트는 Java의 **JDBC(Java Database Connectivity)** 기술을 학습하고 실습하기 위한 예제 모음입니다.
기본적인 데이터베이스 연결부터 CRUD 작업, 트랜잭션 관리, 그리고 저장 프로시저(Stored Procedure) 호출에 이르기까지 JDBC의 핵심 기능들을 다룹니다.

## 📚 주요 학습 내용

  * **JDBC 드라이버 로딩 및 연결**: `DriverManager`를 사용한 기본 데이터베이스 연결
  * **Statement 활용**:
      * `Statement`: 기본적인 SQL 쿼리 실행
      * `PreparedStatement`: 파라미터화된 SQL을 사용하여 SQL Injection을 방지하고 성능을 향상시키는 방법
      * `CallableStatement`: MySQL 저장 프로시저(Stored Procedure) 호출 및 OUT 파라미터 처리
  * **CRUD 구현**:
      * **C**reate: `INSERT` (데이터 생성)
      * **R**ead: `SELECT` (데이터 조회)
      * **U**pdate: `UPDATE` (데이터 수정)
      * **D**elete: `DELETE` (데이터 삭제)
  * **DB 연결 유틸리티**: `ResourceBundle`과 `.properties` 파일을 활용하여 DB 연결 정보를 외부에서 관리하는 `DBUtil` 클래스 구현
  * **미니 프로젝트**:
      * **게시판 (Boards)**: 콘솔 기반의 간단한 CRUD 게시판
      * **회원 관리 (Members)**: 저장 프로시저를 연동한 회원 관리 기능
  * **SQL 스크립트**:
      * 저장 프로시저(SP) 생성 및 사용
      * 데이터베이스 트리거(Trigger) 예제

## 📂 프로젝트 구조

주요 소스 코드는 `src` 디렉터리 내에 패키지별로 구분되어 있습니다.

  * `jdbcEx01/`: JDBC 기본 연결 및 `Statement`, `PreparedStatement`를 사용한 간단한 Insert/Select 예제.
  * `jdbcEx02/`: `PreparedStatement`를 사용한 Update, Delete 예제.
  * `jdbcEx03/`: 'Account' 객체를 사용한 CRUD 예제.
  * `jdbc_boards/`: 콘솔 기반의 게시판 미니 프로젝트.
      * `view/Main.java`: 프로그램 시작점.
      * `Controller/BoardMenu.java`: 사용자 메뉴 및 입력 처리.
      * `model/BoardDAO.java`: 데이터베이스 로직 처리 (DAO).
      * `vo/Board.java`: 게시글 데이터 객체 (VO).
      * `util/DBUtil.java`: 게시판 전용 DB 연결 유틸.
  * `callableStatementEx/`: `CallableStatement`를 사용한 저장 프로시저 호출 예제.
      * `main.java`: 프로그램 시작점.
      * `MemberDao.java`: 멤버 관련 저장 프로시저 호출 로직 (DAO).
      * `procedure.sql`: 회원 관리를 위한 저장 프로시저 SQL.
      * `trigger.sql`, `Nested_trigger.sql`: 트리거 예제 SQL.
  * `util/`: 공용 DB 연결 유틸리티 (`DBUtil.java`) 및 설정 파일 (`dbinfo.properties`).

## ⚙️ 실행 환경 및 설정

### 1\. 요구 사항

  * **Java**: JDK 17
  * **Database**: MySQL
  * **Libraries**:
      * MySQL Connector/J (JDBC 드라이버)
      * Lombok (VO 객체용)

### 2\. 데이터베이스 설정

1.  MySQL 서버에 `bookmarketdb` 스키마(데이터베이스)를 생성합니다.
2.  `src/callableStatementEx/` 경로의 `.sql` 파일들(`procedure.sql`, `trigger.sql` 등)을 실행하여 필요한 테이블과 저장 프로시저를 생성합니다.

### 3\. 연결 정보 설정

이 프로젝트에는 두 개의 DB 설정 파일이 있습니다. 사용하는 예제에 맞게 수정해야 합니다.

  * **공용 설정**: `src/util/dbinfo.properties`
  * **게시판 앱 설정**: `src/jdbc_boards/util/dbinfo.properties`

      * *참고: 위 파일들은 각기 다른 DB 유저(`root`, `bookadmin`)를 사용하고 있습니다. 본인의 MySQL 환경에 맞게 아이디와 비밀번호를 수정하세요.*

## ▶️ 실행 방법

각 예제는 `main` 메소드를 포함하고 있어 개별적으로 실행할 수 있습니다.

  * **게시판 프로젝트 실행**: `src/jdbc_boards/view/Main.java` 실행
  * **저장 프로시저 예제 실행**: `src/callableStatementEx/main.java` 실행
  * **기타 예제**: `jdbcEx01`, `jdbcEx02`, `jdbcEx03` 패키지 내의 Java 파일들을 각각 실행

-----
