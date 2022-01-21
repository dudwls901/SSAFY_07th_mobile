-- drop database productManager;

create database productManager 
default character set utf8mb4 collate utf8mb4_general_ci ;

use productManager;

-- drop table product;

#1
CREATE TABLE product(
	productCode int		not null auto_increment,
    productName varchar(20)		not null,
    productPrice int,
    primary key (productCode)
) Engine=InnoDb default CHARSET=utf8mb4;

#2
insert into product (productName, productPrice) values('정윤TV', 50000);
insert into product (productName, productPrice) values('영진TV', 3550000);
insert into product (productName, productPrice) values('정윤씨의 자취방 TV', 1000000);
insert into product (productName, productPrice) values('재홍TV', 50000);
insert into product (productName, productPrice) values('종욱TV', 50000);
insert into product (productName, productPrice) values('옹벨노트북', 1200000);
insert into product (productName, productPrice) values('만제노트북', 3000000);
insert into product (productName, productPrice) values('정윤노트북', 9550000);

#3
select productCode, productName, productPrice, round(productprice/100*85,0) "세일가"
from product;


#4
update product set productPrice = round(productprice/100*80,2)
where productName like '%TV%';

#5
select sum(productPrice) '총 금액' from product;