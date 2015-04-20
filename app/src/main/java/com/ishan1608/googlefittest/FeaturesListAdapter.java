package com.ishan1608.googlefittest;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * Created by ishan on 4/20/15.
 */
public class FeaturesListAdapter extends BaseAdapter implements ListAdapter {
    private final Activity activity;
    String[] featuresList = {"Home", "Weather Suggestions", "Report Card", "Activity"};
    private TextView featureTitleTextView;
    private LinearLayout featuresListItem;
    private DrawerLayout featuresDrawerLayout;
    private Fragment featureFragment;

    public FeaturesListAdapter(Activity activity) {
        super();
        this.activity = activity;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return featuresList[position];
    }

    @Override
    public int getCount() {
        return featuresList.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if(convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.features_list_item, null);
        }
        // Setting Title
        featureTitleTextView = (TextView) convertView.findViewById(R.id.feature_title);
        featureTitleTextView.setText(featuresList[position]);
        // TODO: Setting Icon

        // Getting handle for drawer layout
        featuresDrawerLayout = (DrawerLayout) activity.findViewById(R.id.features_drawer_layout);

        // Setting click listener
        featuresListItem = (LinearLayout) convertView.findViewById(R.id.features_list_item);

        switch (position) {
            case 3:
                featureFragment = new PhysicalFragment();
                break;
            default:
                featureFragment = PlaceHolderFragment.newInstance();
                break;
        }

        featuresListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction featureTransaction = activity.getFragmentManager().beginTransaction();

                featureTransaction.replace(R.id.main_container, featureFragment);
                featureTransaction.commit();
                featuresDrawerLayout.closeDrawer(Gravity.LEFT);
            }
        });
        return convertView;
    }
}
