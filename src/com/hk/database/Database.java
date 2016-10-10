/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hk.database;

import com.hk.authenticate.AdminsAuth;
import com.hk.objs.Admins;
import com.hk.objs.Books;
import com.hk.objs.BorrowDetails;
import com.hk.objs.Borrows;
import com.hk.objs.Categories;
import com.hk.objs.ReturnBooks;
import com.hk.objs.Users;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hoangkien
 */
public class Database {

    private static Connection conn = null;

    public static void initialize() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/librarymanage?"
                        + "user=root&password=1234&useSSL=false");
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
        }
    }

    public static String hashPassword(String password) {
        MessageDigest digest;
        String generatedPassword = null;
        try {
            digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            byte[] bytes = digest.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString(
                        (bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Database.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return generatedPassword;
    }
    
    public static java.sql.Date utilDateToSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

    public static Users checkUserLogin(String username, String password) {
        Users user = null;
        //initialize(); // Remove after complete
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM users WHERE username=? and password=?;");
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                String fullname = result.getString("fullname");
                Date birthday = result.getDate("birthday");
                String address = result.getString("address");
                String phone = result.getString("phone");
                Date expirationDate = result.getDate("expirationDate");
                int deposit = result.getInt("deposit");
                user = new Users(username);
                user.setRawPassword(password);
                user.setFullname(fullname);
                user.setBirthday(birthday);
                user.setAddress(address);
                user.setPhone(phone);
                user.setExpirationDate(expirationDate);
                user.setDeposit(deposit);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return user;
    }

    public static Users findUserByUsername(String username) {
        //initialize(); // Remove after complete
        Users user = null;
        if (AdminsAuth.getAdmin() != null) {
            try {
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT * FROM users WHERE username=?");
                stmt.setString(1, username);
                ResultSet result = stmt.executeQuery();
                if (result.next()) {
                    String fullname = result.getString("fullname");
                    Date birthday = result.getDate("birthday");
                    String address = result.getString("address");
                    String phone = result.getString("phone");
                    Date expirationDate = result.getDate("expirationDate");
                    String password = result.getString("password");
                    int deposit = result.getInt("deposit");
                    user = new Users(username);
                    user.setRawPassword(password);
                    user.setFullname(fullname);
                    user.setBirthday(birthday);
                    user.setAddress(address);
                    user.setPhone(phone);
                    user.setExpirationDate(expirationDate);
                    user.setDeposit(deposit);
                }
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
        }
        return user;
    }

    public static Admins checkAdminLogin(String username, String password) {
        Admins admin = null;
        //initialize(); // Remove after complete
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM admins WHERE username=? and password=?;");

            stmt.setString(1, username);
            stmt.setString(2, password);
            //System.out.println(stmt);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                String fullname = result.getString("fullname");
                String phone = result.getString("phone");
                admin = new Admins(username);
                admin.setRawPassword(password);
                admin.setFullname(fullname);
                admin.setPhone(phone);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return admin;
    }

    public static boolean saveBook(Books book) {
        //initialize(); // Remove after complete
        if (AdminsAuth.getAdmin() != null) {
            try {
                if (book.getBookId() == -1) {
                    // Create new book
                    PreparedStatement stmt = conn.prepareStatement(
                            "INSERT INTO books "+
                            "(bookName, author, publishCom, "+
                            "categoryId, shelf, price, publishYear) "+
                            "VALUES (?, ?, ?, ?, ?, ?, ?)", 
                            Statement.RETURN_GENERATED_KEYS);
                    stmt.setString(1, book.getBookName());
                    stmt.setString(2, book.getAuthor());
                    stmt.setString(3, book.getPublishCom());
                    stmt.setInt(4, book.getCategory().getCategoryId());
                    stmt.setString(5, book.getShelf());
                    stmt.setInt(6, book.getPrice());
                    stmt.setInt(7, book.getPublishYear());
                    stmt.executeUpdate();
                    ResultSet key = stmt.getGeneratedKeys();
                    if (key.next()) {
                        book.setBookId(key.getInt(1));
                    }
                } else {
                    PreparedStatement stmt = conn.prepareStatement(
                            "UPDATE books " +
                            "SET bookName=?, author=?, publishCom=?, " +
                            "categoryId=?, shelf=?, price=?, publishYear=? " +
                            "WHERE bookId=?");
                    stmt.setString(1, book.getBookName());
                    stmt.setString(2, book.getAuthor());
                    stmt.setString(3, book.getPublishCom());
                    stmt.setInt(4, book.getCategory().getCategoryId());
                    stmt.setString(5, book.getShelf());
                    stmt.setInt(6, book.getPrice());
                    stmt.setInt(7, book.getPublishYear());
                    stmt.setInt(8, book.getBookId());
                    stmt.executeUpdate();
                }
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public static boolean removeBook(Books book) {
        //initialize(); // Remove after complete
        if (AdminsAuth.getAdmin() != null) {
            try {
                PreparedStatement stmt = conn.prepareStatement(
                        "DELETE FROM books WHERE bookId=?");
                stmt.setInt(1, book.getBookId());
                stmt.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public static boolean saveUser(Users user) {
        //initialize(); // Remove after complete
        if (AdminsAuth.getAdmin() != null) {
            try {
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO users " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE " +
                            "password=?, fullname=?, birthday=?, address=?, " +
                            "phone=?, expirationDate=?, deposit=?");
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getPassword());
                stmt.setString(3, user.getFullname());
                stmt.setDate(4, utilDateToSqlDate(user.getBirthday()));
                stmt.setString(5, user.getAddress());
                stmt.setString(6, user.getPhone());
                stmt.setDate(7, utilDateToSqlDate(user.getExpirationDate()));
                stmt.setInt(8, user.getDeposit());
                stmt.setString(9, user.getPassword());
                stmt.setString(10, user.getFullname());
                stmt.setDate(11, utilDateToSqlDate(user.getBirthday()));
                stmt.setString(12, user.getAddress());
                stmt.setString(13, user.getPhone());
                stmt.setDate(14, utilDateToSqlDate(user.getExpirationDate()));
                stmt.setInt(15, user.getDeposit());
                stmt.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public static boolean deleteUser(Users user) {
        //initialize(); // remove after complete
        if (AdminsAuth.getAdmin() != null) {
            try {
                PreparedStatement stmt = conn.prepareStatement(
                        "DELETE FROM Users WHERE username=?");
                stmt.setString(1, user.getUsername());
                stmt.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public static boolean saveAdmin(Admins admin) {
        //initialize(); // Remove after complete
        if (AdminsAuth.getAdmin() != null) {
            try {
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO admins "
                        + "VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE "
                        + "password=?, fullname=?,  phone=?;");
                stmt.setString(1, admin.getUsername());
                stmt.setString(2, admin.getPassword());
                stmt.setString(3, admin.getFullname());
                stmt.setString(4, admin.getPhone());
                stmt.setString(5, admin.getPassword());
                stmt.setString(6, admin.getFullname());
                stmt.setString(7, admin.getPhone());
                //System.out.println(stmt.toString());
                stmt.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public static boolean saveCategory(Categories category) {
        //initialize(); // Remove after complete
        if (AdminsAuth.getAdmin() != null) {
            try {
                if (category.getCategoryId() == -1) {
                    PreparedStatement stmt = conn.prepareStatement(
                            "INSERT INTO categories (categoryName, description) "
                            + "VALUES (?, ?);", Statement.RETURN_GENERATED_KEYS);
                    stmt.setString(1, category.getCategoryName());
                    stmt.setString(2, category.getDescription());
                    stmt.executeUpdate();
                    ResultSet key = stmt.getGeneratedKeys();
                    if (key.next()) {
                        category.setCategoryId(key.getInt(1));
                    }
                } else {
                    PreparedStatement stmt = conn.prepareStatement(
                            "UPDATE categories "
                            + "SET categoryName=?, description=? "
                            + "WHERE categoryId=?");
                    stmt.setString(1, category.getCategoryName());
                    stmt.setString(2, category.getDescription());
                    stmt.setInt(3, category.getCategoryId());
                    stmt.executeUpdate();
                }
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public static boolean saveBorrow(Borrows borrow) {
        //initialize(); // Remove after complete
        if (AdminsAuth.getAdmin() != null) {
            try {
                if (borrow.getBorrowId() == -1) {
                    PreparedStatement stmt = conn.prepareStatement(
                            "INSERT INTO borrows "+
                            "(borrowDate, borrowUser) "
                            + "VALUES (?, ?);", 
                            Statement.RETURN_GENERATED_KEYS);
                    stmt.setDate(1, utilDateToSqlDate(borrow.getBorrowDate()));
                    stmt.setString(2, borrow.getBorrowUser().getUsername());
                    stmt.executeUpdate();
                    ResultSet key = stmt.getGeneratedKeys();
                    if (key.next()) {
                        borrow.setBorrowId(key.getInt(1));
                    }
                } else {
                    PreparedStatement stmt = conn.prepareStatement(
                            "UPDATE borrows "
                            + "SET borrowDate = ?, borrowUser = ? "
                            + "WHERE borrowId = ?");
                    stmt.setDate(1, utilDateToSqlDate(borrow.getBorrowDate()));
                    stmt.setString(2, borrow.getBorrowUser().getUsername());
                    stmt.setInt(3, borrow.getBorrowId());
                    stmt.executeUpdate();
                }
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public static boolean saveBorrowDetail(int borrowId, BorrowDetails detail) {
        //initialize(); // Remove after complete
        if (AdminsAuth.getAdmin() != null) {
            try {
                if (detail.getBorrowDetailId() == -1) {
                    PreparedStatement stmt = conn.prepareStatement(
                            "INSERT INTO borrowdetails (borrowId, bookId, expirationDate)"
                            + "VALUES (?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS);
                    stmt.setInt(1, borrowId);
                    stmt.setInt(2, detail.getBook().getBookId());
                    stmt.setDate(3, utilDateToSqlDate(detail.getExpirationDate()));
                    stmt.executeUpdate();
                    ResultSet key = stmt.getGeneratedKeys();
                    if (key.next()) {
                        detail.setBorrowDetailId(key.getInt(1));
                    }
                } else {
                    PreparedStatement stmt = conn.prepareStatement(
                            "UPDATE borrowdetails "
                            + "SET borrowId = ?, bookId = ?, expirationDate = ? "
                            + "WHERE borrowDetailId = ?");
                    stmt.setInt(1, borrowId);
                    stmt.setInt(2, detail.getBook().getBookId());
                    stmt.setDate(3, utilDateToSqlDate(detail.getExpirationDate()));
                    stmt.setInt(4, detail.getBorrowDetailId());
                    stmt.executeUpdate();
                }
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public static boolean saveReturnBook(int borrowDetailId, ReturnBooks rb) {
        //initialize(); // Remove after complete
        if (AdminsAuth.getAdmin() != null) {
            try {
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO returnbooks "
                        + "VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE "
                        + "returnDate = ?, penalty = ?");
                java.sql.Date returnDate
                        = new java.sql.Date(rb.getReturnDate().getTime());
                stmt.setInt(1, borrowDetailId);
                stmt.setDate(2, returnDate);
                stmt.setInt(3, rb.getPenalty());
                stmt.setDate(4, returnDate);
                stmt.setInt(5, rb.getPenalty());
                stmt.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public static Books findBookById(int id) {
        Books book = null;
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT Books.*, Categories.categoryName, "+
                    "Categories.description " +
                    "FROM Books, Categories " +
                    "WHERE Books.categoryId = Categories.categoryId " +
                    "and bookId=" + id;
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                Categories category = new Categories();
                category.setCategoryId(rs.getInt("categoryId"));
                category.setCategoryName(rs.getString("categoryName"));
                category.setDescription(rs.getString("description"));
                book = new Books();
                book.setBookId(rs.getInt("bookId"));
                book.setBookName(rs.getString("bookName"));
                book.setAuthor(rs.getString("author"));
                book.setPublishCom(rs.getString("publishCom"));
                book.setCategory(category);
                book.setShelf(rs.getString("shelf"));
                book.setPrice(rs.getInt("price"));
                book.setPublishYear(rs.getInt("publishYear"));
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return book;
    }

    public static ArrayList<Books> findBookByName(String name) {
        ArrayList<Books> list = new ArrayList<>();
        Books book;
        Categories category;
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT Books.*, Categories.categoryName, " +
                    "Categories.description " +
                    "FROM Books, Categories "+
                    "WHERE Books.categoryId = Categories.categoryId " +
                    "and bookName LIKE '%" + name + "%'";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                book = new Books();
                category = new Categories();
                category.setCategoryId(rs.getInt("categoryId"));
                category.setCategoryName(rs.getString("categoryName"));
                category.setDescription(rs.getString("description"));
                book.setBookId(rs.getInt("bookId"));
                book.setBookName(rs.getString("bookName"));
                book.setAuthor(rs.getString("author"));
                book.setPublishCom(rs.getString("publishCom"));
                book.setCategory(category);
                book.setShelf(rs.getString("shelf"));
                book.setPrice(rs.getInt("price"));
                book.setPublishYear(rs.getInt("publishYear"));
                list.add(book);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return list;
    }

    public static ArrayList<Books> findBookByAuthor(String author) {
        ArrayList<Books> list = new ArrayList<>();
        Books book;
        Categories category;
        try {
            Statement stmt = conn.createStatement();
            String query = 
                    "SELECT Books.*, Categories.categoryName, " +
                    "Categories.description " +
                    "FROM Books, Categories " + 
                    "WHERE Books.categoryId = Categories.categoryId " +
                    "and author LIKE '%" + author + "%';";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                book = new Books();
                category = new Categories();
                category.setCategoryId(rs.getInt("categoryId"));
                category.setCategoryName(rs.getString("categoryName"));
                category.setDescription(rs.getString("description"));
                book.setBookId(rs.getInt("bookId"));
                book.setBookName(rs.getString("bookName"));
                book.setAuthor(rs.getString("author"));
                book.setPublishCom(rs.getString("publishCom"));
                book.setCategory(category);
                book.setShelf(rs.getString("shelf"));
                book.setPrice(rs.getInt("price"));
                book.setPublishYear(rs.getInt("publishYear"));
                list.add(book);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return list;
    }

    public static ArrayList<Books> findBookByCategory(String categoryName) {
        ArrayList<Books> list = new ArrayList<>();
        Books book;
        Categories category;
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT Books.*, Categories.categoryName, "+
                    "Categories.description " +
                    "from Books, Categories " +
                    "WHERE Books.categoryId = categories.categoryId " +
                    "AND categories.categoryName like '%" + categoryName + "%'";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                book = new Books();
                category = new Categories();
                category.setCategoryId(rs.getInt("categoryId"));
                category.setCategoryName(rs.getString("categoryName"));
                category.setDescription(rs.getString("description"));
                book.setBookId(rs.getInt("bookId"));
                book.setBookName(rs.getString("bookName"));
                book.setAuthor(rs.getString("author"));
                book.setPublishCom(rs.getString("publishCom"));
                book.setCategory(category);
                book.setShelf(rs.getString("shelf"));
                book.setPrice(rs.getInt("price"));
                book.setPublishYear(rs.getInt("publishYear"));
                list.add(book);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return list;
    }

    public static ArrayList<Books> getAllBooks() {
        //initialize(); // Remove after complete
        ArrayList<Books> list = new ArrayList<>();
        Books book;
        Categories category;
        try {
            Statement stmt = conn.createStatement();
            String query = 
                    "SELECT Books.*, Categories.categoryName, " + 
                    "Categories.description " + 
                    "From Books, Categories " +
                    "Where Books.CategoryId = Categories.CategoryId";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                book = new Books();
                category = new Categories();
                category.setCategoryId(rs.getInt("categoryId"));
                category.setCategoryName(rs.getString("categoryName"));
                category.setDescription(rs.getString("description"));
                book.setBookId(rs.getInt("bookId"));
                book.setBookName(rs.getString("bookName"));
                book.setAuthor(rs.getString("author"));
                book.setPublishCom(rs.getString("publishCom"));
                book.setCategory(category);
                book.setShelf(rs.getString("shelf"));
                book.setPrice(rs.getInt("price"));
                book.setPublishYear(rs.getInt("publishYear"));
                list.add(book);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return list;
    }

    public static ArrayList<Books> getAllBookAvailable() {
        //initialize(); // remove after complete
        ArrayList<Books> list = new ArrayList<>();
        Books book;
        Categories category;
        String sql = "select Books.*, Categories.categoryName, Categories.description "
                + "from books, categories "
                + "where Books.CategoryId = Categories.CategoryId and bookId not in "
                + "(select bookID from borrowdetails where borrowDetailId not in "
                + "(select borrowdetails.borrowDetailId from borrowdetails, returnbooks "
                + "where borrowDetails.borrowDetailId = returnbooks.borrowDetailId));";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                book = new Books();
                category = new Categories();
                category.setCategoryId(rs.getInt("categoryId"));
                category.setCategoryName(rs.getString("categoryName"));
                category.setDescription(rs.getString("description"));
                book.setBookId(rs.getInt("bookId"));
                book.setBookName(rs.getString("bookName"));
                book.setAuthor(rs.getString("author"));
                book.setPublishCom(rs.getString("publishCom"));
                book.setCategory(category);
                book.setShelf(rs.getString("shelf"));
                book.setPrice(rs.getInt("price"));
                book.setPublishYear(rs.getInt("publishYear"));
                list.add(book);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return list;
    }

    public static ArrayList<Books> getAllBookNotAvailable() {
        ArrayList<Books> list = new ArrayList<>();
        Books book;
        Categories category;
        String sql
                = "select Books.*, Categories.categoryName, Categories.description "
                + "from borrowdetails, Books, Categories "
                + "where Books.bookId = borrowdetails.bookId "
                + "and Books.CategoryId = Categories.CategoryId "
                + "and borrowDetailId not in ( "
                + "select borrowdetails.borrowDetailId "
                + "from borrowdetails, returnbooks "
                + "where borrowDetails.borrowDetailId = returnbooks.borrowDetailId );";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                book = new Books();
                category = new Categories();
                category.setCategoryId(rs.getInt("categoryId"));
                category.setCategoryName(rs.getString("categoryName"));
                category.setDescription(rs.getString("description"));
                book.setBookId(rs.getInt("bookId"));
                book.setBookName(rs.getString("bookName"));
                book.setAuthor(rs.getString("author"));
                book.setPublishCom(rs.getString("publishCom"));
                book.setCategory(category);
                book.setShelf(rs.getString("shelf"));
                book.setPrice(rs.getInt("price"));
                book.setPublishYear(rs.getInt("publishYear"));
                list.add(book);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return list;
    }

    public static boolean checkBookAvailable(Books book) {
        boolean result = true;
        String sql
                = "select bookID from borrowdetails "
                + "where bookId = ? and borrowDetailId not in ( "
                + "select borrowdetails.borrowDetailId from borrowdetails, returnbooks "
                + "where borrowDetails.borrowDetailId = returnbooks.borrowDetailId)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, book.getBookId());
            ResultSet rs = stmt.executeQuery();
            return !rs.next();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return result;
    }

    public static ArrayList<Categories> getAllCategories() {
        //initialize(); // Remove after complete
        ArrayList<Categories> list = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM categories;";
            ResultSet rs = stmt.executeQuery(query);
            Categories cat;
            while (rs.next()) {
                cat = new Categories(rs.getString("categoryName"),
                        rs.getString("description"));
                cat.setCategoryId(rs.getInt("categoryId"));
                list.add(cat);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return list;
    }

    public static ArrayList<Users> getAllUsers() {
        //initialize(); // Remove after complete
        ArrayList<Users> list = new ArrayList<>();
        if (AdminsAuth.getAdmin() != null) {
            try {
                Statement stmt = conn.createStatement();
                String query = "SELECT * FROM users";
                ResultSet rs = stmt.executeQuery(query);
                Users user;
                while (rs.next()) {
                    user = new Users(rs.getString("username"));
                    user.setRawPassword(rs.getString("password"));
                    user.setFullname(rs.getString("fullname"));
                    user.setBirthday(rs.getDate("birthday"));
                    user.setAddress(rs.getString("address"));
                    user.setPhone(rs.getString("phone"));
                    user.setExpirationDate(rs.getDate("expirationDate"));
                    user.setDeposit(rs.getInt("deposit"));
                    list.add(user);
                }
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
        }
        return list;
    }

    public static ArrayList<Borrows> getAllBorrowListByUser(Users user) {
        ArrayList<Borrows> list = new ArrayList<>();
        Borrows borrow;
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM borrows WHERE borrowUser = ?");
            stmt.setString(1, user.getUsername());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                borrow = new Borrows();
                borrow.setBorrowId(rs.getInt("borrowId"));
                borrow.setBorrowDate(rs.getDate("borrowDate"));
                borrow.setBorrowUser(user);
                list.add(borrow);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return list;
    }

    public static ArrayList<BorrowDetails> getAllBorrowDetailListByBorrow(
            Borrows borrow) {
        ArrayList<BorrowDetails> list = new ArrayList<>();
        BorrowDetails detail;
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM borrowdetails WHERE borrowId = ?");
            stmt.setInt(1, borrow.getBorrowId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                detail = new BorrowDetails();
                detail.setBook(findBookById(rs.getInt("bookId")));
                detail.setBorrowDetailId(rs.getInt("borrowDetailId"));
                detail.setExpirationDate(rs.getDate("expirationDate"));
                list.add(detail);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return list;
    }

    public static ReturnBooks getReturnBookByBorrowDetail(BorrowDetails detail) {
        ReturnBooks rb = null;
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM returnbooks WHERE borrowDetailId = ?");
            stmt.setInt(1, detail.getBorrowDetailId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                rb = new ReturnBooks();
                rb.setPenalty(rs.getInt("penalty"));
                rb.setReturnDate(rs.getDate("returnDate"));
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return rb;
    }

    public static boolean removeCategory(Categories category) {
        //initialize(); // Remove after complete
        if (AdminsAuth.getAdmin() != null) {
            try {
                PreparedStatement stmt = conn.prepareStatement(
                        "DELETE FROM categories WHERE categoryId=?");
                stmt.setInt(1, category.getCategoryId());
                stmt.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public static Date newDate(int day, int month, int year) {
        LocalDate date = LocalDate.of(year, month, day);
        return localDateToDate(date);
    }

    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static void main(String[] args) {
        ArrayList<Books> list = getAllBookAvailable();
    }
}
