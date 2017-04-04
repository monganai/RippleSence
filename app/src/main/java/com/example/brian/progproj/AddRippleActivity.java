package com.example.brian.progproj;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.mvc.imagepicker.ImagePicker;

import java.util.ArrayList;
import java.util.Random;

public class AddRippleActivity extends AppCompatActivity {

    Bitmap icon;
    String parentFarm = "";

    EditText height;
    EditText depth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        if(b != null)
            parentFarm = b.getString("farm");
        setContentView(R.layout.activity_add_ripple);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
         height = (EditText) findViewById(R.id.Heightb) ;
         depth = (EditText) findViewById(R.id.depthb) ;

        setSupportActionBar(toolbar);
        ImagePicker.setMinQuality(600, 600);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        icon = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
        CircularImageView view = (CircularImageView) findViewById(R.id.imageButton);
        if(icon != null)
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
        /**if(db.getString(textbox.getText().toString()) != null){
            Snackbar.make(findViewById(android.R.id.content), "A Ripple with this name already exists!", Snackbar.LENGTH_LONG).setDuration(1800).show();
            return;
        }**/
        ArrayList<String> farms = new ArrayList<>();
        if(db.getListString(parentFarm + "_ripple") != null){
            farms = db.getListString(parentFarm + "_ripple");
        }
        RippleInstance r = new RippleInstance(textbox.getText().toString(), Bitmap.createScaledBitmap(icon, 128,  icon.getHeight()/(icon.getWidth()/128), false));
        Random q = new Random();
        r.waterLevel = q.nextInt(100);

        r.Depth = Integer.parseInt(depth.getText().toString());
        r.RippleHeight = Integer.parseInt(height.getText().toString());
        Log.d("depth" , r.Depth + "");
        Log.d("height" , r.RippleHeight + "");


        String farmName = r.toString();
        farms.add(farmName);
        db.putListString(parentFarm + "_ripple", farms);
        setResult(RESULT_OK);
        finish();
    }

}
