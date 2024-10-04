package com.boss.To_Do_List;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


import java.util.ArrayList;

public class my_custom_dialog extends DialogFragment {
    public interface OnInputSelected{
        void sentInput(int input);
    }

    public OnInputSelected mOnInputSelected;
    private static final String TAG ="my_custom_dialog";

                Button yes,cancel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.delete_file, container, false);


        mydbhelper3 dbhelper3;
        dbhelper3 = new mydbhelper3(getActivity().getApplicationContext());

//
        yes = view.findViewById(R.id.yes);
        cancel = view.findViewById(R.id.cancel);


        Bundle mArgs = getArguments();

        int position = mArgs.getInt("key");

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position>=0){
                    Contactmodel contactmodel = new Contactmodel();
                    ArrayList<Contactmodel> arrcontacts = dbhelper3.getcontect();
                    contactmodel.id=arrcontacts.get(position).id;
                    dbhelper3.DeleteContact(contactmodel);
//                    MainActivity.refresh1();

                    dismiss();

                    int input = position;

//                    MainActivity.refresh();
//                    mOnInputSelected.sentInput(input);
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mOnInputSelected =(OnInputSelected) getTargetFragment();

        }catch (ClassCastException e){
            Log.e(TAG,"onAttach:"+e.getMessage());
        }
    }
}

