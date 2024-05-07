package com.example.expense_manager.views.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.expense_manager.R;
import com.example.expense_manager.adapters.AccountAdapter;
import com.example.expense_manager.adapters.CategoryAdapter;
import com.example.expense_manager.databinding.FragmentAddTransactionBinding;
import com.example.expense_manager.databinding.ListDialogBinding;
import com.example.expense_manager.models.Category;
import com.example.expense_manager.models.Transaction;
import com.example.expense_manager.models.account;
import com.example.expense_manager.utils.Constants;
import com.example.expense_manager.viewmodel.MainViewModel;
import com.example.expense_manager.views.activity.MainActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddTransactionFragment extends BottomSheetDialogFragment {


    public AddTransactionFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentAddTransactionBinding binding;
    Transaction transaction;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentAddTransactionBinding.inflate(inflater);
        transaction= new Transaction();

        binding.incomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.income_selector));
                binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
                binding.expenseBtn.setTextColor(getContext().getColor(R.color.textColor));
                binding.incomeBtn.setTextColor(getContext().getColor(R.color.greenColor));
                transaction.setType(Constants.INCOME);
            }
        });
        binding.expenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
                binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.expense_selector));
                binding.expenseBtn.setTextColor(getContext().getColor(R.color.redColor));
                binding.incomeBtn.setTextColor(getContext().getColor(R.color.textColor));
                transaction.setType(Constants.EXPENSE);
            }
        });

        binding.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog= new DatePickerDialog(getContext());
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.YEAR,year);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM,yyyy");
                        String dateToShow = dateFormat.format(calendar.getTime());
                        binding.date.setText(dateToShow);
                        transaction.setDate(calendar.getTime());
                        transaction.setId(calendar.getTime().getTime());

                    }
                });
                datePickerDialog.show();
            }
        });
        binding.category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListDialogBinding dialogBinding= ListDialogBinding.inflate(inflater);
                AlertDialog categoryDialog= new AlertDialog.Builder(getContext()).create();
                categoryDialog.setView(dialogBinding.getRoot());
                CategoryAdapter  categoryAdapter = new CategoryAdapter(getContext(), Constants.categories, new CategoryAdapter.CategoryClickListener() {
                    @Override
                    public void onCategoryClicked(Category category) {
                        binding.category.setText(category.getCategoryName());
                        transaction.setCategory(category.getCategoryName());
                        categoryDialog.dismiss();
                    }
                });
                dialogBinding.RecyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
                dialogBinding.RecyclerView.setAdapter(categoryAdapter);
                categoryDialog.show();
            }
        });

        binding.account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListDialogBinding dialogBinding= ListDialogBinding.inflate(inflater);
                AlertDialog accountsDialog= new AlertDialog.Builder(getContext()).create();
               accountsDialog.setView(dialogBinding.getRoot());
               ArrayList<account> accounts= new ArrayList<>();
               accounts.add(new account(0,"Cash"));
               accounts.add(new account(0,"Bank"));
               accounts.add(new account(0,"Esewa"));
               accounts.add(new account(0,"Khalti"));
               accounts.add(new account(0,"Others"));

                AccountAdapter  adapter= new AccountAdapter(getContext(), accounts, new AccountAdapter.AccountsClickListner() {
                    @Override
                    public void onAccountSelectd(account account) {
                        binding.account.setText(account.getAccountName());
                        transaction.setAccount(account.getAccountName());
                        accountsDialog.dismiss();
                    }
                });

                dialogBinding.RecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                dialogBinding.RecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
                dialogBinding.RecyclerView.setAdapter(adapter);
                accountsDialog.show();
            }
        });
        binding.saveTransactionbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount = Double.parseDouble(binding.amount.getText().toString());
                String note = binding.note.getText().toString();
                if(transaction.getType().equals(Constants.EXPENSE)){
                    transaction.setAmount(amount*-1);
                }else{
                    transaction.setAmount(amount);
                }
                transaction.setAmount(amount);
                transaction.setNote(note);
                ((MainActivity)getActivity()).viewModel.addTransaction(transaction);//transaction
                ((MainActivity)getActivity()).getTransaction_details();
                dismiss();
            }
        });



        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}