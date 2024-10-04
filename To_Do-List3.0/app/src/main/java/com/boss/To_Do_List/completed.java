package com.boss.To_Do_List;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;


public class completed extends Fragment {
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view =inflater.inflate(R.layout.fragment_completed, container, false);
        recyclerView =view.findViewById(R.id.completed_recycler);

        ArrayList<TaskModel> arrTask= new ArrayList<>();
        RecyclerAdapter adapter= new RecyclerAdapter(getActivity(),arrTask,1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setAdapter(adapter);


        mydbhelper3 dbhelper3;
        dbhelper3 = new mydbhelper3(getActivity().getApplicationContext());

        ArrayList<Contactmodel> arrcontacts = dbhelper3.getcontect();
        ArrayList<String> arrnames = new ArrayList<>();
        ArrayList<String> arrnumbers = new ArrayList<>();
        ArrayList<String> arrtime = new ArrayList<>();
        ArrayList<Boolean> arrstatus= new ArrayList<>();
        for(int i=0;i<arrcontacts.size();i++){
            arrnames.add(arrcontacts.get(i).name);
            arrnumbers.add(arrcontacts.get(i).time);
            arrtime.add(arrcontacts.get(i).date);
            arrstatus.add(arrcontacts.get(i).status);
        }

        for(int i=0;i<arrcontacts.size();i++){
                arrTask.add(new TaskModel(arrnames.get(i),arrnumbers.get(i).toString(),arrtime.get(i),arrstatus.get(i)));
                adapter.notifyItemInserted(arrTask.size()-1);
                recyclerView.scrollToPosition(arrTask.size()-1);
        }

        // Inflate the layout for this fragment
        return view;
    }
}