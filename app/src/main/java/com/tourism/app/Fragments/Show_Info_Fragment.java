package com.tourism.app.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tourism.app.R;

public class Show_Info_Fragment extends Fragment {

    public static Show_Info_Fragment newInstance() {
        return new Show_Info_Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_info_fragment, container, false);
        view.setBackgroundColor(getResources().getColor(R.color.backround));
        return view;
    }

}
