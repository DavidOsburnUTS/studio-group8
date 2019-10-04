package com.example.studio_group8;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Accountfragment extends Fragment{


    private Context context;
    Button loginsec;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_account,container, false);

        return v;
    }



















}
