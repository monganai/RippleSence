package com.example.brian.progproj;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

import java.util.ArrayList;


public class RippleActivity extends Activity {


    static final int ADD_RIPPLE_REQUEST = 1;

    ArrayList<RippleInstance> farms = new ArrayList<>();
    RippleGridAdapter arrayAdapter;
    String parentFarm = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();





        if(b != null){
            parentFarm = b.getString("farm");
        }
        setContentView(R.layout.activity_ripple);
        View root = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        root.setBackgroundColor(Color.WHITE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        GridView list = (GridView) findViewById(R.id.farmList);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        TinyDB db = new TinyDB(this.getApplicationContext());
        ArrayList<String> strings = db.getListString(parentFarm + "_ripple");
        // Snackbar.make(findViewById(android.R.id.content), strings.get(0), Snackbar.LENGTH_LONG).setDuration(1800).show();
        /**
        for(String s : strings){
            Bitmap bitmap = Bitmap.createScaledBitmap(new ImageSaver(this.getApplicationContext()).
                    setFileName(s + ".png").
                    setDirectoryName(parentFarm + "_ripple").
                    load(),32, 32, false);
            farms.add(new RippleInstance(s, bitmap));
        }
         **/
        for(String s : strings){ //parentFarm + ":" + r.getName() + ".png"
            RippleInstance r = new Gson().fromJson(s, RippleInstance.class);
            /**Bitmap bitmap = new ImageSaver(this.getApplicationContext()).
                    setFileName(r.getName() + ".png").
                    setDirectoryName(parentFarm + "_ripple").
                    load();
            //r.setImage(bitmap);**/
            farms.add(r);
        }
        for(RippleInstance ri : farms){
            log(ri.getName(), ri.toString());
        }
        arrayAdapter = new RippleGridAdapter(
                this,
                farms);
        list.setAdapter(arrayAdapter);


        DecoView decoView = (DecoView) findViewById(R.id.dynamicArcView2);

        final SeriesItem seriesItem = new SeriesItem.Builder((Color.parseColor("#1BC2EE")))
                .setRange(0, 100, 0)
                .setInset(new PointF(50f, 50f))
                .setInitialVisibility(false)
                .setLineWidth(75)
                .build();




        final SeriesItem seriesItem2 = new SeriesItem.Builder((Color.parseColor("#039CD5")))
                .setRange(0, 100, 0)
                .setInset(new PointF(125f, 125f))
                .setInitialVisibility(false)
                .setLineWidth(75)
                .build();




        final SeriesItem seriesItem3 = new SeriesItem.Builder((Color.parseColor("#4075BB")))
                .setRange(0, 100, 0)
                .setInset(new PointF(200f, 200f))
                .setInitialVisibility(false)
                .setLineWidth(75)
                .build();




        final SeriesItem seriesItem4 = new SeriesItem.Builder((Color.parseColor("#21409A")))
                .setRange(0, 100, 0)
                .setInset(new PointF(275f, 275f))
                .setInitialVisibility(false)
                .setLineWidth(75)
                .build();


        int series1Index = decoView.addSeries(seriesItem);
        int series2Index = decoView.addSeries(seriesItem2);
        int series3Index = decoView.addSeries(seriesItem3);
        int series4Index = decoView.addSeries(seriesItem4);



        decoView.addEvent(new DecoEvent.Builder(80)
                .setIndex(series1Index)
                .setDelay(1000)
                .build());

        decoView.addEvent(new DecoEvent.Builder(70)
                .setIndex(series2Index)
                .setDelay(1000)
                .build());

        decoView.addEvent(new DecoEvent.Builder(60)
                .setIndex(series3Index)
                .setDelay(1000)
                .build());

        decoView.addEvent(new DecoEvent.Builder(90)
                .setIndex(series4Index)
                .setDelay(1000)
                .build());


    }


    public void log(String tag, String message) {
        // Split by line, then ensure each line can fit into Log's maximum length.
        for (int i = 0, length = message.length(); i < length; i++) {
            int newline = message.indexOf('\n', i);
            newline = newline != -1 ? newline : length;
            do {
                int end = Math.min(newline, i + 4000);
                Log.d(tag, message.substring(i, end));
                i = end;
            } while (i < newline);
        }
    }

    protected void onResume(){
        super.onResume();
        TinyDB db = new TinyDB(this.getApplicationContext());
        farms.clear();
        ArrayList<String> strings = db.getListString(parentFarm + "_ripple");
        /**for(String s : strings){
            Bitmap bitmap = new ImageSaver(this.getApplicationContext()).
                    setFileName(s + ".png").
                    setDirectoryName(parentFarm + "_ripple").
                    load();
            farms.add(new RippleInstance(s, bitmap));}**/
        for(String s : strings){
            RippleInstance r = new Gson().fromJson(s, RippleInstance.class);
/**            Bitmap bitmap = new ImageSaver(this.getApplicationContext()).
                    setFileName(r.getName() + ".png").
                    setDirectoryName(parentFarm + "_ripple").
                    load();
            //r.setImage(bitmap);**/
            farms.add(r);
        }
        arrayAdapter = new RippleGridAdapter(
                this,
                farms);
        GridView list = (GridView) findViewById(R.id.farmList);
        list.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == ADD_RIPPLE_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Snackbar.make(findViewById(android.R.id.content), "Device Added!" , Snackbar.LENGTH_LONG).setDuration(1800).show();
                TinyDB db = new TinyDB(this.getApplicationContext());
                ArrayList<String> strings = db.getListString(parentFarm + "_ripple");
                farms.clear();
                /**for(String s : strings){
                 Bitmap bitmap = new ImageSaver(this.getApplicationContext()).
                 setFileName(s + ".png").
                 setDirectoryName(parentFarm + "_ripple").
                 load();
                 farms.add(new RippleInstance(s, bitmap));}**/
                for(String s : strings){
                    RippleInstance r = new Gson().fromJson(s, RippleInstance.class);
                    /**Bitmap bitmap = new ImageSaver(this.getApplicationContext()).
                            setFileName(r.getName() + ".png").
                            setDirectoryName(parentFarm + "_ripple").
                            load();
                   // r.setImage(bitmap);**/
                    farms.add(r);
                }
                arrayAdapter.notifyDataSetChanged();
            }
        }
    }

    public void onClick(View view){
        Intent intent = new Intent(this, AddRippleActivity.class);
        intent.putExtra("farm", parentFarm);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivityForResult(intent, ADD_RIPPLE_REQUEST);
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
