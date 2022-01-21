#1.
use world;

#2.
desc city;
desc country;
desc countrylanguage;

#3.
select * from country
where code = "KOR";

#4.
select Code, Name, gnp, gnpold, gnp-gnpold "gnp변동량"
from country
where (GNP-GNPOLD) > 0
order by (gnp-gnpold);

#5.
select distinct Continent 
from country
order by length(Continent);

#6.
select concat(Name ,"은 " , Continent , "에 속하며 인구는 ", Population ,"명이다.")  "정보"
from country
where Continent is not null and Continent = 'Asia';

#7
select Name, Continent, GNP, Population
from country
where IndepYear is null AND Population >= 10000
order by Population;

#8
select Code, Name, Indepyear
from country
where Population between 100000000 and 200000000
order by Population desc limit 3;

#9
select Code, Name, Indepyear
from country
where Indepyear
in (800, 1810, 1811, 1901)
order by Indepyear , Code desc;

#10
select Code, Name, Region
from country
where Region like '%asia%' and name like '_o%';

#11
select char_length('홍길동') 한글, length('hong') 영문;

#12
select Code, Name, GovernmentForm
from country 
where Governmentform like '%Republic%' and length(name) >=10
order by length(name) desc limit 10; 

#13
select Code, Name
from country
where left(code,1)='a' or left(code,1)='e' or left(code,1)='i'
or left(code,1)='o' or left(code,1)='u'
order by Name limit 2, 3;

#14
select name, insert(name, 3,length(name)-4,repeat('*',length(name)-4))
from country;

#15
select distinct replace(Region, ' ','_') "지역들"
from country
order by length(Region);
 
#16
-- pdf에는 왜 India가 없는 지 모르겠습니다 
select name, SurfaceArea, Population, round(SurfaceArea/Population,3) "1인당 점유면적"
from country
where Population >= 100000000
order by SurfaceArea/Population limit 10;

#17
select curdate() "오늘", dayofyear(date_sub(curdate(), interval 2022 year)) "올해 지난 날",
date_add(curdate(), interval 100 day) "100일 후" ;

#18
select Name, Continent, LifeExpectancy, 
case when LifeExpectancy > 80 then '장수국가'
when LifeExpectancy >60 then '일반국가'
else '단명국가' 
end '구분'
from country
where Continent = 'Asia'
order by LifeExpectancy;

#19
select name, gnp, gnpold,
case when gnpold = null then "신규"
else gnp-gnpold 
end
'gnp 향상'
from country
order by name;

#20
select weekday('2020-05-05') 'weekday(2020-05-05)' , case when weekday(now()) < 5 then '행복'
else '불행'
end '행복여부';
