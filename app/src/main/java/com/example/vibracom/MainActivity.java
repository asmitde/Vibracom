package com.example.vibracom;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        Switch toggle = findViewById(R.id.toggleButton);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    startCapture();
                } else {
                    stopCapture();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float[] readings = {x, y, z};
            updateReadings(readings);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void stopCapture() {
        sensorManager.unregisterListener(this);
    }

    private void startCapture() {
        sensorManager.registerListener(this, accel, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);

        float[] readings = {0, 0, 0};
        updateReadings(readings);
    }

    private void updateReadings(float[] readings) {
        TextView view_X = findViewById(R.id.x_data);
        TextView view_Y = findViewById(R.id.y_data);
        TextView view_Z = findViewById(R.id.z_data);

        view_X.setText(String.valueOf(readings[0]));
        view_Y.setText(String.valueOf(readings[1]));
        view_Z.setText(String.valueOf(readings[2]));
    }
}
