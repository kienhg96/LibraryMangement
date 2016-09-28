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
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanage?"
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
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return generatedPassword;
    }

    public static Users checkUserLogin(String username, String password) {
        Users user = null;
        initialize(); // Remove after complete
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
                user = new Users(username, password, fullname, birthday, address, phone, expirationDate);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return user;
    }

    public static Admins checkAdminLogin(String username, String password) {
        Admins admin = null;
        initialize(); // Remove after complete
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM admins WHERE username=? and password=?;");

            stmt.setString(1, username);
            stmt.setString(2, password);
            //System.out.println(stmt);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                String fullname = result.getString("fullname");
                int privilege = result.getInt("privilege");
                String phone = result.getString("phone");
                admin = new Admins(username, password, fullname, privilege, phone);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return admin;
    }

    public static boolean saveBook(Books book) {
        initialize(); // Remove after complete
        if (AdminsAuth.getAdmin() != null) {
            try {
                if (book.getBookId() == -1) {
                    // Create new book
                    PreparedStatement stmt = conn.prepareStatement(
                            "INSERT INTO books(bookName, author, publishCom, categoryId, shelf, price, publishYear) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
                    stmt.setString(1, book.getBookName());
                    stmt.setString(2, book.getAuthor());
                    stmt.setString(3, book.getPublishCom());
                    stmt.setInt(4, book.getCategoryId());
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
                            "UPDATE books "
                            + "SET bookName=?, author=?, publishCom=?, categoryId=?"
                            + ", shelf=?, price=?, publishYear=? "
                            + "WHERE bookId=?");
                    stmt.setString(1, book.getBookName());
                    stmt.setString(2, book.getAuthor());
                    stmt.setString(3, book.getPublishCom());
                    stmt.setInt(4, book.getCategoryId());
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

    public static boolean saveUser(Users user) {
        initialize(); // Remove after complete
        if (AdminsAuth.getAdmin() != null) {
            try {
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO users "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE"
                        + "password=?, fullname=?, birthday=?, address=?, phone=?, expirationDate=?");
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getPassword());
                stmt.setString(3, user.getFullname());
                stmt.setDate(4, new java.sql.Date(user.getBirthday().getTime()));
                stmt.setString(5, user.getAddress());
                stmt.setString(6, user.getPhone());
                stmt.setDate(7, new java.sql.Date(user.getExpirationDate().getTime()));
                stmt.setString(8, user.getPassword());
                stmt.setString(9, user.getFullname());
                stmt.setDate(10, new java.sql.Date(user.getBirthday().getTime()));
                stmt.setString(11, user.getAddress());
                stmt.setString(12, user.getPhone());
                stmt.setDate(13, new java.sql.Date(user.getExpirationDate().getTime()));
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

    public static boolean saveAdmin(Admins admin) {
        initialize(); // Remove after complete
        if (AdminsAuth.getAdmin() != null && AdminsAuth.getAdmin().getPrivilege() == 1) {
            try {
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO admins "
                        + "VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE "
                        + "password=?, fullname=?, privilege=?, phone=?;");
                stmt.setString(1, admin.getUsername());
                stmt.setString(2, admin.getPassword());
                stmt.setString(3, admin.getFullname());
                stmt.setInt(4, admin.getPrivilege());
                stmt.setString(5, admin.getPhone());
                stmt.setString(6, admin.getPassword());
                stmt.setString(7, admin.getFullname());
                stmt.setInt(8, admin.getPrivilege());
                stmt.setString(9, admin.getPhone());
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
        initialize(); // Remove after complete
        if (AdminsAuth.getAdmin() != null) {
            try {
                if (category.getCategoryId() == -1) {
                    PreparedStatement stmt = conn.prepareStatement(
                            "INSERT INTO categories (categoryName, description) "
                            + "VALUES (?, ?);", Statement.RETURN_GENERATED_KEYS);
                    stmt.setString(1, category.getCategoryName());
                    stmt.setString(2, category.getDescription());
                    //System.out.println(stmt.toString());
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
        initialize(); // Remove after complete
        if (AdminsAuth.getAdmin() != null) {
            try {
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO borrows (borrowDate, borrowUser, expirationDate, deposit) "
                        + "VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
                stmt.setDate(1, new java.sql.Date(borrow.getBorrowDate().getTime()));
                stmt.setString(2, borrow.getBorrowUser());
                stmt.setDate(3, new java.sql.Date(borrow.getExpirationDate().getTime()));
                stmt.setInt(4, borrow.getDeposit());
                //System.out.println(stmt.toString());
                stmt.executeUpdate();
                ResultSet key = stmt.getGeneratedKeys();
                if (key.next()) {
                    borrow.setBorrowId(key.getInt(1));
                } else {
                    System.out.println("Cannot get Key");
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

    public static boolean saveBorrowDetail(BorrowDetails detail) {
        initialize(); // Remove after complete
        if (AdminsAuth.getAdmin() != null) {
            try {
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO borrowdetails (borrowId, bookId)"
                        + "VALUES (?, ?);", Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, detail.getBorrowId());
                stmt.setInt(2, detail.getBookId());
                stmt.executeUpdate();
                ResultSet key = stmt.getGeneratedKeys();
                if (key.next()) {
                    detail.setBorrowDetailId(key.getInt(1));
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

    public static boolean saveReturnBook(ReturnBooks rb) {
        initialize(); // Remove after complete
        if (AdminsAuth.getAdmin() != null) {
            try {
                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO returnbooks "
                        + "VALUES (?, ?, ?);");
                stmt.setInt(1, rb.getBorrowDetailId());
                stmt.setDate(2, new java.sql.Date(rb.getReturnDate().getTime()));
                stmt.setInt(3, rb.getPenalty());
                //System.out.println(stmt);
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
            String query = "SELECT * FROM Books WHERE bookId=" + id + ";";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                book = new Books(rs.getString("bookName"), rs.getString("author"),
                        rs.getString("publishCom"), rs.getInt("categoryId"),
                        rs.getString("shelf"),
                        rs.getInt("price"), rs.getInt("publishYear"));
                book.setBookId(rs.getInt("bookId"));
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
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM Books WHERE bookName LIKE '%" + name + "%';";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                book = new Books(rs.getString("bookName"), rs.getString("author"),
                        rs.getString("publishCom"), rs.getInt("categoryId"), rs.getString("shelf"),
                        rs.getInt("price"), rs.getInt("publishYear"));
                book.setBookId(rs.getInt("bookId"));
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
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM Books WHERE author LIKE '%" + author + "%';";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                book = new Books(rs.getString("bookName"), rs.getString("author"),
                        rs.getString("publishCom"), rs.getInt("categoryId"), rs.getString("shelf"),
                        rs.getInt("price"), rs.getInt("publishYear"));
                book.setBookId(rs.getInt("bookId"));
                list.add(book);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return list;
    }

    public static ArrayList<Books> findBookByCategory(String category) {
        ArrayList<Books> list = new ArrayList<>();
        Books book;
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT Books.* from Books, Categories "
                    + "WHERE Books.categoryId = categories.categoryId "
                    + "AND categories.categoryName like '%" + category + "%';";
//            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                book = new Books(rs.getString("bookName"), rs.getString("author"),
                        rs.getString("publishCom"), rs.getInt("categoryId"), rs.getString("shelf"),
                        rs.getInt("price"), rs.getInt("publishYear"));
                book.setBookId(rs.getInt("bookId"));
                list.add(book);
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return list;
    }

    public static ArrayList<Categories> getAllCategories() {
        initialize(); // Remove after complete
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

    public static boolean removeCategory(Categories category) {
        initialize(); // Remove after complete
        if (AdminsAuth.getAdmin() != null) {
            try {
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM categories WHERE categoryId=?");
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

    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static void main(String[] args) {
        if (AdminsAuth.login("lib", "lib")) {
            System.out.println("Logined");
            Books book = findBookById(5);
            book.setPublishYear(2013);
            book.save();
        } else {
            System.out.println("Login failed");
        }

    }
}
