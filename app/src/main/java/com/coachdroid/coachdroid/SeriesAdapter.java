package com.coachdroid.coachdroid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.coachdroid.coachdroid.db.Series;

import java.util.ArrayList;
import java.util.List;


public class SeriesAdapter extends ArrayAdapter<Series> {

    private static class ViewHolder {
        TextView times;
        TextView length;
        TextView name;
        TextView description;
    }

    private Context context;
    private List<Series> series;

    public SeriesAdapter(Context context, ArrayList<Series> series) {
        super(context, R.layout.series_row, series);

        this.context = context;
        this.series = series;
    }

    @NonNull @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.series_row, parent, false);
        }

        ViewHolder holder = new ViewHolder();

        holder.times = (TextView) convertView.findViewById(R.id.textTimes);
        holder.length = (TextView) convertView.findViewById(R.id.textLength);
        holder.name = (TextView) convertView.findViewById(R.id.textName);
        holder.description = (TextView) convertView.findViewById(R.id.textDescription);

        convertView.setTag(holder);

        Series s = series.get(position);

        holder.times.setText(s.getTimes());
        holder.length.setText(s.getLength());
        holder.name.setText(s.getName());
        holder.description.setText(s.getDescription());

        return convertView;
    }
}
