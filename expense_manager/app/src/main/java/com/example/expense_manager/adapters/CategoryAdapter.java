package com.example.expense_manager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expense_manager.R;
import com.example.expense_manager.databinding.SampleCategoryLayoutBinding;
import com.example.expense_manager.models.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    Context context;
    ArrayList<Category> categories;
    public CategoryAdapter(Context context,ArrayList<Category> categories,CategoryClickListener categoryClickListener){
        this.context= context;
        this.categories=categories;
        this.categoryClickListener=categoryClickListener;
    }
    public interface  CategoryClickListener{
        void onCategoryClicked(Category category);
    }
    CategoryClickListener categoryClickListener;


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.sample_category_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.binding.catogarytext.setText(category.getCategoryName());
        holder.binding.categoryIcon.setImageResource(category.getCategoryImage());
        holder.binding.categoryIcon.setBackgroundTintList(context.getColorStateList(category.getCategoryColour()));
        holder.itemView.setOnClickListener(c->{
            categoryClickListener.onCategoryClicked(category);

        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends  RecyclerView.ViewHolder{
        SampleCategoryLayoutBinding binding;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            binding= SampleCategoryLayoutBinding.bind(itemView);
        }
    }

}
