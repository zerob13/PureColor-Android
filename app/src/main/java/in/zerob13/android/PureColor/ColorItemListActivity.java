package in.zerob13.android.PureColor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AbsListView;
import android.widget.SeekBar;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import in.zerob13.android.PureColor.Utils.DummyAssets;
import in.zerob13.android.PureColor.Utils.DummyContent;


/**
 * An activity representing a list of ColorItems. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ColorItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ColorItemListFragment} and the item details
 * (if present) is a {@link ColorItemDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link ColorItemListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class ColorItemListActivity extends FragmentActivity
        implements ColorItemListFragment.Callbacks, SeekBar.OnSeekBarChangeListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private static String sColors;
    private SeekBar mQuickSeeker;
    private boolean isNeedCallSeek = true;
    private AbsListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(R.layout.activity_coloritem_list);

        if (findViewById(R.id.coloritem_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            mListView = ((ColorItemListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.coloritem_list)).getListView();
            ((ColorItemListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.coloritem_list))
                    .setActivateOnItemClick(true);

            onItemSelected(DummyContent.ITEMS.get(0).id);
            //for tow-pan mode ,add a seeker to quick seek
            initActionbar();
        }


    }

    private void initActionbar() {
        getActionBar().setCustomView(R.layout.color_speed_slide);
        mQuickSeeker = (SeekBar) getActionBar().getCustomView().findViewById(R.id.quick_color_seek);
        mQuickSeeker.setOnSeekBarChangeListener(this);
        mQuickSeeker.setMax(DummyContent.ITEMS.size() - 1);
        mQuickSeeker.setKeyProgressIncrement(1);
        getActionBar().setDisplayShowCustomEnabled(true);
    }

    private void init() {
        if (TextUtils.isEmpty(sColors)) {
            sColors = DummyAssets.getFromRaw(getBaseContext());
        }
        try {
            JSONTokener jsonP = new JSONTokener(sColors);
            JSONObject jo = (JSONObject) jsonP.nextValue();
            JSONArray ja = jo.optJSONArray("colors");
            if (ja != null) {
                int co = 0;
                JSONObject t = ja.optJSONObject(co);
                while (t != null) {
                    DummyContent.DummyItem tem = new DummyContent.DummyItem(t.optString("num"), t.optString("hex"));
                    DummyContent.ITEMS.add(tem);
                    DummyContent.ITEM_MAP.put(tem.id, tem);
                    co++;
                    t = ja.optJSONObject(co);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        DummyContent.ScreenWidth = dm.widthPixels;
        DummyContent.ScreenHeight = dm.heightPixels;

    }

    /**
     * Callback method from {@link ColorItemListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            Bundle arguments = new Bundle();
            arguments.putString(ColorItemDetailFragment.ARG_ITEM_ID, id);
            ColorItemDetailFragment fragment = new ColorItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.coloritem_detail_container, fragment)
                    .commit();
            if (isNeedCallSeek && mQuickSeeker != null) {
                mQuickSeeker.setProgress(DummyContent.ITEMS.indexOf(DummyContent.ITEM_MAP.get(id)));
            }
        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, ColorItemDetailActivity.class);
            detailIntent.putExtra(ColorItemDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, final int i, boolean b) {
        onItemSelected(DummyContent.ITEMS.get(i).id);
        mListView.post(new Runnable() {
            @Override
            public void run() {
                mListView.setSelection(i);
                View v = mListView.getChildAt(i);
                if (v != null) {
                    v.requestFocus();
                }
            }
        });

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        isNeedCallSeek = false;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        isNeedCallSeek = true;
    }

}
