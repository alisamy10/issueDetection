package com.example.issuedetection.ui.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.issuedetection.R;

public class AddNewEvent extends Fragment {

    public AddNewEvent() {
        // Required empty public constructor
    }

   public static AddNewEvent newInstance(){
        return new AddNewEvent();
   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_event, container, false);
    }


}
