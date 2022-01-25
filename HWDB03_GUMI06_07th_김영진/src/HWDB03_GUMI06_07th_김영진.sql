
USE empdept;

select * from dept;
select * from emp;

#1
select ename, job, sal
from emp
where deptno = (
select deptno
from dept
where loc = "chicago");

#2
select empno, ename, job, deptno
from emp
where empno not in(
select distinct mgr
from emp
where mgr is not null);

#3
select empno, ename, job, mgr
from emp
where mgr = (
select mgr
from emp
where ename = "blake"
);

#4
select * 
from emp
order by hiredate limit 5;

#5
select emp.ename, emp.job, dept.dname, emp.hiredate
from emp left outer join dept
on emp.deptno = dept.deptno
where emp.hiredate> (
select hiredate
from emp
where ename = "JONES"
); 
