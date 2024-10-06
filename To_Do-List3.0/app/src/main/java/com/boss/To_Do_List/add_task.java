package com.boss.To_Do_List;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.boss.gpt.NotificationReceiver;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class add_task extends AppCompatActivity {
    String textDate="";
    String textTime="";
    Toolbar toolbar;
    TextView dateText;
    TextView timeText;

    //gpt notification
    int notihour=0;
    int notimin=0;
    int notiyear=0;
    int notimonth=0;
    int notiday=0;
    //gpt notification

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_task);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //gpt notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "To_Do_List",
                    "To_do_list",
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }

        //gptnotification



        ArrayList<TaskModel> arrTask= new ArrayList<>();
        mydbhelper3 dbhelper3;
        dbhelper3 = new mydbhelper3(this);
        ArrayList<Contactmodel> arrcontacts = dbhelper3.getcontect();
        for(int i=0;i<arrcontacts.size();i++){
            arrTask.add(new TaskModel(arrcontacts.get(i).task,arrcontacts.get(i).date.toString(),arrcontacts.get(i).time,arrcontacts.get(i).status));

        }
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("NEW TASK");
        }
        //toolbar

        EditText taskedt = findViewById(R.id.taskedt);
        RelativeLayout dateedt = findViewById(R.id.dateedt);
        RelativeLayout timeedt =findViewById(R.id.timeedt);
        dateText =findViewById(R.id.datetext);
         timeText =findViewById(R.id.timetext);

        dateedt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog();

            }
        });
        
        timeedt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeDialog();

            }
        });


                EditText edttask = findViewById(R.id.taskedt);
                FloatingActionButton btnAction = findViewById(R.id.btnAction);
                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Contactmodel data = null;
                        String task1 ="";
                        String date1= "";
                        String time1="";
                        if(!edttask.getText().toString().equals("")){
                            task1=edttask.getText().toString();
//                            data.name=task1;
                        }
                        else{
                            Toast.makeText(add_task.this, "task can't be empty", Toast.LENGTH_SHORT).show();
                        }
                        if(!textDate.toString().equals("")){
                            date1=textDate.toString();
//                            data.date=date1;
                        }
                        else {
                            Toast.makeText(add_task.this, "date missing", Toast.LENGTH_SHORT).show();
                        }
                        if(!textTime.toString().equals("")){
                            time1=textTime.toString();
                        }
                        else {
                            Toast.makeText(add_task.this, "time missing", Toast.LENGTH_SHORT).show();
                        }
                        if(!task1.toString().equals("") && !textTime.toString().equals("") && !textDate.toString().equals("")){
//


                            arrTask.add(new TaskModel(task1,date1,time1,false));
                            dbhelper3.addContacts(0,edttask.getText().toString(),textTime.toString(),textDate.toString(),false);
                            //gpt notification
//                            int taskId = (int) System.currentTimeMillis();
                            int hour = notihour;
                            int minute = notimin;
                            long timeInMillis = getMilliseconds(notiyear,notimonth,notiday,notihour, notimin);
//                            testTimeDifference(notiyear,notimonth,notiday,notihour, notimin);

                            scheduleNotification( add_task.this, task1, time1+","+date1, timeInMillis, (int) System.currentTimeMillis());

                            //gpt notification

                            Intent home = new Intent(add_task.this,MainActivity.class);
                            startActivity(home);
                            finish();
                        }
                    }

                });


    }
    public void  dateDialog(){

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        // this is just for style you can remove it//
        DatePickerDialog dilog= new DatePickerDialog(add_task.this,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //this is for the day like sunday,monday
                Calendar c = Calendar.getInstance();
                c.set(year,month,dayOfMonth);
                notiyear=year;
                notimonth=month;
                notiday=day;


                String dayOfTheWeek= String.format("%tA",c);//use a instead of A for miniature form of days
                // this is for the day
                textDate =(String.valueOf(year)+"/"+String.valueOf(month+1)+"/"+dayOfMonth+","+dayOfTheWeek);
                dateText.setText(textDate);

            }
        }, year, month, day);
        dilog.show();

    }
    public String timeDialog(){
        String task ="";
        Calendar c1=Calendar.getInstance();
        TimePickerDialog dialog = new TimePickerDialog(add_task.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                textTime=(hourOfDay+":"+minute);
                timeText.setText(textTime);
                notihour=hourOfDay;
                notimin=minute;
            }
        },c1.get(Calendar.HOUR_OF_DAY),  c1.get(Calendar.MINUTE),false);
        dialog.show();
        return task;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId==android.R.id.home) {
//            Intent home = new Intent(add_task.this,MainActivity.class);
//            startActivity(home);
            super.getOnBackPressedDispatcher();
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    public void scheduleNotification(Context context, String title, String message, long timeInMillis, int notificationId) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("title", title);
        intent.putExtra("message", message);
        intent.putExtra("notificationId", notificationId);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
        }
    }
    public static long getMilliseconds(int year,int month,int day, int hour, int minute) {


        // Create a Calendar instance
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute, 0); // Set the hour, minute, and second
        calendar.set(Calendar.MILLISECOND, 0); // Set milliseconds to 0


        return calendar.getTimeInMillis();
    }
//    public void testTimeDifference(int year,int month,int day, int hour, int minute) {
//        // Create a Calendar instance for the current time
//        Calendar currentCalendar = Calendar.getInstance();
//        long currentTimeMillis = currentCalendar.getTimeInMillis(); // Current time in millis
//
//        // Get the target time in millis using the getTimeInMillis function
//        long targetTimeMillis = getMilliseconds(year,month,day,hour, minute);
//
//        // Calculate the difference in minutes
//        long differenceInMillis = targetTimeMillis - currentTimeMillis;
//        long differenceInMinutes = differenceInMillis / (1000 * 60); // Convert millis to minutes
//
//        // Log the results for debugging in Logcat
//        Log.d("TimeDebug", "Current Time in Millis: " + currentTimeMillis);
//        Log.d("TimeDebug", "Target Time in Millis: " + targetTimeMillis);
//        Log.d("TimeDebug", "Time Difference (Minutes): " + differenceInMinutes);
//    }
}
