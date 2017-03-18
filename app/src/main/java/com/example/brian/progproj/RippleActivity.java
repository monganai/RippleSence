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
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.charts.SeriesLabel;
import com.hookedonplay.decoviewlib.events.DecoEvent;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;


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
        LinearLayout layout = (LinearLayout)findViewById(R.id.graph_layout);
        TextView title = (TextView)layout.findViewById(R.id.textView);
        title.setText(parentFarm);
        View root = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        root.setBackgroundColor(Color.WHITE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        GridView list = (GridView) findViewById(R.id.rippleList);
        list.setEmptyView(findViewById(R.id.empty));
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        TinyDB db = new TinyDB(this.getApplicationContext());
        ArrayList<String> strings = db.getListString(parentFarm + "_ripple");

        for(String s : strings){ //parentFarm + ":" + r.getName() + ".png"
            RippleInstance r = new Gson().fromJson(s, RippleInstance.class);
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
        ArrayList<Integer> series = new ArrayList<>();
        ArrayList<SeriesItem> seriesItems = new ArrayList<>();
        String[] colours = {"#1BC2EE", "#039CD5", "#4075BB", "#21409A"};
        String[] greys = {"#D0D0D0", "#B8B8B8"};
        if(farms.size() != 0) {
            float lineWidth = 160 / farms.size();
            for (int i = 0; i < farms.size(); i++) {
                decoView.addSeries(new SeriesItem.Builder(Color.parseColor(greys[i % greys.length]))
                        .setRange(0, 100, 100)
                        .setInset(new PointF(50f + (lineWidth * i), 50f + (lineWidth * i)))
                        .setInitialVisibility(true)
                        .setLineWidth(lineWidth)
                        .build());
                final SeriesItem seriesItem = new SeriesItem.Builder((Color.parseColor(colours[i % 4])))
                        .setRange(0, 100, 0)
                        .setInset(new PointF(50f + (lineWidth * i), 50f + (lineWidth * i)))
                        .setInitialVisibility(false)
                        .setLineWidth(lineWidth)
                        .setSeriesLabel(new SeriesLabel.Builder(farms.get(i).getName() + " %.0f%%")
                                .setColorBack(Color.argb(218, 0, 0, 0))
                                .setColorText(Color.argb(255, 255, 255, 255))
                                .setVisible(true)
                                .build())
                        .build();
                seriesItems.add(seriesItem);
                series.add(decoView.addSeries(seriesItem));

            }
            for (int x = 0; x < series.size(); x++) {
                decoView.addEvent(new DecoEvent.Builder(farms.get(x).waterLevel)
                        .setIndex(series.get(x))
                        .setDelay(1000)
                        .build());
            }
        }
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

        for(String s : strings){
            RippleInstance r = new Gson().fromJson(s, RippleInstance.class);
            farms.add(r);
        }
        arrayAdapter = new RippleGridAdapter(
                this,
                farms);
        GridView list = (GridView) findViewById(R.id.rippleList);
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
                for(String s : strings){
                    RippleInstance r = new Gson().fromJson(s, RippleInstance.class);
                    farms.add(r);
                }
                arrayAdapter.notifyDataSetChanged();
            }
            DecoView decoView = (DecoView) findViewById(R.id.dynamicArcView2);
            decoView.executeReset();
            ArrayList<Integer> series = new ArrayList<>();
            ArrayList<SeriesItem> seriesItems = new ArrayList<>();
            String[] colours = {"#1BC2EE", "#039CD5", "#4075BB", "#21409A"};
            float lineWidth = 160/farms.size();
            if(farms.size() < 3)
                lineWidth = 160/3;
            for(int i = 0; i < farms.size(); i++) {
                final SeriesItem seriesItem = new SeriesItem.Builder((Color.parseColor(colours[i%4])))
                        .setRange(0, 100, 0)
                        .setInset(new PointF(50f + (lineWidth * i), 50f + (lineWidth * i)))
                        .setInitialVisibility(false)
                        .setLineWidth(lineWidth)
                        .build();

                seriesItems.add(seriesItem);
                series.add(decoView.addSeries(seriesItem));

            }
            for(int x = 0; x < series.size(); x++){
                decoView.addEvent(new DecoEvent.Builder(farms.get(x).waterLevel)
                        .setIndex(series.get(x))
                        .setDelay(1000)
                        .build());
            }
        }
    }

    public void onClick(View view){
        Intent intent = new Intent(this, AddRippleActivity.class);
        intent.putExtra("farm", parentFarm);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivityForResult(intent, ADD_RIPPLE_REQUEST);
    }

}
