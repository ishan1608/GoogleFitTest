package com.ishan1608.googlefittest;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ReportCardFragment extends Fragment {

    private View returnView;

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
        returnView = inflater.inflate(R.layout.fragment_report_card, container, false);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) returnView.findViewById(R.id.report_view_pager);
        // viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(), this));
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getFragmentManager(), getActivity().getApplicationContext()));

        // Give the SlidingTabLayout the ViewPager
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) returnView.findViewById(R.id.report_sliding_tabs);
        // Center the tabs in the layout
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);


        return  returnView;
    }


}
