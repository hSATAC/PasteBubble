package cat.ash.bubblepaste;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

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
        // Set the type of events that this service wants to listen to.  Others
        // won't be passed to this service.
//        AccessibilityServiceInfo info = getServiceInfo();

//        info.eventTypes = AccessibilityEvent.TYPE_VIEW_FOCUSED | AccessibilityEvent.TYPE_VIEW_CLICKED;

        // If you only want this service to work with specific applications, set their
        // package names here.  Otherwise, when the service is activated, it will listen
        // to events from all applications.
        //info.packageNames = new String[]
        //        {"com.example.android.myFirstApp", "com.example.android.mySecondApp"};

        // Set the type of feedback your service will provide.
//        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;

        // Default services are invoked only if no package-specific ones are present
        // for the type of AccessibilityEvent generated.  This service *is*
        // application-specific, so the flag isn't necessary.  If this was a
        // general-purpose service, it would be worth considering setting the
        // DEFAULT flag.

//        info.flags = AccessibilityServiceInfo.DEFAULT;

//        info.notificationTimeout = 100;

//        this.setServiceInfo(info);

    }
}
