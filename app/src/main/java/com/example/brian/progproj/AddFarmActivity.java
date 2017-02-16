package com.example.brian.progproj;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class AddFarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_farm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void onClick(View view){
        final AddFarmActivity activity = this;

        EditText textbox = (EditText)findViewById(R.id.editText);
        if(textbox.getText().length() == 0){
            Snackbar.make(findViewById(android.R.id.content), "Please Fill Out All Fields", Snackbar.LENGTH_LONG).setDuration(1800).show();
            return;
        }
        TinyDB db = new TinyDB(this.getApplicationContext());
        ArrayList<String> farms = new ArrayList<>();
        if(db.getListString("Farms") != null){
            farms = db.getListString("Farms");
        }
        farms.add(textbox.getText().toString());
        db.putListString("Farms", farms);
        setResult(RESULT_OK);
        finish();
    }

}
