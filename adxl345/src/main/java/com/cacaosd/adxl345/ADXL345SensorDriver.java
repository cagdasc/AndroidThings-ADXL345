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

package com.cacaosd.adxl345;

import android.hardware.Sensor;

import com.google.android.things.userdriver.UserDriverManager;
import com.google.android.things.userdriver.UserSensor;
import com.google.android.things.userdriver.UserSensorDriver;
import com.google.android.things.userdriver.UserSensorReading;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by cagdas on 20.12.2016.
 */

public class ADXL345SensorDriver implements AutoCloseable {

    private static final String DRIVER_VENDOR = "";
    private static final String DRIVER_NAME = "ADXL345";
    private static final int DRIVER_VERSION = 1;
    private static final int DRIVER_RESOLUTION = 10; //bits
    private static final float DRIVER_POWER = 2.5f; // Volt
    private static final int DRIVER_MAX_DELAY_US = Math.round(1000000.f / 0.1f);
    private static final int DRIVER_MIN_DELAY_US = Math.round(1000000.f / 3200.0f);
    private static final String DRIVER_REQUIRED_PERMISSION = "";

    private static final String TAG = ADXL345SensorDriver.class.getName();

    private ADXL345 adxl345;
    private AccelerometerUserDriver mUserDriver;

    public ADXL345SensorDriver(String bus) throws IOException {
        adxl345 = new ADXL345(bus);
    }

    public void registerAccelerometerSensor() {
        if (adxl345 == null) {
            throw new IllegalStateException("cannot register closed driver");
        }

        if (mUserDriver == null) {
            mUserDriver = new AccelerometerUserDriver();
            UserDriverManager.getManager().registerSensor(mUserDriver.getUserSensor());
        }
    }

    public void unregisterAccelerometerSensor() {
        if (mUserDriver != null) {
            UserDriverManager.getManager().unregisterSensor(mUserDriver.getUserSensor());
            mUserDriver = null;
        }
    }

    @Override
    public void close() throws Exception {
        unregisterAccelerometerSensor();
        if (adxl345 != null) {
            try {
                adxl345.close();
            } finally {
                adxl345 = null;
            }
        }
    }

    private class AccelerometerUserDriver extends UserSensorDriver {

        private UserSensor mUserSensor;

        private UserSensor getUserSensor() {
            if (mUserSensor == null) {
                mUserSensor = new UserSensor.Builder()
                        .setType(Sensor.TYPE_ACCELEROMETER)
                        .setName(DRIVER_NAME)
                        .setVendor(DRIVER_VENDOR)
                        .setVersion(DRIVER_VERSION)
                        .setResolution(DRIVER_RESOLUTION)
                        .setPower(DRIVER_POWER)
                        .setMinDelay(DRIVER_MIN_DELAY_US)
                        .setRequiredPermission(DRIVER_REQUIRED_PERMISSION)
                        .setMaxDelay(DRIVER_MAX_DELAY_US)
                        .setUuid(UUID.randomUUID())
                        .setDriver(this)
                        .build();
            }
            return mUserSensor;
        }

        @Override
        public UserSensorReading read() throws IOException {
            return new UserSensorReading(adxl345.getAccelerations());
        }
    }
}
