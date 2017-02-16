package com.example.brian.progproj;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class FarmActivity extends AppCompatActivity {

    static final int ADD_FARM_REQUEST = 1;

    ArrayList<String> strings = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ListView list = (ListView) findViewById(R.id.farmList);
        TinyDB db = new TinyDB(this.getApplicationContext());
        strings = db.getListString("Farms");
        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                strings);
        list.setAdapter(arrayAdapter);

    }

    protected void onResume(){
        super.onResume();
        TinyDB db = new TinyDB(this.getApplicationContext());
        strings.clear();
        strings.addAll(db.getListString("Farms"));
        arrayAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == ADD_FARM_REQUEST) {
         // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Snackbar.make(findViewById(android.R.id.content), "Farm Added!" , Snackbar.LENGTH_LONG).setDuration(1800).show();
                TinyDB db = new TinyDB(this.getApplicationContext());
                strings.clear();
                strings.addAll(db.getListString("Farms"));
                arrayAdapter.notifyDataSetChanged();
            }
        }
    }

    public void onClick(View view){
        Intent intent = new Intent(this, AddFarmActivity.class);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivityForResult(intent, ADD_FARM_REQUEST);
    }

}
