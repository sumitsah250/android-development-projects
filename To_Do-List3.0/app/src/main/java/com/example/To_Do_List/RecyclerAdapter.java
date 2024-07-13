package com.example.To_Do_List;

import static android.app.PendingIntent.getActivity;
import static androidx.core.content.ContextCompat.getSystemService;
import static androidx.core.content.ContextCompat.startActivity;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>  {


    Context context;
    ArrayList<TaskModel> arrtask;
    int id;
    RecyclerAdapter(Context context,ArrayList<TaskModel> arrtask,int id){
        this.context=context;
        this.arrtask=arrtask;
        this.id=id;
    }
    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row,parent,false);
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
        dbhelper3 = new mydbhelper3(context);
        ArrayList<TaskModel> arrTask= new ArrayList<>();

        ArrayList<Contactmodel> arrcontacts = dbhelper3.getcontect();
        ArrayList<String> arrnames = new ArrayList<>();
        ArrayList<String> arrnumbers = new ArrayList<>();
        ArrayList<String> arrtime = new ArrayList<>();
        ArrayList<Boolean> arrstatus = new ArrayList<>();

        for(int i=0;i<arrcontacts.size();i++){
            arrnames.add(arrcontacts.get(i).name);
            arrnumbers.add(arrcontacts.get(i).time);
            arrtime.add(arrcontacts.get(i).date);
            arrstatus.add(arrcontacts.get(i).status);
        }
        for(int i=0;i<arrcontacts.size();i++){
            arrTask.add(new TaskModel(arrnames.get(i),arrnumbers.get(i).toString(),arrtime.get(i),arrstatus.get(i)));

        }
        Contactmodel contactmodel = new Contactmodel();
        contactmodel.id=position;
        contactmodel.name=arrTask.get(position).task;
        contactmodel.date=arrTask.get(position).time; /// due to code issue this got swaped
        contactmodel.time=arrTask.get(position).date;
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    contactmodel.status=TRUE;
                    contactmodel.id=arrcontacts.get(position).id;
                    dbhelper3.UpdateContact(contactmodel);

                }else
                {
                    contactmodel.status=FALSE;
                    contactmodel.id=arrcontacts.get(position).id;
                    dbhelper3.UpdateContact(contactmodel);

                }

            }
        });

        if(id==1 && arrtask.get(position).status){
            holder.task.setText(arrtask.get(position).task);
            holder.task_details.setText(arrtask.get(position).date+" ,"+arrtask.get(position).time);Toast.makeText(context, ""+arrtask.get(position).date, Toast.LENGTH_SHORT).show();
//            try{
//                String[] arr;
//                arr=(arrtask.get(position).date.toString().split(":"));
//                setTimer(Integer.parseInt(arr[0]),Integer.parseInt(arr[1]));
//
//            }catch (Exception e){
//                Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
//            }
            holder.lladd.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    androidx.appcompat.app.AlertDialog.Builder delDialog = new AlertDialog.Builder(context);
                    delDialog.setTitle("Are you sure ");
                    delDialog.setMessage("Do you want to remove this item ?");
                    delDialog.setIcon(R.drawable.icons8_delete);
                    delDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            arrtask.remove(position);
                            notifyItemRemoved(position);
                            Contactmodel contactmodel = new Contactmodel();
                            ArrayList<Contactmodel> arrcontacts = dbhelper3.getcontect();
                            contactmodel.id=arrcontacts.get(position).id;
                            try {
                                dbhelper3.DeleteContact(contactmodel);

                            }catch (Exception e){
                                Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
                    }

                    return true;

                }
            });

            holder.lladd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    args.putInt("key",position);

                    Intent home = new Intent(context,Update_task.class);
                    home.putExtras(args);
                    context.startActivity(home);
//                Bundle bundle1 = getIntent().getExtras();
//                int stuff = bundle1.getInt("Key");
                }
            });
        }

        if (id == 0 && !arrtask.get(position).status){
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

                    androidx.appcompat.app.AlertDialog.Builder delDialog = new AlertDialog.Builder(context);
                    delDialog.setTitle("Are you sure ");
                    delDialog.setMessage("Do you want to remove this item ?");
                    delDialog.setIcon(R.drawable.icons8_delete);
                    delDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            arrtask.remove(position);
                            notifyItemRemoved(position);

                            Contactmodel contactmodel = new Contactmodel();
                            ArrayList<Contactmodel> arrcontacts = dbhelper3.getcontect();
                            contactmodel.id=arrcontacts.get(position).id;
                            try {
                                dbhelper3.DeleteContact(contactmodel);

                            }catch (Exception e){
                                Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
                    }

                    return true;

                }
            });

            holder.lladd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    args.putInt("key",position);
                    Intent home = new Intent(context,Update_task.class);
                    home.putExtras(args);
                    context.startActivity(home);
//                Bundle bundle1 = getIntent().getExtras();
//                int stuff = bundle1.getInt("Key");
                }
            });
       }else{
            holder.task.setText(arrtask.get(position).task);
            holder.task_details.setText(arrtask.get(position).date+" ,"+arrtask.get(position).time);
        }

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
