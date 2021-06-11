package com.example.cargusser;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Random;

public class identifyCar extends AppCompatActivity {

//    Declaring variables
    final Random random = new Random();
    private Spinner carListSpinner;
    HashMap<String, String> carList = new HashMap<>();
    public int counter = 20;
    ImageView carImage;
    String name;
    Button identifyBtn;
    TextView result;
    TextView displayAnswer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Resources res = getResources();

//        Initialing variables
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_car);
        carListSpinner = findViewById(R.id.carListSpinner);
        TextView timer = (TextView) findViewById(R.id.showTimer);


//      Getting data from main pages to check if timer switch is turned on
        Bundle data = getIntent().getExtras();
        boolean timerState = false;
        timerState = data.getBoolean("timer");

//        Checking if timer switch is turned on and starting timer
        if (timerState) {
            new CountDownTimer(20000, 1000) {
                public void onTick(long millisUntilFinished) {
                    timer.setText("TIME : "+String.valueOf(counter));
                    counter--;
                }

                @Override
                public void onFinish() {
                    timer.setText("Times up!");
                    buttonAction();
                }

            }.start();
        }


        createCarHashMap();
//        Getting random Car image from car array
        carImage = (ImageView) findViewById(R.id.randomCarHints);
        name = "car_" + random.nextInt(30);
//        displaying picture
        carImage.setImageDrawable(
                getResources().getDrawable(getResourceID(name, "drawable", getApplicationContext()))
        );

//        adding values to the dropdown list
        ArrayAdapter<String> aryAdapater = new ArrayAdapter(this, android.R.layout.simple_spinner_item, carList.values().toArray());
        aryAdapater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carListSpinner.setAdapter(aryAdapater);

//        initiating resources
        identifyBtn = findViewById(R.id.identifyBtn);
        result = findViewById(R.id.resultBox);
        displayAnswer = findViewById(R.id.displayAns);
        identifyBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                buttonAction();

            }
        });


    }


//    function for submit button
    private void buttonAction() {
        if (carList.get(name).equals(carListSpinner.getSelectedItem().toString())) {
            result.setText("CORRECT!");
            result.setTextColor(Color.parseColor("#FF42930E"));
            carImage.setImageAlpha(50);

        } else {
            result.setText("WRONG");
            result.setTextColor(Color.RED);
            carImage.setImageAlpha(50);
            displayAnswer.setText(carList.get(name));
        }
        identifyBtn.setText("Next");
        identifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });
    }

//    Method the get resource ID for images
    protected final static int getResourceID
            (final String imageName, final String imageType, final Context conTxt) {
        final int ResourceID =
                conTxt.getResources().getIdentifier(imageName, imageType,
                        conTxt.getApplicationInfo().packageName);
        if (ResourceID == 0) {
            throw new IllegalArgumentException
                    (
                            "No resource string found with name " + imageName
                    );
        } else {
            return ResourceID;
        }

    }


//    imploring car arrays to hash map
    void createCarHashMap() {

        String[] keys = (getResources().getStringArray(R.array.imgName));
        String[] values = (getResources().getStringArray(R.array.cars));


        for (int i = 0; i < keys.length; i++) {
            carList.put(keys[i], values[i]);
        }
    }


}


