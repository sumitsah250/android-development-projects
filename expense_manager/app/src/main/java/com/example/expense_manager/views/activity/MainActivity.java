package com.example.expense_manager.views.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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

        setUpDatabase();


        calendar=Calendar.getInstance();
        updateDate();
        binding.nextDatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constants.SELECTED_TAB==0){
                calendar.add(Calendar.DATE,1);

            } else if (Constants.SELECTED_TAB==1) {
                    calendar.add(Calendar.MONTH,1);

                }


                updateDate();
            }
        });
        binding.previousDatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constants.SELECTED_TAB==0){
                    calendar.add(Calendar.DATE,-1);
                }else if (Constants.SELECTED_TAB==1) {
                    calendar.add(Calendar.MONTH,-1);

                }
                updateDate();
            }
        });



        //for binding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setSupportActionBar(binding.materialToolbar);
        getSupportActionBar().setTitle("Transaction");


        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddTransactionFragment().show(getSupportFragmentManager(),null);
            }
        });
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               if(tab.getText().equals("Monthly")){
                   Constants.SELECTED_TAB=1;
                   updateDate();
               }else if(tab.getText().toString().equals("Daily")){
                   Constants.SELECTED_TAB=0;
                   updateDate();
               }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        binding.transactionsList.setLayoutManager(new LinearLayoutManager(this));
        viewModel.transaction.observe(this, new Observer<RealmResults<Transaction>>() {
            @Override
            public void onChanged(RealmResults<Transaction> transactions) {
                TransactionAdapter transactionAdapter = new TransactionAdapter(MainActivity.this,transactions);
                binding.transactionsList.setAdapter(transactionAdapter);
                if(transactions.size()>0){
                    binding.emptylist.setVisibility(View.GONE);
                }
                else{
                    binding.emptylist.setVisibility(View.VISIBLE);
                }


            }
        });
        viewModel.getTransaction(calendar);

        viewModel.totalIncome.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.incomelbl.setText(String.valueOf(aDouble));

            }
        });
        viewModel.totalExpense.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.expenselbl.setText(String.valueOf(aDouble));

            }
        });
        viewModel.totalAmount.observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.totalAmountlbl.setText(String.valueOf(aDouble));

            }
        });








    }
    void setUpDatabase(){



    }



    void updateDate(){
        if(Constants.SELECTED_TAB==Constants.DAILY){
            binding.currentdate.setText(Helper.formatDate(calendar.getTime()));
        }else if(Constants.SELECTED_TAB==Constants.MONTHLY){
            binding.currentdate.setText(Helper.formatDateByMonth(calendar.getTime()));
        }

        viewModel.getTransaction(calendar);
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