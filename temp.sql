insert into users values('nhai', '4321', 'Nguyen Nhai', str_to_date('29-08-1996', '%d-%m-%Y'), 
'Hanoi', '0987654321', str_to_date('10-8-2020', '%d-%m-%Y'));

select * from admins;
select * from books;
select * from users;

select * from categories;

select * from borrows;
delete from borrows where borrowId = 9;
select * from borrowdetails ;

select * from returnBooks;

delete from borrows where borrowId=7;
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
FROM Books, Categories WHERE Books.categoryId = Categories.categoryId 
and Books.bookName LIKE '%A%';

-- sach dang muon
select bookID from borrowdetails
where borrowDetailId not in (
	select borrowdetails.borrowDetailId from borrowdetails, returnbooks
	where borrowDetails.borrowDetailId = returnbooks.borrowDetailId);

-- Sach co the muon
select books.*, Categories.categoryName, Categories.description from books, categories 
where Books.CategoryId = Categories.CategoryId and bookId not in
	(select bookID from borrowdetails where borrowDetailId not in 
			(select borrowdetails.borrowDetailId from borrowdetails, returnbooks
				where borrowDetails.borrowDetailId = returnbooks.borrowDetailId)
);

-- sach dang muon cua user
select bookID, borrowUser, borrowDate from borrowdetails, borrows
where borrows.borrowId = borrowdetails.borrowId and borrows.borrowUser like 'hk1' and 
	borrowDetailId not in (
	select borrowdetails.borrowDetailId from borrowdetails, returnbooks
	where borrowDetails.borrowDetailId = returnbooks.borrowDetailId);
    

select borrowDetailId, bookId from borrowdetails;

select bookID from borrowdetails
where bookId = 2 and borrowDetailId not in (
	select borrowdetails.borrowDetailId from borrowdetails, returnbooks
	where borrowDetails.borrowDetailId = returnbooks.borrowDetailId);

ALTER TABLE borrows
	drop column expirationDate;
ALTER TABLE borrowDetails
	add expirationDate Date not null;
ALTER TABLE Users
	ADD deposit INTEGER NOT NULL;
    
SELECT * from useruseruserid;
SELECT 1 + 1 AS Solution;

INSERT INTO subject SET subjectName = 'Anh';
select * from subject;
INSERT INTO essayquestion SET content = 'Khái niệm bằng chứng kiểm toán? Các loại bằng chứng kiểm toán?',
	subjectId = 1, level = 1, description = 'none', answer = '234';

select choicequestion.* from choicequestion, subject 
	WHERE subject.subjectName LIKE 'Toan';

INSERT INTO choiceanswer SET choiceQuestionId = 2, content = 'Cà mau', trueFalse = true;

select choicequestion.content, choiceanswer.content 
	from choicequestion, choiceanswer
    where choicequestion.choiceQuestionId = choiceanswer.choiceQuestionId;
select * from choiceanswer where choiceQuestionId = 1;

select * from choicequestion ;
select * from subject where subjectName LIKE 'Toan';
select * from books;
SELECT choicequestion.* 
	FROM choicequestion, subject 
    WHERE choicequestion.subjectId = subject.subjectId 
    AND subject.subjectName LIKE 'Van';

select * from books where bookId not in(    
	select bookId from borrowdetails where returnDate is NULL);
    
    SELECT bookId FROM borrowdetails WHERE bookId IS NULL;

select * from borrowdetails;
update borrowdetails set penalty = 10000 where borrowDetailId = 15;
ALTER TABLE borrowdetails 
	ADD returnDate DATE;
ALTER TABLE borrowdetails 
	ADD penalty INTEGER;
DROP TABLE returnbooks;
delete from borrowdetails where borrowDetailId = 16;
select * from borrows;
select * from borrowdetails;
update borrowdetails set penalty = 0 where borrowDetailId > 0;
SELECT * FROM customer WHERE phone LIKE '0123456789';
select * from customer;
delete from driver where driverId >2;
select * from driver;

ALTER TABLE subject
	modify subjectName VARCHAR(200) NOT NULL unique;
    
select * from subject;
select * from essayquestion;