DROP DATABASE IF EXISTS world;

CREATE DATABASE world;

USE world;


select * from country;
select * from countrylanguage;
select * from city;

#1
select code, country.name
from country, city
where country.code = city.countrycode and city.name = "kabul" ;

#2
select country.name, language, percentage
from countrylanguage, country
where countrylanguage.countrycode = country.code and isofficial = true and percentage = 100.0
order by country.name;

#3
select city.name, countrylanguage.language, country.name
from city, country, countrylanguage
where city.name ="Amsterdam"
and city.countrycode = country.code  
and countrylanguage.countrycode = country.code
and countrylanguage.isofficial = true;

#4
select country.name, capital, headofstate "수도", city.population "수도인구"
from country, city
where country.name like "united%"
and city.id = country.capital
and country.capital is not null;
 
#5
select country.name, capital, 
case when capital is null then '수도없음'
else headofstate
end "수도"
, case when capital is null then '수도없음'
else city.population
end "수도인구"
from country left outer join city 
on country.capital = city.id
where country.name like "united%";

#6
select  count(distinct countrycode) "국가수"
from countrylanguage
where countrylanguage.percentage >
(select max(percentage)
from countrylanguage
where countrycode = "che")
and isofficial = true;


#7
select countrylanguage.language
from countrylanguage join country
on country.code = countrylanguage.countrycode
where country.name ="south korea"
and countrylanguage.isofficial = true;


#8
select country.name, country.code, count(city.id) "도시개수"
from city left outer join country
on country.code = city.countrycode
where country.name like "bo%"
group by country.name;


#9
select country.name, country.code, 
case when count(city.id) > 0 then count(city.id)
else "단독"
end "도시개수"
from country left outer join city
on country.code = city.countrycode
where country.name like "bo%"
group by country.name;


#10
select countrycode, name, population
from city
where population = (
select max(population)
from city);

#11
select country.name,city.countrycode, city.name, city.population
from city left outer join country
on city.countrycode = country.code
where city.population = (
select min(population)
from city);

#12
select countrycode, name, population
from city
where population > (
select population from city
where name = "seoul");


#13
select city.countrycode, countrylanguage.language
from countrylanguage join city
on countrylanguage.countrycode = city.countrycode
where city.name = "san miguel"
and countrylanguage.isofficial = true;


#14
select country.code, max(city.population) "max_pop"
from city left outer join country
on country.code = city.countrycode
group by country.code
order by country.code;


#15
select countrycode, name, population
from city
group by countrycode
order by countrycode;

#16
select country.code , country.name, city.name, city.population
from country left outer join city
on country.code = city.countrycode
group by country.code
order by country.code;

#17
create view summary as
select co.code , co.name "co_name", ci.name "ci_name", ci.population
from country co left outer join city ci
on co.code = ci.countrycode
group by co.code
order by co.code;

#18
select code, co_name, ci_name, population
from summary
where code ="KOR";