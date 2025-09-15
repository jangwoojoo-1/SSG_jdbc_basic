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
