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

import com.google.android.things.pio.I2cDevice;
import com.google.android.things.pio.PeripheralManagerService;

import java.io.IOException;

/**
 * Created by cagdas on 20.12.2016.
 */

public class ADXL345 implements AutoCloseable {
    public static final int ADXL345_DEFAULT_ADDRESS = 0x53;
    public static final int ADXL345_RA_DEVID = 0x00;
    public static final int ADXL345_RA_RESERVED1 = 0x01;
    public static final int ADXL345_RA_THRESH_TAP = 0x1D;
    public static final int ADXL345_RA_OFSX = 0x1E;
    public static final int ADXL345_RA_OFSY = 0x1F;
    public static final int ADXL345_RA_OFSZ = 0x20;
    public static final int ADXL345_RA_DUR = 0x21;
    public static final int ADXL345_RA_LATENT = 0x22;
    public static final int ADXL345_RA_WINDOW = 0x23;
    public static final int ADXL345_RA_THRESH_ACT = 0x24;
    public static final int ADXL345_RA_THRESH_INACT = 0x25;
    public static final int ADXL345_RA_TIME_INACT = 0x26;
    public static final int ADXL345_RA_ACT_INACT_CTL = 0x27;
    public static final int ADXL345_RA_THRESH_FF = 0x28;
    public static final int ADXL345_RA_TIME_FF = 0x29;
    public static final int ADXL345_RA_TAP_AXES = 0x2A;
    public static final int ADXL345_RA_ACT_TAP_STATUS = 0x2B;
    public static final int ADXL345_RA_BW_RATE = 0x2C;
    public static final int ADXL345_RA_POWER_CTL = 0x2D;
    public static final int ADXL345_RA_INT_ENABLE = 0x2E;
    public static final int ADXL345_RA_INT_MAP = 0x2F;
    public static final int ADXL345_RA_INT_SOURCE = 0x30;
    public static final int ADXL345_RA_DATA_FORMAT = 0x31;
    public static final int ADXL345_RA_DATAX0 = 0x32;
    public static final int ADXL345_RA_DATAX1 = 0x33;
    public static final int ADXL345_RA_DATAY0 = 0x34;
    public static final int ADXL345_RA_DATAY1 = 0x35;
    public static final int ADXL345_RA_DATAZ0 = 0x36;
    public static final int ADXL345_RA_DATAZ1 = 0x37;
    public static final int ADXL345_RA_FIFO_CTL = 0x38;
    public static final int ADXL345_RA_FIFO_STATUS = 0x39;

    public static final int ADXL345_AIC_ACT_AC_BIT = 7;
    public static final int ADXL345_AIC_ACT_X_BIT = 6;
    public static final int ADXL345_AIC_ACT_Y_BIT = 5;
    public static final int ADXL345_AIC_ACT_Z_BIT = 4;
    public static final int ADXL345_AIC_INACT_AC_BIT = 3;
    public static final int ADXL345_AIC_INACT_X_BIT = 2;
    public static final int ADXL345_AIC_INACT_Y_BIT = 1;
    public static final int ADXL345_AIC_INACT_Z_BIT = 0;

    public static final int ADXL345_TAPAXIS_SUP_BIT = 3;
    public static final int ADXL345_TAPAXIS_X_BIT = 2;
    public static final int ADXL345_TAPAXIS_Y_BIT = 1;
    public static final int ADXL345_TAPAXIS_Z_BIT = 0;

    public static final int ADXL345_TAPSTAT_ACTX_BIT = 6;
    public static final int ADXL345_TAPSTAT_ACTY_BIT = 5;
    public static final int ADXL345_TAPSTAT_ACTZ_BIT = 4;
    public static final int ADXL345_TAPSTAT_ASLEEP_BIT = 3;
    public static final int ADXL345_TAPSTAT_TAPX_BIT = 2;
    public static final int ADXL345_TAPSTAT_TAPY_BIT = 1;
    public static final int ADXL345_TAPSTAT_TAPZ_BIT = 0;

