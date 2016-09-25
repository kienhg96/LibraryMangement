/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hk.database;
import com.hk.objs.Admins;
import com.hk.objs.Books;
import com.hk.objs.Borrows;
import com.hk.objs.Categories;
import com.hk.objs.Users;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
/**
 *
 * @author hoangkien
 */
public class Database {
    private static Connection conn = null;
    public static void initialize() {
        if (conn == null) { 
            try {
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanage?" +
                                       "user=root&password=1234&useSSL=false");
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            }
        }
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
    
    public static Admins checkAdminLogin(String username, String password){
        Admins admin = null;
        initialize(); // Remove after complete
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM admins WHERE username=? and password=?;");
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                String fullname = result.getString("fullname");
                int privilege = result.getInt("privilege");
                String phone = result.getString("phone");
                admin = new Admins(username, password, fullname, privilege, phone);
            }
        }
        catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return admin;
    }
    
    public static boolean saveBook(Books book){
        initialize(); // Remove after complete
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO books(bookName, author, publishCom, categoryId, shelf, price) " +
                        "VALUES (?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, book.getBookName());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getPublishCom());
            stmt.setInt(4, book.getCategoryId());
            stmt.setString(5, book.getShelf());
            stmt.setInt(6, book.getPrice());
            stmt.executeUpdate();
            ResultSet key = stmt.getGeneratedKeys();
            if (key.next()) {
                book.setBookId(key.getInt(1));
            }
        }
        catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }
        return true;
    }
    
    public static boolean saveUser(Users user) {
        initialize(); // Remove after complete
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO users " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?);");
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getFullname());
            stmt.setDate(4, new java.sql.Date(user.getBirthday().getTime()));
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getPhone());
            stmt.setDate(7, new java.sql.Date(user.getExpirationDate().getTime()));
            //System.out.println(stmt.toString());
            stmt.executeUpdate();
        }
        catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }
        return true;
    }
    
    public static boolean saveAdmin(Admins admin) {
        initialize(); // Remove after complete
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO admins " +
                        "VALUES (?, ?, ?, ?, ?);");
            stmt.setString(1, admin.getUsername());
            stmt.setString(2, admin.getPassword());
            stmt.setString(3, admin.getFullname());
            stmt.setInt(4, admin.getPrivilege());
            stmt.setString(5, admin.getPhone());
            //System.out.println(stmt.toString());
            stmt.executeUpdate();
        }
        catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }
        return true;
    }
    
    public static boolean saveCategory(Categories category) {
        initialize(); // Remove after complete
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO categories (categoryName, description) " +
                        "VALUES (?, ?);", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, category.getCategoryName());
            stmt.setString(2, category.getDescription());
            //System.out.println(stmt.toString());
            stmt.executeUpdate();
            ResultSet key = stmt.getGeneratedKeys();
            if (key.next()) {
                category.setCategoryId(key.getInt(1));
            }
        }
        catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }
        return true;
    }
    
    public static boolean saveBorrow(Borrows borrow) {
        initialize(); // Remove after complete
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO borrows (borrowDate, borrowUser, expirationDate) " +
                        "VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            stmt.setDate(1, new java.sql.Date(borrow.getBorrowDate().getTime()));
            stmt.setString(2, borrow.getBorrowUser());
            stmt.setDate(3, new java.sql.Date(borrow.getExpirationDate().getTime()));
            System.out.println(stmt.toString());
            stmt.executeUpdate();
            ResultSet key = stmt.getGeneratedKeys();
            if (key.next()) {
                borrow.setBorrowId(key.getInt(1));
            }
            else {
                System.out.println("Cannot get Key");
            }
        }
        catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }
        return true;
    }
    
    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    
    public static void main(String[] args) {
        initialize();
        
    }
}
