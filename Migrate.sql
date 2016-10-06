CREATE TABLE Users(
	username VARCHAR(100) PRIMARY KEY NOT NULL,
    password VARCHAR(100) NOT NULL,
    fullname VARCHAR(100) NOT NULL,
    birthday DATE,
    address  VARCHAR(256),
    phone 	 VARCHAR(20),
    expirationDate DATE NOT NULL
);

CREATE TABLE Admins(
	username VARCHAR(100) PRIMARY KEY NOT NULL,
    password VARCHAR(100) NOT NULL,
    fullname VARCHAR(100) NOT NULL,
    phone    VARCHAR(20)
);

CREATE TABLE Categories(
	categoryId INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    categoryName VARCHAR(100),
    description varchar(256);
);

CREATE TABLE Books(
	bookId INTEGER PRIMARY KEY NOT NULL auto_increment,
    bookName VARCHAR(100) NOT NULL,
    author VARCHAR(100) NOT NULL,
    publishCom VARCHAR(100) NOT NULL,
    categoryId INTEGER NOT NULL,
    shelf VARCHAR(100) NOT NULL,
    price INTEGER NOT NULL,
    publishYear integer
);

CREATE TABLE Borrows(
	borrowId INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    borrowDate DATE NOT NULL,
    borrowUser VARCHAR(100) NOT NULL,
    expirationDate DATE NOT NULL,
    deposit INTEGER
);

CREATE TABLE BorrowDetails(
	borrowDetailId INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
	borrowId INTEGER NOT NULL,
    bookId INTEGER NOT NULL
);

CREATE TABLE ReturnBooks(
	borrowDetailId INTEGER NOT NULL PRIMARY KEY,
    returnDate DATE NOT NULL,
    penalty INTEGER
);


ALTER TABLE returnbooks
	ADD CONSTRAINT fkBorrowDetail foreign key (borrowDetailId) 
    references BorrowDetails(borrowDetailId) ON DELETE CASCADE;


ALTER TABLE Books
	ADD CONSTRAINT fkCategory FOREIGN KEY (categoryId) 
    references categories(categoryId) ON DELETE CASCADE;

ALTER TABLE borrows 
	ADD CONSTRAINT fkUser foreign key (borrowUser) 
    references users(username) ON DELETE CASCADE;

ALTER TABLE BorrowDetails
	ADD CONSTRAINT fkBorrowId foreign key (borrowId) 
    references borrows(borrowId) ON DELETE CASCADE;

ALTER TABLE BorrowDetails
    ADD CONSTRAINT fkBookId foreign key (bookId) 
    references books(bookId) ON DELETE CASCADE;

ALTER TABLE Returnbooks 
	ADD CONSTRAINT fk_BorrowId foreign key (borrowDetailId) 
    references BorrowDetails(borrowDetailId) ON DELETE CASCADE;

    