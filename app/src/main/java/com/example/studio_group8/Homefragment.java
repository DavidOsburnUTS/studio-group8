package com.example.studio_group8;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Homefragment extends Fragment implements View.OnClickListener {


    FloatingActionButton fabmain, fabone, fabtwo;
    Float translationY = 100f;
    OvershootInterpolator interpolator = new OvershootInterpolator();


    private static String TAG = "MainActivity";

    Boolean isMenuOpen = false;

    private Context context;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_home_seller,container, false);
        fabmain = (FloatingActionButton) v.findViewById(R.id.fabmain);
        fabone = (FloatingActionButton) v.findViewById(R.id.fab1);


        fabone.setAlpha(0f);

        fabone.setTranslationY(translationY);

        fabmain.setOnClickListener(this);
//        fabone.setOnClickListener(this);

        return v;


    }



/*    private void initFabMenu() {



        fabone.setAlpha(0f);
        fabtwo.setAlpha(0f);

        fabone.setTranslationY(translationY);
        fabtwo.setTranslationY(translationY);


        fabmain.setOnClickListener(this);
        fabone.setOnClickListener(this);
        fabtwo.setOnClickListener(this);


    }*/



    private void openMenu() {
        isMenuOpen = !isMenuOpen;

        fabmain.animate().setInterpolator(interpolator).rotation(45f).setDuration(300).start();

        fabone.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();

    }

    private void closeMenu() {
        isMenuOpen = !isMenuOpen;

        fabmain.animate().setInterpolator(interpolator).rotation(0f).setDuration(300).start();
        fabone.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();

    }



    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.fabmain:
                Log.i(TAG, "onClick: fab main");
                if (isMenuOpen) {
                    closeMenu();
                } else {
                    openMenu();
                }
                break;
     /*       case R.id.fab1:
                Log.i(TAG, "onClick: fab one");
                if (isMenuOpen) {
                    closeMenu();
                } else {
                    openMenu();
                }
                break;*/
        }
    }



}
