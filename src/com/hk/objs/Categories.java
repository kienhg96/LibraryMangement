/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hk.objs;

import com.hk.database.Database;
import java.util.ArrayList;

/**
 *
 * @author hoangkien
 */
public class Categories {
    private int categoryId;
    private String categoryName;
    private String description;

    public Categories() {
        this.categoryId = -1;
        this.categoryName = "";
        this.description = "";
    }
    
    public Categories(String categoryName, String description) {
        this.categoryId = -1;
        this.categoryName = categoryName;
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public boolean setCategoryId(int categoryId) {
        if (this.categoryId == -1) {
            this.categoryId = categoryId;
            return true;
        }
        else {
            return false;
        }
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public boolean save(){
        return Database.saveCategory(this);
    }
    public static ArrayList<Categories> getAllCategory(){
        return Database.getAllCategories();
    }
    public boolean remove(){
        return Database.removeCategory(this);
    }
}
