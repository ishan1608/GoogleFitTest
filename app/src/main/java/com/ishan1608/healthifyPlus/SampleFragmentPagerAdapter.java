package com.ishan1608.healthifyPlus;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;


/**
 * Created by samsung on 21-03-2015.
 */
public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 5;
    private String tabTitles[] = new String[] { "Health Question", "hygiene question", "LifeStyle questions","Medical History questions","Diet"};
    private Context context;

    public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:

                return new HealthQuestionsFragment();
            case 1:
                return new HealthQuestionsFragment1();
            case 2:
                return new LifestyleQuestionsFragment();
            case 3:
                return new MedicalQuestionsFragment();
            case 4:
                return new DietQuestionsFragment();


            default:
                return PageFragment.newInstance(position + 1);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}