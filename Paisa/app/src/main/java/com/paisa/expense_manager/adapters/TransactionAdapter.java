package com.paisa.expense_manager.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paisa.expense_manager.R;
import com.paisa.expense_manager.databinding.RowTransactionBinding;
import com.paisa.expense_manager.models.Category;
import com.paisa.expense_manager.models.Transaction;
import com.paisa.expense_manager.utils.Constants;
import com.paisa.expense_manager.utils.Helper;
import com.paisa.expense_manager.views.activity.MainActivity;

import io.realm.RealmResults;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHOlder> {
    Context context;
   RealmResults<Transaction> transactions;
    public TransactionAdapter(Context context, RealmResults<Transaction> transactions){
        this.transactions=transactions;
        this.context=context;

    }




    @NonNull
    @Override
    public TransactionViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionViewHOlder(LayoutInflater.from(context).inflate(R.layout.row_transaction, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHOlder holder, int position) {
        Transaction transaction = transactions.get(position);

        holder.binding.transationamount.setText(String.valueOf(transaction.getAmount()));
        holder.binding.accountlabel.setText(transaction.getAccount());

        holder.binding.transationdate.setText(Helper.formatDate(transaction.getDate()));
        holder.binding.transationCatogary.setText(transaction.getCategory());

        Category transactionCategegory =  Constants.getCategoryDetails(transaction.getCategory());
        holder.binding.catagoryicon.setImageResource(transactionCategegory.getCategoryImage());
        holder.binding.catagoryicon.setBackgroundTintList(context.getColorStateList(transactionCategegory.getCategoryColour()));

        holder.binding.accountlabel.setBackgroundTintList(context.getColorStateList(Constants.getAccountColor(transaction.getAccount())));



        if(transaction.getType().equals(Constants.INCOME)){
            holder.binding.transationamount.setTextColor(context.getColor(R.color.greenColor));
        }else if(transaction.getType().equals(Constants.EXPENSE)) {
            holder.binding.transationamount.setTextColor(context.getColor(R.color.redColor));

        }
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog deleteDialog = new AlertDialog.Builder(context).create();
                deleteDialog.setTitle("Delete Transaction");
                deleteDialog.setMessage("Are you sure to delete this transaction?");
                deleteDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", (dialogInterface, i) -> {
                    ((MainActivity)context).viewModel.deleteTransaction(transaction);
                });
                deleteDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", (dialogInterface, i) -> {
                    deleteDialog.dismiss();
                });
                deleteDialog.show();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class TransactionViewHOlder extends RecyclerView.ViewHolder{
        RowTransactionBinding binding;
        public TransactionViewHOlder(@NonNull View itemView) {
            super(itemView);
            binding = RowTransactionBinding.bind(itemView);
        }
    }
}
