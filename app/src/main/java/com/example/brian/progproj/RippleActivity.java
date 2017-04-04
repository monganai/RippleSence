package com.example.brian.progproj;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.charts.SeriesLabel;
import com.hookedonplay.decoviewlib.events.DecoEvent;

import java.util.ArrayList;


public class RippleActivity extends Activity {


    static final int ADD_RIPPLE_REQUEST = 1;

    ArrayList<RippleInstance> farms = new ArrayList<>();
    RippleGridAdapter arrayAdapter;
    String parentFarm = "";
    float warningPercentage = 10.0f;
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
        TextView title = (TextView)layout.findViewById(R.id.textViewFarm);
        String FarmN;
        SpannableString Farmc;
        Farmc = new SpannableString(parentFarm);
        Farmc.setSpan(new RelativeSizeSpan(1f), 0, parentFarm.length(), 0); // set size
        Farmc.setSpan(new ForegroundColorSpan(Color.WHITE), 0, parentFarm.length(), 0);// set color
        title.setText(Farmc);
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

        DecoView decoView = (DecoView) findViewById(R.id.decoView2);
        TextView midle = (TextView)  findViewById(R.id.middleMulti);
        ArrayList<Integer> series = new ArrayList<>();
        ArrayList<SeriesItem> seriesItems = new ArrayList<>();
        String[] colours = {"#1BC2EE", "#039CD5", "#4075BB", "#21409A"};

        if(farms.size() != 0) {
            float lineWidth = 200 / farms.size();
            if(farms.size() == 1)
                lineWidth = 50;

            for (int i = 0; i < farms.size(); i++) {
                int c = Color.parseColor(colours[i%4]);

                if(farms.get(i).waterLevel < warningPercentage)
                    c = Color.RED;

                final SeriesItem seriesItem = new SeriesItem.Builder(c)
                        .setRange(0, 100, 0)
                        .setInset(new PointF(50f + (lineWidth * i), 50f + (lineWidth * i)))
                        .setInitialVisibility(false)
                        .setLineWidth(lineWidth)
                        .setSeriesLabel(new SeriesLabel.Builder(farms.get(i).getName() + " %.0f%%")
                               .setColorBack(Color.argb(218, 0, 0, 0))
                               .setColorText(Color.argb(255, 255, 255, 255))
                                .setVisible(false)
                                .build())
                        .build();
                seriesItems.add(seriesItem);
                series.add(decoView.addSeries(seriesItem));

                int ANS=0;
                String middlePercent;
                SpannableString middleS;
                for(int z =0;z<series.size();z++){
                    ANS = ANS + farms.get(z).waterLevel;
                    decoView.addEvent(new DecoEvent.Builder(farms.get(z).waterLevel)
                            .setIndex(series.get(z))
                            .setDelay(1000)
                            .build());
                }
                ANS = ANS/farms.size();


                middlePercent = "" + (int)ANS + "%";
                middleS = new SpannableString(middlePercent);
                middleS.setSpan(new RelativeSizeSpan(2f), 0, middlePercent.length(), 0); // set size
                middleS.setSpan(new ForegroundColorSpan(Color.parseColor("#1BC2EE")), 0, middlePercent.length(), 0);// set color

                midle.setText(middleS);



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
            DecoView decoView = (DecoView) findViewById(R.id.decoView2);
            decoView.executeReset();
            ArrayList<Integer> series = new ArrayList<>();
            ArrayList<SeriesItem> seriesItems = new ArrayList<>();
            String[] colours = {"#1BC2EE", "#039CD5", "#4075BB", "#21409A"};

            float lineWidth = 160/farms.size();
            if(farms.size() < 3)
                lineWidth = 160/3;
            for(int i = 0; i < farms.size(); i++) {
                int c = Color.parseColor(colours[i%4]);

                if(farms.get(i).waterLevel < warningPercentage)
                    c = Color.RED;
                final SeriesItem seriesItem = new SeriesItem.Builder((c))
                        .setRange(0, 100, 0)
                        .setInset(new PointF(50f + (lineWidth * i), 50f + (lineWidth * i)))
                        .setInitialVisibility(false)
                        .setLineWidth(lineWidth)
                        .build();



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
