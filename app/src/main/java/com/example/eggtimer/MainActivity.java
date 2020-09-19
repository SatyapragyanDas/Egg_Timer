package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar timerSeekBar;
    TextView textView;
    Button button;
    CountDownTimer countDownTimer;
    boolean counterIsActive=false;
    public void resetTimer() {

        textView.setText("0:30");

        timerSeekBar.setProgress(30);

        timerSeekBar.setEnabled(true);

        countDownTimer.cancel();

        button.setText("GO!");

        counterIsActive = false;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerSeekBar=findViewById(R.id.seekBar);
        textView=findViewById(R.id.textView);
        button=findViewById(R.id.button);
        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);
        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    public void updateTimer(int secondsLeft) {

        int minutes = secondsLeft / 60;

        int seconds = secondsLeft - (minutes * 60);


        String secondString = Integer.toString(seconds);


        if (seconds <= 9) {

            secondString = "0" + secondString;

        }


        textView.setText(Integer.toString(minutes) + ":" + secondString);

    }
    public void invokeTimer(View view) {
        if(counterIsActive){
            resetTimer();
        }
        else{
            counterIsActive=true;
            timerSeekBar.setEnabled(false);
            button.setText("STOP!");
            countDownTimer=new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished/1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.doorbell);
                    mediaPlayer.start();
                    resetTimer();
                }
            }.start();
        }
    }
}
