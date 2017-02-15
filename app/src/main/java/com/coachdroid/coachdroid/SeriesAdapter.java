package com.coachdroid.coachdroid;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.coachdroid.coachdroid.db.Series;

import java.util.List;
import java.util.Locale;


public class SeriesAdapter extends ArrayAdapter<Series> {

    private static class ViewHolder {
        TextView times;
        TextView length;
        TextView name;
        TextView description;
    }

    private Context context;
    private List<Series> series;

    public SeriesAdapter(Context context, List<Series> series) {
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

//        ViewHolder holder = new ViewHolder();

        TextView times = (TextView) convertView.findViewById(R.id.textTimes);
        TextView length = (TextView) convertView.findViewById(R.id.textLength);
        TextView name = (TextView) convertView.findViewById(R.id.textName);
        TextView description = (TextView) convertView.findViewById(R.id.textDescription);
//        holder.times = (TextView) convertView.findViewById(R.id.textTimes);
//        holder.length = (TextView) convertView.findViewById(R.id.textLength);
//        holder.name = (TextView) convertView.findViewById(R.id.textName);
//        holder.description = (TextView) convertView.findViewById(R.id.textDescription);

//        convertView.setTag(holder);

        Series s = series.get(position);

        Locale l;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            l = context.getResources().getConfiguration().getLocales().get(0);
        } else {
            // use of Configuration.locale is deprecated, this ensures backwards-compatibility
            //noinspection deprecation
            l = context.getResources().getConfiguration().locale;
        }
        times.setText(String.format(l, "%d", s.getTimes()));
        length.setText(String.format(l, "%d", s.getLength()));
        name.setText(s.getName());
        description.setText(s.getDescription());
//        holder.times.setText(s.getTimes());
//        holder.length.setText(s.getLength());
//        holder.name.setText(s.getName());
//        holder.description.setText(s.getDescription());

        return convertView;
    }
}
