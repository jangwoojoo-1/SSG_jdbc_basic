DELIMITER $$
CREATE TRIGGER  trigger_제품추가로그
    AFTER INSERT ON 제품
    FOR EACH ROW
BEGIN
    INSERT INTO 제품로그(처리 , 내용) VALUES ('INSERT',CONCAT('제품번호 :' , NEW.제품번호,'제품명:',NEW.제품명));

END $$
DELIMITER ;

truncate 제품;
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

-- ------------------------------------------    중첩 트리거    --------------------------------------------------------------------------
-- 데이터베이스 생성
drop database if exists triggerDB;
create database if not exists triggerDB;

use triggerDB;


-- 테이블 생성
create table orderTbl(
    orderNo INT auto_increment primary key ,
    userID varchar(5),
    prodName varchar(5),
    orderamount int
);

create table prodTbl(
    prodName varchar(5),
    account int
);

create table deliverTbl(
    deliverNo int auto_increment primary key ,
    prodName varchar(5),
    account int unique
);


-- 데이터 입력
insert into prodTbl values('사과', 100);
insert into prodTbl values('배', 100);
insert into prodTbl values('귤', 100);
commit;


-- 트리거 작성 - 입력 시 재고 감소
drop trigger if exists orderTrg;
delimiter $$
create trigger orderTrg
    after insert
    on orderTbl
    for each row
begin
    update prodTbl set account = account - new.orderamount where prodName = New.prodName;
end $$
delimiter ;

-- 트리거 작성 2 - 구매 시 배송 테이블에 새 배송 입력
delimiter $$
create trigger prodTrg
    after update
    on prodTbl
    for each row
begin
    declare orderamount int;
    set orderAmount = old.account - new.account;
    insert into deliverTbl(prodName, account) values(new.prodName, orderamount);
end $$
delimiter ;

insert into orderTbl values (null, 'John', '배', 5);

select * from orderTbl;
select * from prodTbl;
select * from deliverTbl;

























