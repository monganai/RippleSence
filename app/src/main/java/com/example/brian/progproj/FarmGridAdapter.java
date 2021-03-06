package com.example.brian.progproj;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class FarmGridAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<FarmInstance> data;

    public FarmGridAdapter(Context c, ArrayList<FarmInstance> array) {
        mContext = c;
        data = array;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public FarmInstance getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            grid = inflater.inflate(R.layout.grid_item, null);

        } else {
          grid = convertView;
        }
        TextView textView = (TextView)grid.findViewById(R.id.grid_text);
        textView.setText(getItem(position).getName());
        CircularImageView imageView = (CircularImageView) grid.findViewById(R.id.grid_image);
        imageView.setImageBitmap(getItem(position).getImage());
        final String name = getItem(position).getName();
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RippleActivity.class);
                intent.putExtra("farm", name);
                mContext.startActivity(intent);
            }
        });
        return grid;
    }

}