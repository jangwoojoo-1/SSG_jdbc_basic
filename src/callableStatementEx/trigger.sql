use bookmarketdb;

-- 샘플 테이블 생성
create table CODE1(
    CID int, cName VARCHAR(50)
);

DESC CODE1;

INSERT INTO CODE1 (cid, cname)
select ifNULL(max(cid), 0)+1 as cld2, 'TEST' as cName2
from CODE1;

select * from code1;

truncate code1;



-- 프로시저 생성 : P_INSERTCODES()
delimiter $$
create procedure P_INSERTCODES(in cData varchar(255), in cTname varchar(255), out resultMsg varchar(255))
begin
    set @strsql = concat('insert into ', cTname, '(cid, cname) ',
                        -- null 값이 아닌 최초의 값 반환하는 함수 coalesce
                         'select coalesce(max(cid),0)+1,? from ', cTname);

    -- 바인딩할 변수 설정
    set @cData = cData;
    set resultMsg = 'Insert Success!';

    prepare stmt from @strsql;
    execute stmt using @cData;

    deallocate prepare stmt;
    commit;
end $$
delimiter ;

set @result = '';
select @result;
call P_INSERTCODES('프론트디자이너', 'code1', @result);

CREATE TABLE TB_MEMBER (
                           m_seq INT AUTO_INCREMENT PRIMARY KEY,  -- 자동 증가 시퀀스
                           m_userid VARCHAR(20) NOT NULL,
                           m_pwd VARCHAR(20) NOT NULL,
                           m_email VARCHAR(50) NULL,
                           m_hp VARCHAR(20) NULL,
                           m_registdate DATETIME DEFAULT NOW(),  -- 기본값: 현재 날짜와 시간
                           m_point INT DEFAULT 0
);


delimiter $$
CREATE PROCEDURE SP_MEMBER_INSERT(
    IN V_USERID VARCHAR(20),
    IN V_PWD VARCHAR(20),
    IN V_EMAIL VARCHAR(50),
    IN V_HP VARCHAR(20),
    OUT RTN_CODE INT
)
begin
    declare v_count int;

    select count(m_seq) into v_count from TB_MEMBER where m_userid = V_USERID;
    if v_count > 0 then
        set RTN_CODE = 100;
    else
        insert into TB_MEMBER(m_userid, m_pwd, m_email, m_hp) values(V_USERID, V_PWD, V_EMAIL, V_HP);
        set RTN_CODE = 200;
    end if;
    commit;
end $$
delimiter ;

call SP_MEMBER_INSERT('pear1111', '1111', 'pear@sample.com', '010-9779-9999', @result);
select @result;

select * from TB_MEMBER;

show create procedure SP_MEMBER_INSERT;

drop procedure SP_MEMBER_LIST;

delimiter $$
create procedure SP_MEMBER_LIST(OUT RTN_CODE INT)
begin
    declare v_count int;

    select count(m_seq) into v_count from TB_MEMBER;
    if v_count = 0 then
        set RTN_CODE = 100;
    else
        select * from TB_MEMBER;
        set RTN_CODE = 200;
    end if;
end $$
delimiter ;

drop procedure SP_MEMBER_SEARCH_ONE;
delimiter $$
create procedure SP_MEMBER_SEARCH_ONE(in userid varchar(50), OUT RTN_CODE INT)
begin
    declare v_count int;

    select count(m_userid) into v_count from TB_MEMBER where m_userid = userid;
    if v_count = 0 then
        set RTN_CODE = 100;
    else
        select * from TB_MEMBER where m_userid = userid;
        set RTN_CODE = 200;
    end if;
end $$
delimiter ;

drop procedure SP_MEMBER_UPDATE;

delimiter $$
create procedure SP_MEMBER_UPDATE(in userid varchar(50),
                                  in select_num int,
                                  in update_msg varchar(255),
                                  OUT RTN_CODE INT)
