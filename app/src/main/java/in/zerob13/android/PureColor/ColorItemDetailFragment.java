package in.zerob13.android.PureColor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;

import in.zerob13.android.PureColor.Utils.BitmapUtils;
import in.zerob13.android.PureColor.Utils.DummyAssets;
import in.zerob13.android.PureColor.Utils.DummyContent;

/**
 * A fragment representing a single ColorItem detail screen.
 * This fragment is either contained in a {@link ColorItemListActivity}
 * in two-pane mode (on tablets) or a {@link ColorItemDetailActivity}
 * on handsets.
 */
public class ColorItemDetailFragment extends Fragment implements View.OnLongClickListener {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    private static String savePath = null;

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
        if (TextUtils.isEmpty(savePath)) {
            String faDir = DummyAssets.getExternalStoragePath();
            if (TextUtils.isEmpty(faDir)) {
                faDir = getActivity().getFilesDir().getPath();
            }
            savePath = faDir + File.separator + "zerob13" + File.separator + "wallpapers";
        }

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
            TextView tv = ((TextView) rootView.findViewById(R.id.coloritem_detail));
            int color = Color.parseColor(mItem.hex);
            tv.setBackgroundColor(color);
            int rColor = Color.rgb(255 - Color.red(color), 255 - Color.green(color), 255 - Color.blue(color));
            tv.setTextColor(rColor);
        }
        rootView.setOnLongClickListener(this);

        return rootView;
    }

    @Override
    public boolean onLongClick(View view) {
        new AlertDialog.Builder(getActivity()).setTitle(R.string.save_alert_dialog_title).setMessage(R.string.save_alert_dialog_message).setPositiveButton(R.string.common_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                doGenBackground();
            }
        }).setNegativeButton(R.string.common_no, null).show();


        return false;
    }

    private void doGenBackground() {
        Bitmap newbit = BitmapUtils.createBitmap(getActivity(), DummyContent.ScreenHeight, DummyContent.ScreenWidth, Color.parseColor(mItem.hex));
        BitmapUtils.writeBitmapToFile(newbit, savePath, mItem.hex.substring(1, mItem.hex.length()) + ".png");
        BitmapUtils.setWallpaper(getActivity(), newbit);
        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(getActivity(), "Saved to " + savePath + mItem.hex + " and seted ", Toast.LENGTH_SHORT);
                toast.show();
            }
        }, 100);
    }
}
