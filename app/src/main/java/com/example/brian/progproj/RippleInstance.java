package com.example.brian.progproj;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by brian on 16/02/2017.
 */

public class RippleInstance {
    String name;
    String macAddress;

    public RippleInstance(String rippleName, String mac){
        name = rippleName;
        macAddress = mac;
    }

    public String getName(){
        return name;
    }

    public void setName(String farm){
        name = farm;
    }

    public String getMacAddress(){
        return macAddress;
    }

    public void setMacAddress(String mac){
        macAddress = mac;
    }

}
