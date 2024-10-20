package com.paisa.expense_manager.views.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.paisa.expense_manager.R;

import java.util.ArrayList;

public class profile extends AppCompatActivity {
    ArrayList<String> arrNames ;
   RecyclerView listview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.listview), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//        arrNames = new ArrayList<>();
//
//        listview=findViewById(R.id.listview);
//        listview.setLayoutManager(new LinearLayoutManager(this));
//        arrNames.add("Sumit");
//
//        ProfilePictureAdapter profilePictureAdapter = new ProfilePictureAdapter(this,arrNames);
//        listview.setAdapter(profilePictureAdapter);







    }
}