package com.ishan1608.healthifyPlus;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class PlaceHolderFragment extends Fragment {

    public static PlaceHolderFragment newInstance() {
        PlaceHolderFragment fragment = new PlaceHolderFragment();
        return fragment;
    }

    public PlaceHolderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_place_holder, container, false);
    }
}
