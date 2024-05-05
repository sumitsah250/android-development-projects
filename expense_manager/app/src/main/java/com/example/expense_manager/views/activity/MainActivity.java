package com.example.expense_manager.views.activity;

import static java.security.AccessController.getContext;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.expense_manager.adapters.TransactionAdapter;
import com.example.expense_manager.models.Transaction;
import com.example.expense_manager.utils.Constants;
import com.example.expense_manager.utils.Helper;
import com.example.expense_manager.viewmodel.MainViewModel;
import com.example.expense_manager.views.fragment.AddTransactionFragment;
import com.example.expense_manager.R;
import com.example.expense_manager.databinding.ActivityMainBinding;
import com.example.expense_manager.views.fragment.BlankFragment;
import com.example.expense_manager.views.fragment.TransactionFragment;
import com.example.expense_manager.views.fragment.statsFragment;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Calendar calendar;
    public MainViewModel viewModel;

    /*
    0 == daily
    1== monthly
    2==calander
    3=Summary
    4==Notes

     */
//    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        //for binding
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Constants.setCategories();

        setSupportActionBar(binding.materialToolbar);
        getSupportActionBar().setTitle("Transaction");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        setUpDatabase();


        calendar=Calendar.getInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content,new TransactionFragment());
        transaction.commit();
        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if(item.getItemId() == R.id.transactions) {
                    getSupportFragmentManager().popBackStack();
                } else if(item.getItemId() == R.id.stats){
                    transaction.replace(R.id.content, new statsFragment());
                    transaction.addToBackStack(null);
                }
                transaction.commit();
                return true;
            }
        });



    }
    void setUpDatabase(){



    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_top_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void getTransaction_details(){
        viewModel.getTransaction(calendar);
    }
}