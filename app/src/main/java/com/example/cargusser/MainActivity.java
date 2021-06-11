package com.example.cargusser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    int score = 0;

    boolean timerState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


//        adding on click functionality for menu buttons

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Switch timer = findViewById(R.id.timerSwitch);
        timer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    timerState = true;
                }else {
                    timerState = false;
                }
                Log.d("test", Boolean.toString(timerState));
            }
        });

        Log.d("test", Boolean.toString(timerState));

        Button idCarBtn = (Button) findViewById(R.id.idCarMakeBtn);
        idCarBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startIdCar();
            }
        });


        Button hints = (Button) findViewById(R.id.hintsBtn);
        hints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHints();

            }
        });

        Button idCarImageBtn = (Button) findViewById(R.id.idCarImageBtn);
        idCarImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idCarImage();

            }
        });

        Button avdLevelBtn = (Button) findViewById(R.id.avdLevelBtn);
        avdLevelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartAvdLevel();

            }
        });



    }

//    methods for the button functionality

    private void StartAvdLevel() {
        Intent passData =new Intent(this, AdvancedLevel.class);
        passData.putExtra("score",score);
        passData.putExtra("timer",timerState);
        startActivity(passData);
    }

    private void startIdCar() {
        Intent intent = new Intent(this, identifyCar.class);
        intent.putExtra("timer",timerState);
        startActivity(intent);
    }


    private void startHints() {
        Intent intent = new Intent(this, Hints.class);
        intent.putExtra("timer",timerState);
        startActivity(intent);
    }

    private void idCarImage() {
        Intent intent = new Intent(this, IdentifyCarImage.class);
        intent.putExtra("timer",timerState);
        startActivity(intent);
    }
}