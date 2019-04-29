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
    long[] mVibratePattern = new long[]{0,30,10,30,10,30,10,30,1000,100,50,100,50,100,50,100};
    int[] mAmplitudes = new int[]{0, 10, 0, 255, 0, 20, 0, 255,0, 10, 0, 255, 0, 20, 0, 255};

 //   EditText text = (EditText)findViewById(R.id.editText2);
 //   String abc= text.getText().toString();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibrate);

        button = findViewById(R.id.button);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        button.setOnClickListener(new View.OnClickListener(){
          //  @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick (View view){
                if(Build.VERSION.SDK_INT >= 26) {
                    //vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
               //     vibrator.vibrate(mVibratePattern, -1);
         //           if (vibrator.hasAmplitudeControl()) {
                        VibrationEffect effect = VibrationEffect.createWaveform(mVibratePattern, mAmplitudes, -1);
                        vibrator.vibrate(effect);
         //           }

                } else {
                 //  vibrator.vibrate(200);
                   vibrator.vibrate(mVibratePattern, -1);
                }

            }
        });
    }
}
