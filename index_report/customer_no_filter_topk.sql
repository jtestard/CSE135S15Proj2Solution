BEGIN;
CREATE TEMP TABLE p_t (name text, id int, price int) ON COMMIT DELETE ROWS;
CREATE TEMP TABLE u_t (name text, id int, price int) ON COMMIT DELETE ROWS;
EXPLAIN ANALYZE VERBOSE insert into u_t (name, id, price) select u.name, s.uid, sum(s.quantity*s.price) as price
from  users u, sales s  where s.uid=u.id group by s.uid, u.name order by price desc offset 0 limit 20;
EXPLAIN ANALYZE VERBOSE insert into p_t (name, id, price) select p.name, s.pid, sum(s.quantity*s.price) as price 
from products p, sales s where s.pid=p.id  group by s.pid, p.name order by price desc offset 0 limit 10;
-- select * from u_t;
-- select * from p_t;
EXPLAIN ANALYZE select s.uid, s.pid, sum(s.quantity*s.price) from u_t u,p_t p, sales s where s.uid=u.id and s.pid=p.id group by s.uid, s.pid;
COMMIT;