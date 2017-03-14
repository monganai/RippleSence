package com.example.brian.progproj;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.mvc.imagepicker.ImagePicker;

import java.util.ArrayList;

public class AddRippleActivity extends AppCompatActivity {

    Bitmap icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ripple);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImagePicker.setMinQuality(600, 600);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        icon = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
        ImageButton view = (ImageButton)findViewById(R.id.imageButton);
        view.setImageBitmap(icon);
    }

    public void onSelectImage(View view){
        ImagePicker.pickImage(this, "Select your image:");
    }
    public void onClick(View view){
        final AddRippleActivity activity = this;

        EditText textbox = (EditText)findViewById(R.id.editText);
        if(textbox.getText().length() == 0){
            Snackbar.make(findViewById(android.R.id.content), "Please Fill Out All Fields", Snackbar.LENGTH_LONG).setDuration(1800).show();
            return;
        }
        if(icon == null){
            Snackbar.make(findViewById(android.R.id.content), "Please select an image", Snackbar.LENGTH_LONG).setDuration(1800).show();
            return;
        }
        TinyDB db = new TinyDB(this.getApplicationContext());
        if(db.getString(textbox.getText().toString()) != null){
            Snackbar.make(findViewById(android.R.id.content), "A Ripple with this name already exists!", Snackbar.LENGTH_LONG).setDuration(1800).show();
            return;
        }
        ArrayList<String> farms = new ArrayList<>();
        if(db.getListString("Ripples") != null){
            farms = db.getListString("Ripples");
        }
        String farmName = textbox.getText().toString();
        farms.add(farmName);
        db.putListString("Ripples", farms);
        String path = db.putImage(this.getApplicationContext().getApplicationInfo().dataDir, farmName + ".png", icon);
        db.putString(farmName, path);
        setResult(RESULT_OK);
        finish();
    }

}
