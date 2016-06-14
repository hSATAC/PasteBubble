package cat.ash.bubblepaste;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

/**
 * Created by cat on 2016/6/15.
 */
public class MyAccessibilityService extends AccessibilityService {

    private static final String TAG = "MyAccessibilityService";

    private AccessibilityEvent mLastAccessibilityViewFocusedEvent;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.v(TAG, "***** onAccessibilityEvent");
        Log.v(TAG, event.toString());
        final int eventType = event.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_ANNOUNCEMENT:
                Log.d(TAG, "accouncement");
                if (mLastAccessibilityViewFocusedEvent != null) {
                    Log.d(TAG, mLastAccessibilityViewFocusedEvent.toString());
                    AccessibilityNodeInfo source = mLastAccessibilityViewFocusedEvent.getSource();
                    if (source == null) {
                        return;
                    } else {
                        Log.d(TAG, "paste");
                        source.performAction(AccessibilityNodeInfoCompat.ACTION_PASTE);
                    }
                }
                break;
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                Log.d(TAG, "focused.");
                mLastAccessibilityViewFocusedEvent = event;
                break;
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                Log.d(TAG, "clicked.");
//                mLastAccessibilityViewFocusedEvent = event;
                break;
            default:
                Log.d(TAG, "unknown event type.");
                break;

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
        info.eventTypes = AccessibilityEvent.TYPE_VIEW_CLICKED | AccessibilityEvent.TYPE_VIEW_FOCUSED|AccessibilityEvent.TYPE_ANNOUNCEMENT;
        info.notificationTimeout = 100;
        info.feedbackType = AccessibilityEvent.TYPE_VIEW_CLICKED | AccessibilityEvent.TYPE_VIEW_FOCUSED|AccessibilityEvent.TYPE_ANNOUNCEMENT;

        setServiceInfo(info);
    }
}
