package com.example.brian.progproj;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by brian on 16/02/2017.
 */

public class FarmInstance {
    String name;
    Bitmap image;
    ArrayList<RippleInstance> ripples; //Replace with RippleInstance
    public FarmInstance(String farmName, Bitmap icon){
        name = farmName;
        image = icon;
        ripples = new ArrayList<>();
    }

    public FarmInstance(String farmName){
        name = farmName;
        ripples = new ArrayList<>();
    }

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

    public void addRipple(RippleInstance ripple){
        ripples.add(ripple);
    }
}
