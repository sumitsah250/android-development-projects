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
        RecyclerAdapter_completed adapter= new RecyclerAdapter_completed(getActivity(),arrTask,1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setAdapter(adapter);


        mydbhelper3 dbhelper3;
        dbhelper3 = new mydbhelper3(getActivity().getApplicationContext());

        ArrayList<dbhelpermodel> arrcontacts = dbhelper3.getcontect1();
        for(int i=0;i<arrcontacts.size();i++){
            arrTask.add(new TaskModel(arrcontacts.get(i).task,arrcontacts.get(i).date.toString(),arrcontacts.get(i).time,arrcontacts.get(i).status,arrcontacts.get(i).id));
            adapter.notifyItemInserted(arrTask.size()-1);
        }

        // Inflate the layout for this fragment

        getParentFragmentManager().setFragmentResultListener("requestKey_completed", this, (key, bundle) -> {
//            String result = bundle.getString("data_key");
//            Toast.makeText(getContext(), "completed"+result, Toast.LENGTH_SHORT).show();
            mydbhelper3 dbhelper0;
            dbhelper0 = new mydbhelper3(getActivity().getApplicationContext());
            ArrayList<dbhelpermodel> arrcontacts0 = dbhelper0.getcontect1();

            arrTask.add(new TaskModel(arrcontacts0.get(arrcontacts0.size()-1).task,arrcontacts0.get(arrcontacts0.size()-1).date.toString(),arrcontacts0.get(arrcontacts0.size()-1).time,arrcontacts0.get(arrcontacts0.size()-1).status,arrcontacts0.get(arrcontacts0.size()-1).id));
            adapter.notifyItemInserted(arrTask.size()-1);
            // Handle the result
        });
        return view;
    }
}