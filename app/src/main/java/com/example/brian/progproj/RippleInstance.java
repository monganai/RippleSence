package com.example.brian.progproj;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by brian on 16/02/2017.
 */

public class RippleInstance {
    String name;
    Bitmap image;
    String address = "98:D3:31:FB:1F:75";

    public RippleInstance(String farmName, Bitmap icon){
        name = farmName;
        image = icon;
    }

    public RippleInstance(String farmName){
        name = farmName;
    }

    public String getAddress() {return address;}
    public Bitmap getImage(){
        return image;
    }

    public void setImage(Bitmap icon){
        image = icon;
    }

    public String getName(){
        return name;
    }

    public void setName(String farm){
        name = farm;
    }
}
