package com.example.cargusser;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Random;

import static com.example.cargusser.identifyCar.getResourceID;

public class AdvancedLevel extends AppCompatActivity {

//    declaring variables
    final Random random = new Random();
    HashMap<String, String> carList = new HashMap<>();
    ImageView displayUniqueCar1;
    ImageView displayUniqueCar2;
    ImageView displayUniqueCar3;
    TextView scoreDisplay;
    TextView avdDisplayTimer;
    EditText textInput1;
    EditText textInput2;
    EditText textInput3;
    Button avdSubmitBtn;
    String uniqueCar0;
    String uniqueCar1;
    String uniqueCar2;
    CountDownTimer gameTimer;
    public int counter = 20;
    boolean timerState = false;
    int submitPressCount = 0;
    int submitDisplayedCount = 3;
    int score = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_level);

//        getting timer state and score from previous activity

        Bundle bundle = getIntent().getExtras();
        int passValue = bundle.getInt("score");
        score = score + passValue;
        scoreDisplay = findViewById(R.id.scoreDisplay);
        createCarHashMap();

        Bundle data = getIntent().getExtras();
        timerState = data.getBoolean("timer");

        avdDisplayTimer = findViewById(R.id.avdTimer);

//        cheking if timer is switch on

        if (timerState == true) {

            timerFunction();
        }


//        picking 3 random car images
        uniqueCar0 = "car_" + random.nextInt(29);
        uniqueCar1 = "car_" + random.nextInt(29);
        uniqueCar2 = "car_" + random.nextInt(29);

//        preventing the same car being picked twice
        while (uniqueCar0.equals(uniqueCar1)) {
            uniqueCar1 = "car_" + random.nextInt(29);
        }

        while (uniqueCar2.equals(uniqueCar0)) {
            uniqueCar2 = "car_" + random.nextInt(29);
        }

        while (uniqueCar2.equals(uniqueCar1)) {
            uniqueCar2 = "car_" + random.nextInt(29);
        }


        displayUniqueCar1 = (ImageView) findViewById(R.id.uniqueCarImg1);
        displayUniqueCar2 = (ImageView) findViewById(R.id.uniqueCarImg2);
        displayUniqueCar3 = (ImageView) findViewById(R.id.uniqueCarImg3);

//        displaying 3 car images

        displayUniqueCar1.setImageDrawable(
                getResources().getDrawable(getResourceID(uniqueCar0, "drawable", getApplicationContext()))
        );

        displayUniqueCar2.setImageDrawable(
                getResources().getDrawable(getResourceID(uniqueCar1, "drawable", getApplicationContext()))
        );

        displayUniqueCar3.setImageDrawable(
                getResources().getDrawable(getResourceID(uniqueCar2, "drawable", getApplicationContext()))
        );


        textInput1 = findViewById(R.id.txtInput1);
        textInput2 = findViewById(R.id.txtInput2);
        textInput3 = findViewById(R.id.txtInput3);


        avdSubmitBtn = findViewById(R.id.avdSubmitBtn);
        scoreDisplay.setText("Score : " + String.valueOf(score));

        avdSubmitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                validatingAnswer();
            }
        });


    }

//    function for 20 second timer
    private void timerFunction() {
        gameTimer = new CountDownTimer(20000, 1000) {
            public void onTick(long millisUntilFinished) {
                avdDisplayTimer.setText("TIME :" + String.valueOf(counter));
                counter--;
            }

            @Override
            public void onFinish() {
                if (submitPressCount >= 2) {
                    avdDisplayTimer.setText("Times UP!");
                    validatingAnswer();
                } else {
                    validatingAnswer();
                    timerFunction();
                    counter = 20;
                }

            }

        }.start();
    }

//    method to validate if the used entered answer is correct

    public void validatingAnswer() {

        submitPressCount++;
        submitDisplayedCount--;
        String text = String.valueOf(score);
        Toast.makeText(getApplicationContext(), submitDisplayedCount + "Tries Left", Toast.LENGTH_SHORT).show();

//        checking if submit button is pressed less than 3times
        if (submitPressCount < 3) {

            if (textInput1.getText().toString().equalsIgnoreCase(carList.get(uniqueCar0))) {
                textInput1.setTextColor(Color.parseColor("#FF42930E"));
                textInput1.setEnabled(false);

            } else {
                textInput1.setTextColor(Color.RED);
            }

            if (textInput2.getText().toString().equalsIgnoreCase(carList.get(uniqueCar1))) {
                textInput2.setTextColor(Color.parseColor("#FF42930E"));
                textInput2.setEnabled(false);

            } else {
                textInput2.setTextColor(Color.RED);
            }

            if (textInput3.getText().toString().equalsIgnoreCase(carList.get(uniqueCar2))) {
                textInput3.setTextColor(Color.parseColor("#FF42930E"));
                textInput3.setEnabled(false);

            } else {
                textInput3.setTextColor(Color.RED);
            }


            if (textInput1.getText().toString().equalsIgnoreCase(carList.get(uniqueCar0)) &&
                    textInput2.getText().toString().equalsIgnoreCase(carList.get(uniqueCar1)) &&
                    textInput3.getText().toString().equalsIgnoreCase(carList.get(uniqueCar2))) {
                avdSubmitBtn.setText("Next");
                score = score + 3;
                avdSubmitBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        nextScreen(v);
                    }
                });

            }
        } else {
            if (!textInput1.getText().toString().equalsIgnoreCase(carList.get(uniqueCar0))) {
                textInput1.setTextColor(Color.parseColor("#FFD600"));
                textInput1.setText(carList.get(uniqueCar0));
                textInput1.setEnabled(false);

            } else {
                score = score + 1;
            }
            if (!textInput2.getText().toString().equalsIgnoreCase(carList.get(uniqueCar1))) {
                textInput2.setTextColor(Color.parseColor("#FFD600"));
                textInput2.setText(carList.get(uniqueCar1));
                textInput2.setEnabled(false);


            } else {
                score = score + 1;
            }
            if (!textInput3.getText().toString().equalsIgnoreCase(carList.get(uniqueCar2))) {
                textInput3.setTextColor(Color.parseColor("#FFD600"));
                textInput3.setText(carList.get(uniqueCar2));
                textInput3.setEnabled(false);

            } else {
                score = score + 1;
            }
            avdSubmitBtn.setText("Next");
            avdSubmitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nextScreen(v);
                }
            });
        }
        scoreDisplay.setText("SCORE : " + String.valueOf(score));

    }

//    method to pass the score to nect activity
    public void nextScreen(View v) {
        Intent passData = new Intent(this, AdvancedLevel.class);
        passData.putExtra("score", score);
        finish();
        startActivity(passData);
    }

//   method to add cars from array to hashmap
    void createCarHashMap() {
        String[] keys = (getResources().getStringArray(R.array.imgName));
        String[] values = (getResources().getStringArray(R.array.cars));


        for (int i = 0; i < keys.length; i++) {
            carList.put(keys[i], values[i]);
        }
    }

// override onStop to stop time wehen activity is closed
    @Override
    protected void onStop() {
        if (timerState) {
            gameTimer.cancel();
        }
        super.onStop();

    }
}