    public static final int ADXL345_BW_LOWPOWER_BIT = 0;
    public static final int ADXL345_BW_RATE_BIT = 3;
    public static final int ADXL345_BW_RATE_LENGTH = 4;

    public static final int ADXL345_RATE_3200 = 0b1111;
    public static final int ADXL345_RATE_1600 = 0b1110;
    public static final int ADXL345_RATE_800 = 0b1101;
    public static final int ADXL345_RATE_400 = 0b1100;
    public static final int ADXL345_RATE_200 = 0b1011;
    public static final int ADXL345_RATE_100 = 0b1010;
    public static final int ADXL345_RATE_50 = 0b1001;
    public static final int ADXL345_RATE_25 = 0b1000;
    public static final int ADXL345_RATE_12P5 = 0b0111;
    public static final int ADXL345_RATE_6P25 = 0b0110;
    public static final int ADXL345_RATE_3P13 = 0b0101;
    public static final int ADXL345_RATE_1P56 = 0b0100;
    public static final int ADXL345_RATE_0P78 = 0b0011;
    public static final int ADXL345_RATE_0P39 = 0b0010;
    public static final int ADXL345_RATE_0P20 = 0b0001;
    public static final int ADXL345_RATE_0P10 = 0b0000;

    public static final int ADXL345_PCTL_LINK_BIT = 5;
    public static final int ADXL345_PCTL_AUTOSLEEP_BIT = 4;
    public static final int ADXL345_PCTL_MEASURE_BIT = 3;
    public static final int ADXL345_PCTL_SLEEP_BIT = 2;
    public static final int ADXL345_PCTL_WAKEUP_BIT = 0;
    public static final int ADXL345_PCTL_WAKEUP_LENGTH = 2;

    public static final int ADXL345_WAKEUP_8HZ = 0b00;
    public static final int ADXL345_WAKEUP_4HZ = 0b01;
    public static final int ADXL345_WAKEUP_2HZ = 0b10;
    public static final int ADXL345_WAKEUP_1HZ = 0b11;

    public static final int ADXL345_INT_DATA_READY_BIT = 7;
    public static final int ADXL345_INT_SINGLE_TAP_BIT = 6;
    public static final int ADXL345_INT_DOUBLE_TAP_BIT = 5;
    public static final int ADXL345_INT_ACTIVITY_BIT = 4;
    public static final int ADXL345_INT_INACTIVITY_BIT = 3;
    public static final int ADXL345_INT_FREE_FALL_BIT = 2;
    public static final int ADXL345_INT_WATERMARK_BIT = 1;
    public static final int ADXL345_INT_OVERRUN_BIT = 0;

    public static final int ADXL345_FORMAT_SELFTEST_BIT = 7;
    public static final int ADXL345_FORMAT_SPIMODE_BIT = 6;
    public static final int ADXL345_FORMAT_INTMODE_BIT = 5;
    public static final int ADXL345_FORMAT_FULL_RES_BIT = 3;
    public static final int ADXL345_FORMAT_JUSTIFY_BIT = 2;
    public static final int ADXL345_FORMAT_RANGE_BIT = 0;
    public static final int ADXL345_FORMAT_RANGE_LENGTH = 2;

    public static final int ADXL345_RANGE_2G = 0b00;
    public static final int ADXL345_RANGE_4G = 0b01;
    public static final int ADXL345_RANGE_8G = 0b10;
    public static final int ADXL345_RANGE_16G = 0b11;

    public static final int ADXL345_FIFO_MODE_BIT = 6;
    public static final int ADXL345_FIFO_MODE_LENGTH = 2;
    public static final int ADXL345_FIFO_TRIGGER_BIT = 5;
    public static final int ADXL345_FIFO_SAMPLES_BIT = 0;
    public static final int ADXL345_FIFO_SAMPLES_LENGTH = 5;

