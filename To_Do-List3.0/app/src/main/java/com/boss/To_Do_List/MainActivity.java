package com.boss.To_Do_List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout Tab;
    ViewPager viewPager;
    public static SwipeRefreshLayout refresh;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        refresh= findViewById(R.id.swipeRefresh);

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent home = new Intent(MainActivity.this,MainActivity.class);
                startActivity(home);
                finish();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh.setRefreshing(false);

                    }
                },2000);
            }
        });

        //for tab layout
        Tab= findViewById(R.id.tab);
        viewPager= findViewById(R.id.viewpager);
        viewPageradapter adapterpager = new viewPageradapter(getSupportFragmentManager());
        viewPager.setAdapter(adapterpager);
        Tab.setupWithViewPager(viewPager);
        //for tab layout


        //toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("To Do List");
        }
        toolbar.setTitle("To Do List");
        //toolbar



    }

    // for toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId==R.id.toolbar_remaining){
            Tab.setScrollX(Tab.getWidth());
            Tab.getTabAt(0).select();
        } else if(itemId==R.id.toolbar_completed){
            Tab.setScrollX(Tab.getWidth());
            Tab.getTabAt(1).select();
        }else if(itemId==android.R.id.home){
            super.getOnBackPressedDispatcher();
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    static void refresh1(){

    }

    // for toolbar
}