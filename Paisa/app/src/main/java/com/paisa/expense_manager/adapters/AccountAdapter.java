package com.paisa.expense_manager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paisa.expense_manager.R;
import com.paisa.expense_manager.databinding.RowAccountsBinding;
import com.paisa.expense_manager.models.account;

import java.util.ArrayList;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountsViewHolder> {
    Context context;
    ArrayList<account> accountArrayList;

    public interface AccountsClickListner {
        void onAccountSelectd(account account);
    }
    AccountsClickListner accountsClickListner;

    public AccountAdapter(Context context, ArrayList<account> accountArrayList,AccountsClickListner accountsClickListner){
        this.context=context;
        this.accountArrayList=accountArrayList;
        this.accountsClickListner= accountsClickListner;
    }




    @NonNull
    @Override
    public AccountsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AccountsViewHolder(LayoutInflater.from(context).inflate(R.layout.row_accounts,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AccountsViewHolder holder, int position) {
        account account = accountArrayList.get(position);
        holder.binding.accountName.setText(account.getAccountName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountsClickListner.onAccountSelectd(account);
            }
        });

    }

    @Override
    public int getItemCount() {
        return accountArrayList.size();
    }

    public class AccountsViewHolder extends RecyclerView.ViewHolder{
        RowAccountsBinding binding;

        public AccountsViewHolder(@NonNull View itemView) {
            super(itemView);
            binding= RowAccountsBinding.bind(itemView);
        }
    }
}
