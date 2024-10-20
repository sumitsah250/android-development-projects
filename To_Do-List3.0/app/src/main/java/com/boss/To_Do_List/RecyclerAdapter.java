package com.boss.To_Do_List;

import static android.app.PendingIntent.getActivity;

import static java.lang.Boolean.TRUE;

import android.content.DialogInterface;
import android.content.Intent;
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
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>  {


   AppCompatActivity fragment;
    ArrayList<TaskModel> arrtask;

    RecyclerAdapter(AppCompatActivity fragment, ArrayList<TaskModel> arrtask, int id){
        this.fragment=fragment;
        this.arrtask=arrtask;
    }
    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(fragment).inflate(R.layout.row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {

        if(arrtask.get(position).status){
            holder.task.setChecked(true);
        }else{
            holder.task.setChecked(false);
        }

        mydbhelper3 dbhelper3;
        dbhelper3 = new mydbhelper3(fragment);


        dbhelpermodel dbhelpermodel = new dbhelpermodel();
        dbhelpermodel.id= arrtask.get(position).ID;
        dbhelpermodel.task=arrtask.get(position).task;
        dbhelpermodel.time=arrtask.get(position).time;
        dbhelpermodel.date=arrtask.get(position).date;
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                   dbhelpermodel.status=TRUE;
                   dbhelpermodel.id=arrtask.get(position).ID;

                    try {
                        dbhelper3.addContacts(1, dbhelpermodel);
                        dbhelper3.DeleteContact(0, dbhelpermodel);

                        Bundle result = new Bundle();
                        result.putString("data_key", "Your data");
                        fragment.getSupportFragmentManager().setFragmentResult("requestKey_completed", result);

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
                //if ends here

            }
        });




//        if (!arrtask.get(position).status){
            holder.task.setText(arrtask.get(position).task);
            holder.task_details.setText(arrtask.get(position).date+" ,"+arrtask.get(position).time);

//              try{
//                  String[] arr;
//                  arr=(arrtask.get(position).date.toString().split(":"));
//                  setTimer(Integer.parseInt(arr[0]),Integer.parseInt(arr[1]));
//
//              }catch (Exception e){
//                  Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
//              }

            holder.lladd.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
//                    Bundle args = new Bundle();
//                    args.putInt("key",position);
//                    my_custom_dialog newFragment = new my_custom_dialog();
//                    newFragment.setArguments(args);
//                    FragmentActivity activity = (FragmentActivity)(context);
//                    FragmentManager fm = activity.getSupportFragmentManager();
//                    newFragment.show(fm, "fragment_alert");

                    androidx.appcompat.app.AlertDialog.Builder delDialog = new AlertDialog.Builder(fragment);
                    delDialog.setTitle("Are you sure ");
                    delDialog.setMessage("Do you want to remove this item ?");
                    delDialog.setIcon(R.drawable.icons8_delete);
                    delDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dbhelpermodel dbhelpermodel = new dbhelpermodel();
//                            ArrayList<dbhelpermodel> arrcontacts = dbhelper3.getcontect();
                            dbhelpermodel.id=arrtask.get(position).ID;
                            try {
                                dbhelper3.DeleteContact(0, dbhelpermodel);

                            }catch (Exception e){
                                Toast.makeText(fragment, ""+e, Toast.LENGTH_SHORT).show();
                            }
                            try{
                                if (arrtask != null && position >= 0 && position < arrtask.size()) {
                                    arrtask.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, arrtask.size());// Notify the adapter of the item removal
                                }
                            }catch (Exception e){

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
                    }catch(Exception e){
                        Toast.makeText(fragment, ""+e, Toast.LENGTH_SHORT).show();
                    }

                    return true;

                }
            });

            holder.lladd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    args.putInt("key",position);
                    Intent home = new Intent(fragment,Update_task.class);
                    home.putExtras(args);
                    fragment.startActivity(home);
//                Bundle bundle1 = getIntent().getExtras();
//                int stuff = bundle1.getInt("Key");
                }
            });
//      } else{
////            holder.task.setText(arrtask.get(position).task);
////            holder.task_details.setText(arrtask.get(position).date+" ,"+arrtask.get(position).time);
//            try{
//                holder.itemView.setVisibility(View.GONE);
//                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
//
//            }catch (Exception e){
//                Toast.makeText(context, ""+"here in else ", Toast.LENGTH_SHORT).show();
//            }
//
//        }

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

//    private void setTimer(int jam,int menit) {
//        AlarmManager alarmManager  = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//
//        Date date = new Date();
//
//        Calendar cal_alarm = Calendar.getInstance();
//        Calendar cal_now = Calendar.getInstance();
//
//        cal_now.setTime(date);
//        cal_alarm.setTime(date);
//
//        cal_alarm.set(Calendar.HOUR_OF_DAY, jam);
//        cal_alarm.set(Calendar.MINUTE, menit);
//        cal_alarm.set(Calendar.SECOND, 0);
//
//        if(cal_alarm.before(cal_now)){
//            cal_alarm.add(Calendar.DATE, 1);
//        }
//
//        Intent i = new Intent(context, MyBroadcastReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(),pendingIntent);
//    }


}
