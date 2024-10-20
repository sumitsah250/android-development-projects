package com.boss.To_Do_List;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.Calendar;

public class Update_task extends AppCompatActivity {

    Toolbar toolbar;
    TextView dateText;
    TextView timeText;
    String task1 ="";
    String date1= "";
    String time1="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_task);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Bundle bundle1 = getIntent().getExtras();
        int position = bundle1.getInt("key");

        mydbhelper3 dbhelper3;
        dbhelper3 = new mydbhelper3(Update_task.this);


        ArrayList<TaskModel> arrTask= new ArrayList<>();
        ArrayList<dbhelpermodel> arrcontacts = dbhelper3.getcontect();
        for(int i=0;i<arrcontacts.size();i++){
            arrTask.add(new TaskModel(arrcontacts.get(i).task,arrcontacts.get(i).date.toString(),arrcontacts.get(i).time,arrcontacts.get(i).status));
        }

        //toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Update TASK");
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


        edttask.setText(arrTask.get(position).task);
        dateText.setText(arrTask.get(position).date);
        timeText.setText(arrTask.get(position).time);


        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbhelpermodel data = null;
//                edttask.setText(arrTask.get(position).task);
//                dateText.setText(arrTask.get(position).date);
//                timeText.setText(arrTask.get(position).time);

                if(!edttask.getText().toString().equals("")){
                    task1=edttask.getText().toString();
                }
                else{
                    Toast.makeText(Update_task.this, "task can't be empty", Toast.LENGTH_SHORT).show();
                }
                if(!dateText.toString().equals("")){
                    date1=dateText.toString();

                }
                else {
                    Toast.makeText(Update_task.this, "date missing", Toast.LENGTH_SHORT).show();
                }
                if(!timeText.toString().equals("")){
                    time1=dateText.toString();
                }
                else {
                                Toast.makeText(Update_task.this, "time missing", Toast.LENGTH_SHORT).show();
                }
                if(!task1.toString().equals("") && !dateText.toString().equals("") && !timeText.toString().equals("")){
                    dbhelpermodel dbhelpermodel = new dbhelpermodel();
                    dbhelpermodel.id=position;

                    dbhelpermodel.task=edttask.getText().toString();
                    dbhelpermodel.date=dateText.getText().toString();
                    dbhelpermodel.time=timeText.getText().toString();
                    ArrayList<dbhelpermodel> arrcontacts = dbhelper3.getcontect();
                    dbhelpermodel.id=arrcontacts.get(position).id;
                    dbhelper3.UpdateContact(0, dbhelpermodel);
                    Intent home = new Intent(Update_task.this,MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "value");
                    home.putExtras(bundle);
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
        DatePickerDialog dilog= new DatePickerDialog(Update_task.this,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //this is for the day like sunday,monday
                Calendar c = Calendar.getInstance();
                c.set(year,month,dayOfMonth);


                String dayOfTheWeek= String.format("%tA",c);//use a instead of A for miniature form of days
                // this is for the day
                dateText.setText(String.valueOf(year)+"/"+String.valueOf(month+1)+"/"+dayOfMonth+","+dayOfTheWeek);

            }
        }, year, month, day);
        dilog.show();

    }
    public String timeDialog(){
        String task ="";
        TimePickerDialog dialog = new TimePickerDialog(Update_task.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                timeText.setText(hourOfDay+":"+minute);
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