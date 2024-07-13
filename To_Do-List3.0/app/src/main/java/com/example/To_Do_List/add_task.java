package com.example.To_Do_List;

import static java.lang.Boolean.FALSE;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;

public class add_task extends AppCompatActivity {
    String textDate="";
    String textTime="";
    Toolbar toolbar;
    TextView dateText;
    TextView timeText;

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



        ArrayList<TaskModel> arrTask= new ArrayList<>();
        mydbhelper3 dbhelper3;
        dbhelper3 = new mydbhelper3(this);
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


        //toolbar
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
                            arrTask.add(new TaskModel(task1,date1,time1,false));
                            dbhelper3.addContacts(edttask.getText().toString(),textTime.toString(),textDate.toString(),false);
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
        TimePickerDialog dialog = new TimePickerDialog(add_task.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                textTime=(hourOfDay+":"+minute);
                timeText.setText(textTime);
            }
        },15, 0,false);
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
}