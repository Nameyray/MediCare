package com.moringaschool.medicare.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.moringaschool.medicare.R;
import com.moringaschool.medicare.ui.fragments.ChooseDoctorFragment;
import com.moringaschool.medicare.ui.fragments.HealthSolutionFragment;
import com.moringaschool.medicare.ui.fragments.OrderFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreenActivity extends AppCompatActivity implements View.OnClickListener{


    @BindView(R.id.txt3) TextView mSkip;
    @BindView(R.id.txt4) TextView mNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ButterKnife.bind(this);
        mSkip.setOnClickListener(this);
        mNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mSkip){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame1, new HealthSolutionFragment()).commit();
//            replaceFragment(new HealthSolutionFragment());
        }

        if (v == mNext){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame1, new ChooseDoctorFragment()).commit();
//            replaceFragment(new OrderFragment());

        }

    }

//    private void replaceFragment(Fragment fragment) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.frameLayout, fragment);
//        fragmentTransaction.commit();
//    }
}