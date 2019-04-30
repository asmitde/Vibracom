package com.example.vibracom;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accel;

    File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
    File log = new File(folder, "Vibracom_log.csv");

    FileOutputStream fileOutputStream = null;
    String logstr = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER_UNCALIBRATED);


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

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER_UNCALIBRATED) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long time = event.timestamp;

            float[] readings = {time, x, y, z};
            updateReadings(readings);

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void stopCapture() {
        sensorManager.unregisterListener(this);

        float[] readings = {0, 0, 0};
        updateReadings(readings);

        writeDataToFile(log, logstr);
    }

    private void writeDataToFile(File log, String logstr) {
        try {
            fileOutputStream = new FileOutputStream(log);
            fileOutputStream.write(logstr.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void startCapture() {
        sensorManager.registerListener(this, accel, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);

        logstr = "";

    }

    private void updateReadings(float[] readings) {
        TextView view_X = findViewById(R.id.x_data);
        TextView view_Y = findViewById(R.id.y_data);
        TextView view_Z = findViewById(R.id.z_data);

        view_X.setText(String.valueOf(readings[0]));
        view_Y.setText(String.valueOf(readings[1]));
        view_Z.setText(String.valueOf(readings[2]));


        String logdata = String.format("%ld,%f,%f,%f\n",readings[0], readings[1], readings[2], readings[3]);
        logstr += logdata;
    }
}
