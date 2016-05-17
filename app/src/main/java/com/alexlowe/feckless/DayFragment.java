package com.alexlowe.feckless;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Keyes on 5/13/2016.
 */
public class DayFragment extends Fragment {
    private Day day;
    TextView text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day, container, false);
        Bundle bundle = this.getArguments();
        text = (TextView) view.findViewById(R.id.section_label);
        day = bundle.getParcelable(PagerFragment.DAY_OBJECT);
        text.setText(day.getDay());
        return view;
    }
}
