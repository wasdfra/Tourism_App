package com.tourism.app.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tourism.app.R;

public class Show_Map_Fragment extends Fragment {

    public static Show_Map_Fragment newInstance() {
        return new Show_Map_Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_map_fragment, container, false);
        view.setBackgroundColor(getResources().getColor(R.color.backround));
        return view;
    }


}
