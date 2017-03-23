package com.coachdroid.coachdroid;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.coachdroid.coachdroid.db.Series;

import java.util.List;
import java.util.Locale;


public class SeriesAdapter extends ArrayAdapter<Series> {

    private TextView times;
    private TextView length;
    private TextView name;
    private TextView description;

    private Context context;
    private List<Series> series;
    private User<Integer> btnClick;

    public SeriesAdapter(Context context, List<Series> series){
        this(context, series, new User<Integer>() {
            @Override
            public void use(Integer i) {
            }
        });
    }

    public SeriesAdapter(Context context, List<Series> series, User<Integer> btnClick) {
        super(context, R.layout.series_row, series);

        this.context = context;
        this.series = series;
        this.btnClick = btnClick;
    }

    @NonNull @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.series_row, parent, false);
        }

        times = (TextView) convertView.findViewById(R.id.textTimes);
        length = (TextView) convertView.findViewById(R.id.textLength);
        name = (TextView) convertView.findViewById(R.id.textName);
        description = (TextView) convertView.findViewById(R.id.textDescription);

        Button btnExecute = (Button) convertView.findViewById(R.id.btnExecute);

        btnExecute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnClick.use(position);
            }
        });

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

        return convertView;
    }
}