begin
    case
        when select_num = 1 then
            update tb_member set m_pwd = update_msg where m_userid = userid;
            set RTN_CODE = 200;
        when select_num = 2 then
            update tb_member set m_email = update_msg where m_userid = userid;
            set RTN_CODE = 200;
        when select_num = 3 then
            update tb_member set m_hp = update_msg where m_userid = userid;
            set RTN_CODE = 200;
        else set rtn_code = 100;
    end case;
    commit;
end $$
delimiter ;

drop procedure SP_MEMBER_DELETE;
delimiter $$
create procedure SP_MEMBER_DELETE(in userid varchar(50), OUT RTN_CODE INT)
begin
    declare v_count int;

    select count(m_userid) into v_count from TB_MEMBER where m_userid = userid;
    if v_count = 0 then
        set RTN_CODE = 100;
    else
        delete from TB_MEMBER where m_userid = userid;
        set RTN_CODE = 200;
    end if;
end $$
delimiter ;

select * from tb_member;



-- ------------------------------------ 실습 테이블 --------------------------

-- 실습테이블 :  제품로그 테이블 => 제품이 추가할때마다 로그 테이블에 정보를 남기는 트리거 작성
CREATE TABLE 제품(
                   제품번호 INT AUTO_INCREMENT PRIMARY KEY,
                   제품명 VARCHAR(100),
                   포장단위 INT DEFAULT 0,
                   단가  INT DEFAULT 0,
                   재고 INT DEFAULT 0
);

CREATE TABLE 제품로그 (
                      로그번호 INT AUTO_INCREMENT PRIMARY KEY ,
                      처리 VARCHAR(10),
                      내용 VARCHAR(100),
                      처리일 TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DELIMITER $$
CREATE TRIGGER  trigger_제품추가로그
    AFTER INSERT ON 제품
    FOR EACH ROW
BEGIN
    INSERT INTO 제품로그(처리 , 내용) VALUES ('INSERT',CONCAT('제품번호 :' , NEW.제품번호,'제품명:',NEW.제품명));

END $$
DELIMITER ;

INSERT INTO 제품 (제품명,단가,재고) VALUES ('애플캔디',2000,10);
SELECT * FROM 제품
WHERE 제품번호 = 1;

SELECT * FROM 제품로그 ;

-- 제품 테이블에서 단가나 재고가 변경되면 변경된 사항을 제품로그 테이블에 저장하는 트리거를 생성
delimiter $$
create trigger trigger_제품변경로그
    After update on 제품
    for each row
begin
    if(new.단가 <> old.단가) then
        insert into 제품로그(처리, 내용) values ('update', concat('제품번호 :', old.제품번호,  '단가: ', old.단가, '->', NEW.단가));
    end if ;

    if(new.재고 <> old.재고) then
        insert into 제품로그(처리, 내용) values ('update', concat('제품번호 : ', old.제품번호, '재고: ', old.재고, '->', new.재고));
    end if;
end $$
delimiter ;

update 제품
set 단가 = 2500
where 제품번호 = 1;
select * from 제품로그;


--  제품테이블에서 제품 정보를 삭제하면 삭제된 레코드의 정보를 제품로그테이블에 저장하는 트리거 작성하세요  trigger_제품삭제로그

DELIMITER $$
CREATE TRIGGER  trigger_제품삭제로그
    AFTER DELETE ON 제품
    FOR EACH ROW
BEGIN

    INSERT INTO 제품로그(처리 , 내용) VALUES ('DELETE',CONCAT('제품번호 :' , OLD.제품번호,'제품명:',OLD.제품명));

END $$
DELIMITER ;

DELETE FROM 제품
WHERE 제품번호 = 1;

SELECT * FROM 제품;
SELECT * FROM 제품로그;

-- 트리거에서는 이벤트가 발생한 값을 확인하기 위해서 사용되는 키워드 old, new
-- old (Update, delete 이벤트)
-- new (insert, update 이벤트에서 적용되어 값을 확인할 수 있다.)