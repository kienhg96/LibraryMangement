insert into users values('nhai', '4321', 'Nguyen Nhai', str_to_date('29-08-1996', '%d-%m-%Y'), 
'Hanoi', '0987654321', str_to_date('10-8-2020', '%d-%m-%Y'));

select * from admins;
select * from books;
select * from users;

select * from categories;

select * from borrows;

select * from borrowdetails;
delete from borrowdetails where borrowdetailid=4;
select * from returnBooks;
delete from returnBooks where borrowDetailId = 4;
select * from borrows;
delete from borrows where borrowId = 7;
insert into borrows(borrowDate, borrowUser, expirationDate) 
	values (str_to_date('25-09-2016', '%d-%m-%Y'), 'hk', str_to_date('25-09-2017', '%d-%m-%Y'));

SELECT * FROM users;

INSERT INTO categories (categoryName) values ('Lap trinh');

SELECT * FROM books;

SELECT Books.* from Books, Categories
WHERE Books.categoryId = categories.categoryId
	AND categories.categoryName like "%Lap%";

INSERT INTO books (bookName, author, publishCom, categoryId, shelf, price)
	VALUEs ("dan lam", "HK", "HK Company", "2", "Ke 5-2", 100000);

DELETE FROM borrows WHERE borrowUser = 'hk';

INSERT INTO users 
VALUES ('user1', '1111', 'Nguoi di duong', '1996-05-08', 'bac giang', '0123456789', '2019-11-30');

delete from users where username='hk';
select * from users;
Alter table borrows
	Add deposit INTEGER;
    
select * from  borrowdetails;

DELETE FROM borrowdetails 
	WHERE borrowdetailId = 3;

insert into borrowdetails (borrowId, bookId)
	VALUES (7, 4);
    
insert into returnbooks VALUES (1, str_to_date('26-09-2016', '%d-%m-%Y'), 0);
select * from returnbooks;
select * from borrowdetails;
select books.*
from books, borrows, borrowdetails
where borrows.borrowUser = 'hk' and borrows.borrowId = borrowDetails.borrowId 
	and borrowdetails.bookId = books.bookId;

select books.*
from books, borrows, borrowdetails
where borrows.borrowUser = 'hk' and borrows.borrowId = borrowDetails.borrowId 
	and borrowdetails.bookId = books.bookId
    and borrowdetails.borrowDetailId not in 
		(select returnbooks.borrowDetailId from returnbooks, borrowdetails 
				WHERE returnbooks.borrowDetailId = borrowdetails.borrowDetailId);
    
select books.*, borrowDetails.borrowDetailId
from returnbooks, books, borrowdetails
WHERE returnbooks.borrowDetailId = borrowdetails.borrowDetailId
	AND borrowdetails.bookId = books.bookId;
    
UPDATE admins
set password='21232f297a57a5a743894a0e4a801fc3'
where username='admin';

DELETE FROM admins
where username='lib';

select * from books where bookId = 1 and bookName = "abc";
insert into books values (1, "abc", "KH", "Kh company", 1, "Ke 2", 100000);
select * from users;

SELECT Books.*, Categories.categoryName, Categories.description 
from Books, categories
WHERE Books.categoryId = categories.categoryId;

SELECT Books.*, Categories.categoryName, Categories.description 
FROM Books WHERE Books.categoryId = Categories.categoryId 
and Books.bookName LIKE '%A%';