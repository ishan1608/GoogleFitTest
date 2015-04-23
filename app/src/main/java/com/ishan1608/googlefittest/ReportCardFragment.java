package com.ishan1608.googlefittest;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ReportCardFragment extends Fragment {

    public static ReportCardFragment newInstance(String param1, String param2) {
        ReportCardFragment fragment = new ReportCardFragment();
        return fragment;
    }

    public ReportCardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report_card, container, false);
    }


}
