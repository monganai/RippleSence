package com.example.brian.progproj;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

import java.util.ArrayList;


public class FarmActivity extends Activity {

    static final int ADD_FARM_REQUEST = 1;

    ArrayList<FarmInstance> farms = new ArrayList<>();
    FarmGridAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm);


DecoView decoView;

        decoView = (DecoView) findViewById(R.id.dynamicArcView2);

        SeriesItem item = new SeriesItem.Builder(Color.BLUE).setRange(0, 3500, 0).build();

        View root = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        root.setBackgroundColor(Color.WHITE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        GridView list = (GridView) findViewById(R.id.farmList);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        TinyDB db = new TinyDB(this.getApplicationContext());
        ArrayList<String> strings = db.getListString("Farms");
       // Snackbar.make(findViewById(android.R.id.content), strings.get(0), Snackbar.LENGTH_LONG).setDuration(1800).show();

        for(String s : strings){
            Bitmap bitmap = Bitmap.createScaledBitmap(new ImageSaver(this.getApplicationContext()).
                    setFileName(s + ".png").
                    setDirectoryName("farms").
                    load(), 128, 128, false);
            farms.add(new FarmInstance(s, bitmap));
        }
        arrayAdapter = new FarmGridAdapter(
                this,
                farms);
        list.setAdapter(arrayAdapter);



    }

    protected void onResume(){
        super.onResume();
        TinyDB db = new TinyDB(this.getApplicationContext());
        farms.clear();
        ArrayList<String> strings = db.getListString("Farms");
        for(String s : strings){
            Bitmap bitmap = new ImageSaver(this.getApplicationContext()).
                    setFileName(s + ".png").
                    setDirectoryName("farms").
                    load();
            farms.add(new FarmInstance(s, bitmap));
        }
        arrayAdapter = new FarmGridAdapter(
                this,
                farms);
        GridView list = (GridView) findViewById(R.id.farmList);
        list.setAdapter(arrayAdapter);
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
                ArrayList<String> strings = db.getListString("Farms");
                farms.clear();
                for(String s : strings){
                    Bitmap bitmap = new ImageSaver(this.getApplicationContext()).
                            setFileName(s + ".png").
                            setDirectoryName("farms").
                            load();
                    farms.add(new FarmInstance(s, bitmap));
                }
                arrayAdapter.notifyDataSetChanged();
            }
        }
    }

    public void onClick(View view){
        Intent intent = new Intent(this, AddFarmActivity.class);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivityForResult(intent, ADD_FARM_REQUEST);
    }
/**
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("farmSize", farms.size());
        for(int i = 0; i < farms.size(); i++){
            outState.putString("farms" + i, new Gson().toJson(farms.get(i), FarmInstance.class));
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedState) {
        super.onRestoreInstanceState(savedState);
        int size = savedState.getInt("farmSize");
        Gson gson = new Gson();
        for(int i = 0; i < size; i++){
            String json = savedState.getString("farms" + i);
            FarmInstance farm = gson.fromJson(json, FarmInstance.class);
            farms.add(farm);
        }
        arrayAdapter = new FarmGridAdapter(
                this,
                farms);
        GridView list = (GridView) findViewById(R.id.farmList);
        list.setAdapter(arrayAdapter);
    }
**/
}
