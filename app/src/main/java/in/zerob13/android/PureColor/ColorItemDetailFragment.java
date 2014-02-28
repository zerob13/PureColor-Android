package in.zerob13.android.PureColor;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.zerob13.android.PureColor.dummy.DummyContent;

/**
 * A fragment representing a single ColorItem detail screen.
 * This fragment is either contained in a {@link ColorItemListActivity}
 * in two-pane mode (on tablets) or a {@link ColorItemDetailActivity}
 * on handsets.
 */
public class ColorItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ColorItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_coloritem_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((View) rootView.findViewById(R.id.coloritem_detail)).setBackgroundColor(Color.parseColor(mItem.hex));
        }

        return rootView;
    }
}
