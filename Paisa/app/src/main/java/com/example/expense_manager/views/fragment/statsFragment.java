package com.example.expense_manager.views.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.example.expense_manager.R;
import com.example.expense_manager.databinding.FragmentStatsBinding;
import com.example.expense_manager.databinding.FragmentTransactionBinding;
import com.example.expense_manager.models.Transaction;
import com.example.expense_manager.utils.Constants;
import com.example.expense_manager.utils.Helper;
import com.example.expense_manager.viewmodel.MainViewModel;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.RealmResults;


public class statsFragment extends Fragment {
    FragmentStatsBinding binding;

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

    public statsFragment() {

    }






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentStatsBinding.inflate(inflater);
            Pie pie = AnyChart.pie();


//        pie.title("Fruits imported in 2015 (in kg)");
//
//        pie.labels().position("outside");
//
//        pie.legend().title().enabled(true);
//        pie.legend().title()
//                .text("Retail channels")
//                .padding(0d, 0d, 10d, 0d);
//
//        pie.legend()
//                .position("center-bottom")
//                .itemsLayout(LegendLayout.HORIZONTAL)
//                .align(Align.CENTER);

//        binding.anychart.setChart(pie);

        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        calendar= Calendar.getInstance();
        updateDate();


        binding.nextDatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constants.SELECTED_STATS==0){
                    calendar.add(Calendar.DATE,1);

                } else if (Constants.SELECTED_STATS==1) {
                    calendar.add(Calendar.MONTH,1);

                }
                updateDate();
            }
        });
        binding.previousDatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Constants.SELECTED_STATS==0){
                    calendar.add(Calendar.DATE,-1);
                }else if (Constants.SELECTED_STATS==1) {
                    calendar.add(Calendar.MONTH,-1);

                }
                updateDate();
            }
        });
        binding.currentdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog= new DatePickerDialog(getActivity());
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.YEAR,year);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM,yyyy");
                        String dateToShow = dateFormat.format(calendar.getTime());
                        binding.currentdate.setText(dateToShow);
                        viewModel.getTransaction(calendar);
                        updateDate();

                    }
                });
//                updateDate();
                datePickerDialog.show();
            }
        });
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getText().equals("Monthly")){
                    Constants.SELECTED_STATS=1;
                    updateDate();
                }else if(tab.getText().toString().equals("Daily")){
                    Constants.SELECTED_STATS=0;
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

                    viewModel.categoriestransaction.observe(getViewLifecycleOwner(), new Observer<RealmResults<Transaction>>() {
                @Override
                public void onChanged(RealmResults<Transaction> transactions) {
                    if(transactions.size()>0){
                        List<DataEntry> data = new ArrayList<>();

                        binding.emptylist.setVisibility(View.GONE);
                        binding.anychart.setVisibility(View.VISIBLE);
//
                        Map<String, Double> categgoryMap = new HashMap<>();
                        for(Transaction transaction :transactions){
                            String category = transaction.getCategory();
                            double  amount = transaction.getAmount();
                            if(categgoryMap.containsKey(category)){
                                double currentTotal =categgoryMap.get(category).doubleValue();
                                currentTotal += Math.abs(amount);
                                categgoryMap.put(category,currentTotal);
                            }else {
                                categgoryMap.put(category,Math.abs(amount));
                            }
                        }
                        for(Map.Entry<String,Double> entry :categgoryMap.entrySet()){
                            data.add(new ValueDataEntry(entry.getKey(),entry.getValue()));

                        }
                        try{
                            pie.data(data);
                        }catch (Exception e){
                            Toast.makeText(getContext(), ""+e, Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        binding.emptylist.setVisibility(View.VISIBLE);
                        binding.anychart.setVisibility(View.GONE);
                    }
                }
            });

        viewModel.getTransaction(calendar,Constants.SELECTED_STATS_TYPE);
        binding.anychart.setChart(pie);
        binding.incomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.income_selector));
                binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
                binding.expenseBtn.setTextColor(getContext().getColor(R.color.textColor));
                binding.incomeBtn.setTextColor(getContext().getColor(R.color.greenColor));
               Constants.SELECTED_STATS_TYPE=Constants.INCOME;
               updateDate();
            }
        });
        binding.expenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
                binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.expense_selector));
                binding.expenseBtn.setTextColor(getContext().getColor(R.color.redColor));
                binding.incomeBtn.setTextColor(getContext().getColor(R.color.textColor));
                Constants.SELECTED_STATS_TYPE=Constants.EXPENSE;
                updateDate();
            }
        });





        return binding.getRoot();
    }

    void updateDate(){
        if(Constants.SELECTED_STATS==Constants.DAILY){
            binding.currentdate.setText(Helper.formatDate(calendar.getTime()));
        }else if(Constants.SELECTED_STATS==Constants.MONTHLY){
            binding.currentdate.setText(Helper.formatDateByMonth(calendar.getTime()));
        }
        viewModel.getTransaction(calendar,Constants.SELECTED_STATS_TYPE);
    }
}