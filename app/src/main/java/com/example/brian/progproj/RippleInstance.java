package com.example.brian.progproj;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by brian on 16/02/2017.
 */

public class RippleInstance {
    String name;
    String image;
    int RippleHeight;
    int Depth;
    int Range;
    int waterLevel = 0;
    int temp = 0;
    String address = "98:D3:31:FB:1F:75";

    public RippleInstance(String farmName, Bitmap icon){
        name = farmName;
        image = getStringFromBitmap(icon);
    }

    public RippleInstance(){

    }

    public String getAddress() {return address;}
    public Bitmap getImage(){
        return getBitmapFromString(image);
    }

    public void setImage(Bitmap icon){
        image = getStringFromBitmap(icon);
    }

    public String getName(){
        return name;
    }

    public void setName(String farm){
        name = farm;
    }

    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(this, RippleInstance.class);
    }
    private String getStringFromBitmap(Bitmap bitmapPicture) {
 /*
 * This functions converts Bitmap picture to a string which can be
 * JSONified.
 * */
        final int COMPRESSION_QUALITY = 100;
        String encodedImage;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
                byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    }

    private Bitmap getBitmapFromString(String jsonString) {
/*
* This Function converts the String back to Bitmap
* */
        byte[] decodedString = Base64.decode(jsonString, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
}
