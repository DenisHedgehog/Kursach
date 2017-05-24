package com.example.hedgehog.kursach;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by hedgehog on 17.04.17.
 */

public class Utils {

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalDividersHeight = 0;
        int totalHeight = 0;
        int totalPadding = 0;
        for (int itemPos = 0; itemPos < listAdapter.getCount(); itemPos++) {
            View item = listAdapter.getView(itemPos, null, listView);
            float px = 360 * (listView.getResources().getDisplayMetrics().density);
            item.measure(View.MeasureSpec.makeMeasureSpec((int)px, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            totalHeight += item.getMeasuredHeight();

            totalPadding += item.getPaddingTop() + item.getPaddingBottom() + (item.getBottom() - item.getTop());
        }


        totalDividersHeight = listView.getDividerHeight() * (listView.getCount() - 1);

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + totalDividersHeight + totalPadding;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

}
