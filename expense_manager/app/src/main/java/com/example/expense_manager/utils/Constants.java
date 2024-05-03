package com.example.expense_manager.utils;

import com.example.expense_manager.R;
import com.example.expense_manager.models.Category;

import java.util.ArrayList;

public class Constants {
    public static String INCOME="INCOME";
    public static  String EXPENSE = "EXPENSE";
    public  static   int SELECTED_TAB =0;
    public  static  int DAILY=0;
    public  static  int MONTHLY=1;
    public  static  int CALANDER=0;
    public  static  int SUMMERY=0;
    public  static  int NOTES=0;

    public static ArrayList<Category> categories;


    public static void setCategories(){
        categories= new ArrayList<>();
        categories.add(new Category("Salary", R.drawable.ic_salary, R.color.category6));
        categories.add(new Category("Business",R.drawable.ic_business, R.color.category2));
        categories.add(new Category("Investment",R.drawable.ic_investment, R.color.category3));
        categories.add(new Category("Loan",R.drawable.ic_loan, R.color.category4));
        categories.add(new Category("Rent",R.drawable.ic_rent, R.color.category5));
        categories.add(new Category("Others",R.drawable.ic_other, R.color.category1));
        categories.add(new Category("Food_&_drinks",R.drawable.food_and_drink_icon, R.color.category7));
    }
    public static Category getCategoryDetails(String categoryName){
        for (Category cat:
        categories){
            if(cat.getCategoryName().equals(categoryName)){
                return cat;
            }
        }
        return null;
    }

    public static int getAccountColor(String accountName){
        switch(accountName){
            case "Cash":
                return R.color.Cash;
                case "Bank":
                return  R.color.Bank;
                case "Esewa":
                return  R.color.Esewa;
                case "Khalti":
                    return  R.color.Khalti;
            default:
                return R.color.Others;
        }
    }
}
