package com.boss.To_Do_List;

import static java.lang.Boolean.FALSE;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

public class RecyclerAdapter_completed extends RecyclerView.Adapter<RecyclerAdapter_completed.ViewHolder>  {


    AppCompatActivity fragment;
    ArrayList<TaskModel> arrtask;
    int id;
    RecyclerAdapter_completed(FragmentActivity fragment, ArrayList<TaskModel> arrtask, int id){
        this.fragment= (AppCompatActivity) fragment;
        this.arrtask=arrtask;
        this.id=id;
    }
    @NonNull
    @Override
    public RecyclerAdapter_completed.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(fragment).inflate(R.layout.row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter_completed.ViewHolder holder, int position) {
        if (arrtask.get(position).status) {
            holder.task.setChecked(true);
        } else {
            holder.task.setChecked(false);
        }

        mydbhelper3 dbhelper3;
        dbhelper3 = new mydbhelper3(fragment);
        dbhelpermodel dbhelpermodel = new dbhelpermodel();
try {
    dbhelpermodel.id = arrtask.get(position).ID;
    dbhelpermodel.task = arrtask.get(position).task;
    dbhelpermodel.time = arrtask.get(position).time;
    dbhelpermodel.date = arrtask.get(position).date;

}catch (Exception e){
    Toast.makeText(fragment, "unknown error occurred, please refresh  ", Toast.LENGTH_SHORT).show();
}

        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                } else {
                    dbhelpermodel.status = FALSE;
                    dbhelpermodel.id=arrtask.get(position).ID;

                    try {
                        dbhelper3.addContacts(0, dbhelpermodel);
                        dbhelper3.DeleteContact(1, dbhelpermodel);

                        Bundle result = new Bundle();
                        result.putString("data_key", "Your data");
                        fragment.getSupportFragmentManager().setFragmentResult("requestKey_remaining", result);

                    }catch (Exception e){
                        Toast.makeText(fragment, ""+e, Toast.LENGTH_SHORT).show();
                    }

                    try{
                        if (arrtask != null && position >= 0 && position < arrtask.size()) {
                            arrtask.remove(position);
                            notifyItemRemoved(position); // Notify the adapter of the item removal
                            notifyItemRangeChanged(position, arrtask.size());
                        }
                    }catch (Exception e) {
                        Log.d("RecyclerView", ""+e);

                    }




                }

            }
        });

            holder.task.setText(arrtask.get(position).task);
            holder.task_details.setText(arrtask.get(position).date + " ," + arrtask.get(position).time);

            holder.lladd.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    androidx.appcompat.app.AlertDialog.Builder delDialog = new AlertDialog.Builder(fragment);
                    delDialog.setTitle("Are you sure ");
                    delDialog.setMessage("Do you want to remove this item ?");
                    delDialog.setIcon(R.drawable.icons8_delete);
                    delDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dbhelpermodel dbhelpermodel = new dbhelpermodel();
//                            ArrayList<dbhelpermodel> arrcontacts = dbhelper3.getcontect();
                            dbhelpermodel.id = arrtask.get(position).ID;
                            try {
                                dbhelper3.DeleteContact(1, dbhelpermodel);

                            } catch (Exception e) {
                                Toast.makeText(fragment, "" + e, Toast.LENGTH_SHORT).show();
                            }
                            if (arrtask != null && position >= 0 && position < arrtask.size()) {
                                arrtask.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, arrtask.size());// Notify the adapter of the item removal
                            }



                        }
                    });
                    delDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }
                    });
                    try {
                        delDialog.show();
                    } catch (Exception e) {
                        Toast.makeText(fragment, "" + e, Toast.LENGTH_SHORT).show();
                    }

                    return true;

                }
            });



    }

    @Override
    public int getItemCount() {
        return arrtask.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;
        TextView task_details;
        LinearLayout lladd;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            task= (CheckBox)itemView.findViewById(R.id.task);
            task_details=itemView.findViewById(R.id.task_details);
            lladd = itemView.findViewById(R.id.lladd);
        }
    }

}
