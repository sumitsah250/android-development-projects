package com.example.expense_manager.views.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.expense_manager.R;
import com.example.expense_manager.adapters.TransactionAdapter;
import com.example.expense_manager.databinding.FragmentTransactionBinding;
import com.example.expense_manager.models.Transaction;
import com.example.expense_manager.utils.Constants;
import com.example.expense_manager.utils.Helper;
import com.example.expense_manager.viewmodel.MainViewModel;
import com.example.expense_manager.views.activity.MainActivity;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.realm.RealmResults;


public class TransactionFragment extends Fragment {
    FragmentTransactionBinding binding;
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




    public TransactionFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTransactionBinding.inflate(inflater);



        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

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



        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddTransactionFragment().show(getParentFragmentManager(),null);
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


        binding.transactionsList.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModel.transaction.observe(getViewLifecycleOwner(), new Observer<RealmResults<Transaction>>() {
            @Override
            public void onChanged(RealmResults<Transaction> transactions) {
                TransactionAdapter transactionAdapter = new TransactionAdapter(getActivity(),transactions);
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

        viewModel.totalIncome.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.incomelbl.setText(String.valueOf(aDouble));

            }
        });
        viewModel.totalExpense.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.expenselbl.setText(String.valueOf(aDouble));

            }
        });
        viewModel.totalAmount.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.totalAmountlbl.setText(String.valueOf(aDouble));

            }
        });

        binding.currentdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog= new DatePickerDialog(getActivity());
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.YEAR,year);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM,yyyy");
                        String dateToShow = dateFormat.format(calendar.getTime());
                        binding.currentdate.setText(dateToShow);
                        viewModel.getTransaction(calendar);
                    }
                });
                datePickerDialog.show();
            }
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
    void updateDate(){
        if(Constants.SELECTED_TAB==Constants.DAILY){
            binding.currentdate.setText(Helper.formatDate(calendar.getTime()));
        }else if(Constants.SELECTED_TAB==Constants.MONTHLY){
            binding.currentdate.setText(Helper.formatDateByMonth(calendar.getTime()));
        }

        viewModel.getTransaction(calendar);
    }
}