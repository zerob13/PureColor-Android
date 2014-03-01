package in.zerob13.android.PureColor.controllers;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import in.zerob13.android.PureColor.Utils.DummyContent;

/**
 * Created by zerob13 on 14-3-1.
 */
public class ColorArrayAdapter extends BaseAdapter {
    private List<DummyContent.DummyItem> mItems;

    public ColorArrayAdapter(List<DummyContent.DummyItem> items) {
        mItems = items;
    }

    public void setItems(List<DummyContent.DummyItem> items) {
        mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView textView = new TextView(viewGroup.getContext());
        DummyContent.DummyItem cur = mItems.get(i);
        int bg = Color.parseColor(cur.hex);
        textView.setBackgroundColor(bg);
        int text = Color.rgb(255 - Color.red(bg), 255 - Color.green(bg), 255 - Color.blue(bg));
        textView.setTextColor(text);
        textView.setText(cur.hex);
        textView.setTextSize(30);
        return textView;
    }
}
