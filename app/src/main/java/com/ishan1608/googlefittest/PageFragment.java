package com.ishan1608.googlefittest;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    //    //new code
//    // View rootView;
//    private TextView questionContentView1,questionContentView2,questionContentView3,questionContentView4,questionContentView5;
//    // private LinearLayout optionView;
//    private TextView optionView,optionContentView2,optionContentView3,optionContentView4,optionContentView5;
//    private View questionSetHeadingView, questionHeadingView1,questionHeadingView2,questionHeadingView3,questionHeadingView4,questionHeadingView5;
//    private View optionHeadingView,optionHeadingView2,optionHeadingView3,optionHeadingView4,optionHeadingView5;
//    private Button registrationButton;
//    private int categoryPosition, eventPosition;
//    //new code ends
    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_question, container, false);

//        TextView textView = (TextView) view;
//        textView.setText("Fragment #" + mPage);
        return rootView;
    }
}