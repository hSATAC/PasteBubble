package cat.ash.bubblepaste;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

/**
 * Created by cat on 2016/6/15.
 */
public class MyAccessibilityService extends AccessibilityService {

    private static final String TAG = "MyAccessibilityService";

    private AccessibilityEvent mAccessibilityEvent;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.v(TAG, "***** onAccessibilityEvent");
        final int eventType = event.getEventType();
        AccessibilityNodeInfo source = event.getSource();
        if (source == null) {
            return;
        } else {
            Log.d(TAG, source.toString());
        }

    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "service interrupt");
    }

    @Override
    public void onServiceConnected() {
        Log.d(TAG, "service connected");
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_VIEW_FOCUSED;
        info.notificationTimeout = 100;
        info.feedbackType = AccessibilityEvent.TYPE_VIEW_FOCUSED;

        setServiceInfo(info);
    }
}
