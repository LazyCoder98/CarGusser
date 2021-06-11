package com.example.cargusser;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Random;

import static com.example.cargusser.identifyCar.getResourceID;

public class Hints extends AppCompatActivity {

    //    Declaring Variables
    final Random random = new Random();
    String nameToBeGuessedConverted;
    String nameToBeGuessedDisplay;
    TextView guessWord;
    TextView triesDisplay;
    TextView displayTimer;
    TextView displayAns;
    String displayWord;
    char[] wordDisplayedArray;
    EditText userInput;
    String guessesTried;
    String guessesLeft;
    boolean timerState = false;

    CountDownTimer gameTimer;
    public int counter = 20;
    Button submitBtn;
    HashMap<String, String> carList = new HashMap<>();



//    Function to inlitation variable set up main activit display
    void startGame() {
        createCarHashMap();


//        Selecting random image and displaying
        final ImageView carImage = (ImageView) findViewById(R.id.randomCarHints);
        final String name = "car_" + random.nextInt(29);
        carImage.setImageDrawable(
                getResources().getDrawable(getResourceID(name, "drawable", getApplicationContext()))
        );

//        setting car name to be guees
        nameToBeGuessedDisplay = carList.get(name);
        nameToBeGuessedConverted = nameToBeGuessedDisplay.toLowerCase();


        wordDisplayedArray = nameToBeGuessedDisplay.toCharArray();

        for (int i = 0; i < wordDisplayedArray.length; i++) {
            wordDisplayedArray[i] = '_';
        }


        displayGuess();

        userInput.setText("");

        guessesTried = " ";

        guessesLeft = " X X X";

        triesDisplay.setText(guessesLeft);

    }

//    function to check if the user ented letter is in the car name to be guessed
    void checkLetterInWord(char letter) {
        char convertedLetter = Character.toLowerCase(letter);
        String te = Character.toString(convertedLetter);
        Log.d("name", nameToBeGuessedConverted);
        Log.d("t", te);
        if (nameToBeGuessedConverted.indexOf(convertedLetter) >= 0) {
            if (displayWord.indexOf(convertedLetter) < 0) {
                showGuessedLetter(convertedLetter);

                displayGuess();

                if (!displayWord.contains("_")) {
                    displayAns.setText("CORRECT!!");
                    displayAns.setTextColor(Color.RED);
                    displayAns.setTextColor(Color.parseColor("#FF42930E"));
                    userInput.setEnabled(false);
                    submitBtn.setText("Next");
                    submitBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                            startActivity(getIntent());
                        }
                    });

                }
            }
        } else {
            reduceTriesLeft();
            if (guessesLeft.isEmpty()) {
                displayAns.setText("WRONG!");
                displayAns.setTextColor(Color.RED);
                guessWord.setText(nameToBeGuessedDisplay);
                userInput.setEnabled(false);
                submitBtn.setText("Next");
                submitBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        startActivity(getIntent());
                    }
                });


            }
        }
    }


//function to reduce the number of tries if the user enters wrong guess
    void reduceTriesLeft() {
        if (!guessesLeft.isEmpty()) {
            guessesLeft = guessesLeft.substring(0, guessesLeft.length() - 2);
            Log.d("message", guessesLeft);
            triesDisplay.setText(guessesLeft);
        } else {

            guessWord.setText(nameToBeGuessedDisplay);

            submitBtn.setText("Next");

            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    startActivity(getIntent());
                }
            });


        }
    }

//function to display the  guess
    void displayGuess() {
        String editedString = "";
        for (char character : wordDisplayedArray) {
            editedString += character + " ";
        }
        guessWord.setText(editedString);
    }

//    function to change the used guessed letter
    void showGuessedLetter(char letter) {
        int letterIndex = nameToBeGuessedConverted.indexOf(letter);

        while (letterIndex >= 0) {
            wordDisplayedArray[letterIndex] = nameToBeGuessedConverted.charAt(letterIndex);
            letterIndex = nameToBeGuessedConverted.indexOf(letter, letterIndex + 1);
        }

        displayWord = String.valueOf(wordDisplayedArray);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hints);

        guessWord = findViewById(R.id.guessDisplay);
        triesDisplay = findViewById(R.id.triesDisplay);
        userInput = findViewById(R.id.guessInput);
        submitBtn = (Button) findViewById(R.id.submitBtn);
        displayTimer = findViewById(R.id.displayTimer);
        displayAns = findViewById(R.id.ansView);

        Bundle data = getIntent().getExtras();

        timerState = data.getBoolean("timer");
        if (timerState == true) {
            timerFunction();

        }

        startGame();
        userInput = findViewById(R.id.guessInput);
        displayWord = String.valueOf(wordDisplayedArray);

        submitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String inputString = userInput.getText().toString();
//                Log.d("input", inputString);
                if (inputString.isEmpty()) {
                    inputString = " ";
                }

                checkLetterInWord(inputString.charAt(0));
            }
        });


    }

//    function to start timer function

    private void timerFunction() {
        gameTimer = new CountDownTimer(20000, 1000) {
            public void onTick(long millisUntilFinished) {
                displayTimer.setText("TIME : " + String.valueOf(counter));
                counter--;
            }

            @Override
            public void onFinish() {
                String inputString = userInput.getText().toString();
                if (inputString.isEmpty()) {
                    inputString = " ";
                }
                checkLetterInWord(inputString.charAt(0));
                if (guessesLeft.isEmpty()) {
                    displayTimer.setText("Times UP!");
                    submitBtn.setText("Next");
                    guessWord.setText(nameToBeGuessedConverted);
                    submitBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                            startActivity(getIntent());
                        }
                    });
                } else {
                    counter = 20;
                    timerFunction();
                }


            }

        }.start();
    }


//    Method to add arras to hashmap
    void createCarHashMap() {

        String[] keys = (getResources().getStringArray(R.array.imgName));
        String[] values = (getResources().getStringArray(R.array.cars));


        for (int i = 0; i < keys.length; i++) {
            carList.put(keys[i], values[i]);
        }
    }


//    overriding onStop method to stop timer when the activity is closed
    @Override
    protected void onStop() {
        if (timerState) {
            gameTimer.cancel();
        }
        super.onStop();

    }


}