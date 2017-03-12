select distinct(thread)
from posts
WHERE
 author NOT IN (SELECT ignor FROM ignor WHERE user = ? AND end > now())
 AND
 thread NOT IN (select title from fdtranzit where folder NOT IN (SELECT folder FROM fdvtranzit WHERE (user = ? OR user = 0) AND view = ?) and user = ?)
 AND
 thread NOT IN (select id from titles where dock !=0)
order by
 created desc
limit ?, ?;