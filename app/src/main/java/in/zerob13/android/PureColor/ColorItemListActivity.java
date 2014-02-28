package in.zerob13.android.PureColor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import in.zerob13.android.PureColor.dummy.DummyAssets;


/**
 * An activity representing a list of ColorItems. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ColorItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ColorItemListFragment} and the item details
 * (if present) is a {@link ColorItemDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link ColorItemListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class ColorItemListActivity extends FragmentActivity
        implements ColorItemListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private static String sColors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coloritem_list);

        if (findViewById(R.id.coloritem_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((ColorItemListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.coloritem_list))
                    .setActivateOnItemClick(true);
        }
        sColors= DummyAssets.getFromRaw(getBaseContext());

        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link ColorItemListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(ColorItemDetailFragment.ARG_ITEM_ID, id);
            ColorItemDetailFragment fragment = new ColorItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.coloritem_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, ColorItemDetailActivity.class);
            detailIntent.putExtra(ColorItemDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
