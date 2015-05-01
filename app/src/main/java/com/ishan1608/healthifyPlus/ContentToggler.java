package com.ishan1608.healthifyPlus;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ishan on 5/2/15.
 */
public class ContentToggler implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        ViewGroup parent = (ViewGroup) v.getParent();
        View childView = parent.getChildAt(parent.indexOfChild(v) + 1);
        this.viewToggler(childView);
    }

    private void viewToggler(View view) {
        if(view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }
}