    public static final int ADXL345_FIFO_MODE_BYPASS = 0b00;
    public static final int ADXL345_FIFO_MODE_FIFO = 0b01;
    public static final int ADXL345_FIFO_MODE_STREAM = 0b10;
    public static final int ADXL345_FIFO_MODE_TRIGGER = 0b11;

    public static final int ADXL345_FIFOSTAT_TRIGGER_BIT = 7;
    public static final int ADXL345_FIFOSTAT_LENGTH_BIT = 5;
    public static final int ADXL345_FIFOSTAT_BIT_START = 6;

    private I2cDevice mDevice;

    public ADXL345(String bus) throws IOException {
        PeripheralManagerService peripheralManagerService = new PeripheralManagerService();
        I2cDevice device = peripheralManagerService.openI2cDevice(bus, ADXL345_DEFAULT_ADDRESS);
        try {
            connect(device);
        } catch (IOException | RuntimeException e) {
            try {
                close();
            } catch (IOException | RuntimeException ignored) {
            }
            throw e;
        }
    }

    @Override
    public void close() throws IOException {
        if (this.mDevice != null) {
            this.mDevice.close();
            this.mDevice = null;
        }
    }

    private void connect(I2cDevice device) throws IOException {
        this.mDevice = device;
        this.mDevice.writeRegByte(ADXL345_RA_POWER_CTL, (byte) 0);
        setAutoSleepEnabled(true);
        setMeasureEnabled(true);

    }

    public void setAutoSleepEnabled(boolean enabled) throws IOException {
        int value = this.mDevice.readRegByte(ADXL345_RA_POWER_CTL) & 0xff;
        if (enabled) {
            value |= (1 << ADXL345_PCTL_AUTOSLEEP_BIT);
        } else {
            value &= ~(1 << ADXL345_PCTL_AUTOSLEEP_BIT);
        }
        this.mDevice.writeRegByte(ADXL345_RA_POWER_CTL, (byte) value);
    }

    public void setMeasureEnabled(boolean enabled) throws IOException {
        int value = this.mDevice.readRegByte(ADXL345_RA_POWER_CTL) & 0xff;
        if (enabled) {
            value |= (1 << ADXL345_PCTL_MEASURE_BIT);
        } else {
            value &= ~(1 << ADXL345_PCTL_MEASURE_BIT);
        }
        this.mDevice.writeRegByte(ADXL345_RA_POWER_CTL, (byte) value);
    }

    public int getDeviceId() throws IOException {
        return this.mDevice.readRegByte(ADXL345_RA_DEVID) & 0xff;
    }

    public int getAccelerationX() throws IOException {
        return this.mDevice.readRegWord(ADXL345_RA_DATAX0);
    }

    public int getAccelerationY() throws IOException {
        return this.mDevice.readRegWord(ADXL345_RA_DATAY0);
    }

    public int getAccelerationZ() throws IOException {
        return this.mDevice.readRegWord(ADXL345_RA_DATAZ0);
    }

    public float[] getAccelerations() throws IOException {
        float[] values = new float[3];
        values[0] = getAccelerationX();
        values[1] = getAccelerationY();
        values[2] = getAccelerationZ();
        return values;
    }

    public void setRate(int rate) throws IOException {
        int value = this.mDevice.readRegByte(ADXL345_RA_BW_RATE) & 0xff;
        int bits = 1;
        int i = 0;
        while (i < ADXL345_BW_RATE_LENGTH - 1) {
            bits = (bits << 1);
            ++bits;
            ++i;
        }

        value &= ~(bits << ADXL345_BW_LOWPOWER_BIT);
        value |= (rate << ADXL345_BW_LOWPOWER_BIT);
        this.mDevice.writeRegByte(ADXL345_RA_BW_RATE, (byte) value);
    }

    public int getRate() throws IOException {
        int value = this.mDevice.readRegByte(ADXL345_RA_BW_RATE) & 0xff;
        return (int) ((value >> ADXL345_BW_LOWPOWER_BIT) % Math.pow(2, ADXL345_BW_RATE_LENGTH));
    }
}

