/*
 * Copyright 2016 Cagdas Caglak
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cacaosd.sample;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import com.cacaosd.adxl345.ADXL345SensorDriver;

import java.io.IOException;

public class AccelerometerActivity extends Activity implements SensorEventListener {

    private static final String TAG = AccelerometerActivity.class.getSimpleName();

    private ADXL345SensorDriver mSensorDriver;
    private SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerDynamicSensorCallback(new SensorManager.DynamicSensorCallback() {
            @Override
            public void onDynamicSensorConnected(Sensor sensor) {
                if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    mSensorManager.registerListener(AccelerometerActivity.this,
                            sensor, SensorManager.SENSOR_DELAY_NORMAL);
                }
            }
        });

        try {
            mSensorDriver = new ADXL345SensorDriver(BoardDefaults.getI2CPort());
            mSensorDriver.registerAccelerometerSensor();
        } catch (IOException e) {
            Log.e(TAG, "Error configuring sensor", e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Closing sensor");
        if (mSensorDriver != null) {
            mSensorManager.unregisterListener(this);
            try {
                mSensorDriver.close();
            } catch (IOException e) {
                Log.e(TAG, "Error closing sensor", e);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mSensorDriver = null;
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d(TAG, "Accel X " + event.values[0] + " Y " + event.values[1] + " Z " + event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.i(TAG, "sensor accuracy changed: " + accuracy);
    }
}
