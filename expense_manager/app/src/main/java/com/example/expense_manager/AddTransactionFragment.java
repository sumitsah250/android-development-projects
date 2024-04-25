package com.example.expense_manager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.expense_manager.databinding.FragmentAddTransactionBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddTransactionFragment extends BottomSheetDialogFragment {


    public AddTransactionFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentAddTransactionBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentAddTransactionBinding.inflate(inflater);
        binding.incomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.income_selector));
                binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
                binding.expenseBtn.setTextColor(getContext().getColor(R.color.textColor));
                binding.incomeBtn.setTextColor(getContext().getColor(R.color.greenColor));
            }
        });
        binding.expenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
                binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.expense_selector));
                binding.expenseBtn.setTextColor(getContext().getColor(R.color.redColor));
                binding.incomeBtn.setTextColor(getContext().getColor(R.color.textColor));
            }
        });
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}