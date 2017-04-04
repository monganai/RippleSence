package com.example.brian.progproj;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
/**
 * Created by brian on 01/02/2017.
 */

public class RippleApplication extends Application{
    public BluetoothDevice device;
    public BluetoothAdapter adapter;
    @Override
    public void onCreate(){
        super.onCreate();
    }
}
