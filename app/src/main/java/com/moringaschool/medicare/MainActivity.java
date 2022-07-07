package com.moringaschool.medicare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.moringaschool.medicare.ui.activities.SplashScreenActivity;
import com.moringaschool.medicare.ui.fragments.ChooseDoctorFragment;
import com.moringaschool.medicare.ui.fragments.HealthSolutionFragment;
import com.moringaschool.medicare.ui.fragments.OrderFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        name = findViewById(R.id.txt);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SplashScreenActivity.class);
                startActivity(intent);
            }
        });

    }
}