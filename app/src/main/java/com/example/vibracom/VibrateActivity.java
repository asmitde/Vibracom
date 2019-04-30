package com.example.vibracom;

import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class VibrateActivity extends AppCompatActivity {

    Button button;
    Vibrator vibrator;
    long[] mVibratePattern1 = new long[]{0,90,10,90,10,90,10,90,10,90,10,90,10,90,10,90};
    long[] mVibratePattern2 = new long[]{0,80,20,80,20,80,20,80,20,80,20,80,20,80,20,80};
    long[] mVibratePattern3 = new long[]{0,70,30,70,30,70,30,70,30,70,30,70,30,70,30,70};
    long[] mVibratePattern4 = new long[]{0,60,40,60,40,60,40,60,40,60,40,60,40,60,40,60};
    long[] mVibratePattern5 = new long[]{0,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50};
    long[] mVibratePattern6 = new long[]{0,40,60,40,60,40,60,40,60,40,60,40,60,40,60,40};
    long[] mVibratePattern7 = new long[]{0,30,70,30,70,30,70,30,70,30,70,30,70,30,70,30};
    long[] mVibratePattern8 = new long[]{0,20,80,20,80,20,80,20,80,20,80,20,80,20,80,20};
    long[] mVibratePattern9 = new long[]{0,10,90,10,90,10,90,10,90,10,90,10,90,10,90,10};

    int[] mAmplitudes = new int[]{0, 255, 0, 255, 0, 255, 0, 255,0, 255, 0, 255, 0, 255, 0, 255};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibrate);

        button = findViewById(R.id.button);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        button.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick (View view){

                    EditText text = (EditText)findViewById(R.id.editText);
                    int pass = Integer.parseInt(text.getText().toString());


                if (pass == 1){
                    VibrationEffect effect = VibrationEffect.createWaveform(mVibratePattern1, mAmplitudes, -1);
                    vibrator.vibrate(effect);}
                else if (pass == 2){
                    VibrationEffect effect = VibrationEffect.createWaveform(mVibratePattern2, mAmplitudes, -1);
                    vibrator.vibrate(effect);}
                else if (pass == 3){
                    VibrationEffect effect = VibrationEffect.createWaveform(mVibratePattern3, mAmplitudes, -1);
                    vibrator.vibrate(effect);}
                else if (pass == 4){
                    VibrationEffect effect = VibrationEffect.createWaveform(mVibratePattern4, mAmplitudes, -1);
                    vibrator.vibrate(effect);}
                else if (pass == 5){
                    VibrationEffect effect = VibrationEffect.createWaveform(mVibratePattern5, mAmplitudes, -1);
                    vibrator.vibrate(effect);}
                else if (pass == 6){
                    VibrationEffect effect = VibrationEffect.createWaveform(mVibratePattern6, mAmplitudes, -1);
                    vibrator.vibrate(effect);}
                else if (pass == 7){
                    VibrationEffect effect = VibrationEffect.createWaveform(mVibratePattern7, mAmplitudes, -1);
                    vibrator.vibrate(effect);}
                else if (pass == 8){
                    VibrationEffect effect = VibrationEffect.createWaveform(mVibratePattern8, mAmplitudes, -1);
                    vibrator.vibrate(effect);}
                else {
                    VibrationEffect effect = VibrationEffect.createWaveform(mVibratePattern9, mAmplitudes, -1);
                    vibrator.vibrate(effect);
           }
           }
        });
    }
}


//vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
//     vibrator.vibrate(mVibratePattern, -1);