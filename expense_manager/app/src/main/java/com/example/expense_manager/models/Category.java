package com.example.expense_manager.models;

public class Category {
    private String categoryName;
    private  int CategoryImage;

    public int getCategoryColour() {
        return categoryColour;
    }

    public void setCategoryColour(int categoryColour) {
        this.categoryColour = categoryColour;
    }

    private int categoryColour;

    public Category() {
    }

    public Category(String categoryName, int categoryImage, int categoryColour) {
        this.categoryName = categoryName;
        CategoryImage = categoryImage;
        this.categoryColour = categoryColour;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryImage() {
        return CategoryImage;
    }

    public void setCategoryImage(int categoryImage) {
        CategoryImage = categoryImage;
    }
}
