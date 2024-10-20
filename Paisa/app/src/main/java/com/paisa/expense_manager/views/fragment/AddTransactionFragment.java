package com.paisa.expense_manager.views.fragment;

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
import android.widget.Toast;

import com.paisa.expense_manager.R;
import com.paisa.expense_manager.adapters.AccountAdapter;
import com.paisa.expense_manager.adapters.CategoryAdapter;
import com.paisa.expense_manager.databinding.FragmentAddTransactionBinding;
import com.paisa.expense_manager.databinding.ListDialogBinding;
import com.paisa.expense_manager.models.Category;
import com.paisa.expense_manager.models.Transaction;
import com.paisa.expense_manager.models.account;
import com.paisa.expense_manager.utils.Constants;
import com.paisa.expense_manager.views.activity.MainActivity;
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
        //default settings
        binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.income_selector));
        binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
        binding.expenseBtn.setTextColor(getContext().getColor(R.color.textColor));
        binding.incomeBtn.setTextColor(getContext().getColor(R.color.greenColor));
        transaction.setType(Constants.INCOME);
        //

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
                String note="";
                double amount=0;
                if(!binding.amount.getText().toString().equals("")){
                    amount= Double.parseDouble(binding.amount.getText().toString());
                }
                if(!binding.note.getText().toString().equals("")){
                    note = binding.note.getText().toString();
                }



                if(transaction.getType().equals(Constants.EXPENSE)){
                    transaction.setAmount(amount*-1);
                }else{
                    transaction.setAmount(amount);
                }
                transaction.setAmount(amount);
                transaction.setNote(note);
                if (binding.date.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please select the date", Toast.LENGTH_SHORT).show();
                }
                 else if(amount==0.0){
                    Toast.makeText(getContext(), "Please enter the Amount", Toast.LENGTH_SHORT).show();
                }else if (binding.category.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please select the Category", Toast.LENGTH_SHORT).show();
                }
                 else if (binding.account.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please select the account type", Toast.LENGTH_SHORT).show();
                }   else if (note.equals("")) {
                    Toast.makeText(getContext(), "please enter the note", Toast.LENGTH_SHORT).show();

                }else{
                    ((MainActivity)getActivity()).viewModel.addTransaction(transaction);//transaction
                    ((MainActivity)getActivity()).getTransaction_details();
                    dismiss();
                }

            }
        });



        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}