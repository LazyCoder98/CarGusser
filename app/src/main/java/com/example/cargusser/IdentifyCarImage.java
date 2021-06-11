package com.example.cargusser;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static com.example.cargusser.identifyCar.getResourceID;

public class IdentifyCarImage extends AppCompatActivity {

//    declaring variables
    final Random random = new Random();
    TextView showCarName;
    ImageView displayCarImage1;
    ImageView displayCarImage2;
    ImageView displayCarImage3;
    Button nextBtn;
    TextView carIdTimer;

    public int counter = 20;
    String carImage0;
    String carImage1;
    String carImage2;
    String guessName;
    HashMap<String, String> carList = new HashMap<>();
    ArrayList<String> randomizeCarPick = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_car_image);

//        accruing passed data for timer state
        Bundle data = getIntent().getExtras();
        boolean timerState = false;
        timerState = data.getBoolean("timer");

//        checkecking if timer switch is true to turn on timer
        carIdTimer = findViewById(R.id.carIdTimer);
        if (timerState == true) {
            new CountDownTimer(20000, 1000) {
                public void onTick(long millisUntilFinished) {
                    carIdTimer.setText("TIME : " + String.valueOf(counter));
                    counter--;
                }

                @Override
                public void onFinish() {
                    carIdTimer.setText("Times up!");
                    showCarName.setText("WRONG!");
                    showCarName.setTextColor(Color.RED);
                    displayCarImage1.setImageAlpha(50);
                    displayCarImage2.setImageAlpha(50);
                    displayCarImage3.setImageAlpha(50);
                }

            }.start();
        }

        createCarHashMap();


//        getting there random car images
        carImage0 = "car_" + random.nextInt(29);
        carImage1 = "car_" + random.nextInt(29);
        carImage2 = "car_" + random.nextInt(29);

//        preventing the same image being picked twice
        while (carImage0.equals(carImage1)) {
            carImage1 = "car_" + random.nextInt(29);
        }

        while (carImage2.equals(carImage0)) {
            carImage2 = "car_" + random.nextInt(29);
        }

        while (carImage2.equals(carImage1)) {
            carImage2 = "car_" + random.nextInt(29);
        }


        displayCarImage1 = (ImageView) findViewById(R.id.randomCar0);
        displayCarImage2 = (ImageView) findViewById(R.id.randomCar1);
        displayCarImage3 = (ImageView) findViewById(R.id.randomCar2);

//        displaying car images

        displayCarImage1.setImageDrawable(
                getResources().getDrawable(getResourceID(carImage0, "drawable", getApplicationContext()))
        );

        displayCarImage2.setImageDrawable(
                getResources().getDrawable(getResourceID(carImage1, "drawable", getApplicationContext()))
        );

        displayCarImage3.setImageDrawable(
                getResources().getDrawable(getResourceID(carImage2, "drawable", getApplicationContext()))
        );


        randomizeCarPick.add(carImage0);
        randomizeCarPick.add(carImage1);
        randomizeCarPick.add(carImage2);

//        picking one random car name from the 3 choosen cars
        guessName = randomizeCarPick.get(random.nextInt(2));

        showCarName = findViewById(R.id.carNameDisplay);

        showCarName.setText(carList.get(guessName));


        nextBtn = (Button) findViewById(R.id.nextBtn);



        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });

        displayCarImage1.setOnClickListener(new View.OnClickListener(

        ) {

            @Override
            public void onClick(View v) {
                checkAnswer(carImage0);
            }
        });


        displayCarImage2.setOnClickListener(new View.OnClickListener(

        ) {

            @Override
            public void onClick(View v) {
                checkAnswer(carImage1);
            }
        });

        displayCarImage3.setOnClickListener(new View.OnClickListener(

        ) {

            @Override
            public void onClick(View v) {
                checkAnswer(carImage2);
            }
        });

    }

//    functionality for checking if the image clicked is correct
    public void checkAnswer(String imgName) {

        if (imgName.equals(guessName)) {
            showCarName.setText("CORRECT!");
            showCarName.setTextColor(Color.parseColor("#FF42930E"));
            displayCarImage1.setImageAlpha(50);
            displayCarImage2.setImageAlpha(50);
            displayCarImage3.setImageAlpha(50);

        } else {
            showCarName.setText("WRONG!");
            showCarName.setTextColor(Color.RED);
            displayCarImage1.setImageAlpha(50);
            displayCarImage2.setImageAlpha(50);
            displayCarImage3.setImageAlpha(50);


        }

    }

//    adding cars from arrays to hashmap
    void createCarHashMap() {

        String[] keys = (getResources().getStringArray(R.array.imgName));
        String[] values = (getResources().getStringArray(R.array.cars));


        for (int i = 0; i < keys.length; i++) {
            carList.put(keys[i], values[i]);
        }
    }


}


