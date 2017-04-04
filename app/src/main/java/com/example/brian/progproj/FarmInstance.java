package com.example.brian.progproj;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by brian on 16/02/2017.
 */

public class FarmInstance {
    String name;
    String image;

    public FarmInstance(String farmName, Bitmap icon){
        name = farmName;
        image = getStringFromBitmap(icon);
    }

    public FarmInstance(String farmName){
        name = farmName;
    }

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
