package com.boss.To_Do_List;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;


public class remaining extends Fragment{

    //this was for data from dialog fragment
//        implements my_custom_dialog.OnInputSelected


    private static final String TAG = "Remaining";
    RecyclerView recyclerView;
    FloatingActionButton floatbtn;
    ArrayList<TaskModel> arrTask= new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //to show the data
        View view;
        view = inflater.inflate(R.layout.fragment_remaining, container, false);


        recyclerView =view.findViewById(R.id.remaining_recycler);
//        ArrayList<TaskModel> arrTask= new ArrayList<>();

        RecyclerAdapter adapter= new RecyclerAdapter(getActivity(),arrTask,0);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setAdapter(adapter);
        //to show the data




        mydbhelper3 dbhelper3;
        dbhelper3 = new mydbhelper3(getActivity().getApplicationContext());
        ArrayList<Contactmodel> arrcontacts = dbhelper3.getcontect();
        for(int i=0;i<arrcontacts.size();i++){
            arrTask.add(new TaskModel(arrcontacts.get(i).task,arrcontacts.get(i).date.toString(),arrcontacts.get(i).time,arrcontacts.get(i).status,arrcontacts.get(i).id));
            adapter.notifyItemInserted(arrTask.size()-1);

        }




        floatbtn= view.findViewById(R.id.remaining_opendialog);

        floatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent home = new Intent(getActivity().getApplicationContext(),add_task.class);
                startActivity(home);
            }

        });

        return view;
    }

//for data passing from the fragment dialog
//    @Override
//    public void sentInput(int input) {
//        arrTask.remove(input);
//    }
    //for data passing from the fragment dialog





}