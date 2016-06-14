package cat.ash.bubblepaste;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by cat on 2016/6/15.
 */
public class FloatingViewControlFragment extends Fragment {

    private static final String TAG = "FloatingViewControl";

    private CountDownTimer mTimer;

    private static final int CHATHEAD_OVERLAY_PERMISSION_REQUEST_CODE = 100;

    public static FloatingViewControlFragment newInstance() {
        FloatingViewControlFragment fragment = new FloatingViewControlFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public FloatingViewControlFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTimer = new CountDownTimer(3000L, 3000L) {
            @Override
            public void onTick(long millisUntilFinished) {
                // do nothing
            }

            @Override
            public void onFinish() {
            }
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_floating_view_control, container, false);

        rootView.findViewById(R.id.showDemo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = getActivity();
                final boolean canShow = showChatHead(context);
                if (!canShow) {
                    @SuppressLint("InlinedApi")
                    final Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName()));
                    startActivityForResult(intent, CHATHEAD_OVERLAY_PERMISSION_REQUEST_CODE);
                }
            }
        });

        return rootView;
    }

    @Override
    @TargetApi(Build.VERSION_CODES.M)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHATHEAD_OVERLAY_PERMISSION_REQUEST_CODE) {
            final Context context = getActivity();
            final boolean canShow = showChatHead(context);
            if (!canShow) {
                Log.w(TAG, "Permission denied");
            }
        }
    }

    @Override
    public void onDestroy() {
        if (mTimer != null) {
            mTimer.cancel();
        }
        super.onDestroy();
    }

    @SuppressLint("NewApi")
    private boolean showChatHead(Context context) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            context.startService(new Intent(context, ChatHeadService.class));
            return true;
        }

        if (Settings.canDrawOverlays(context)) {
            context.startService(new Intent(context, ChatHeadService.class));
            return true;
        }

        return false;
    }
}
