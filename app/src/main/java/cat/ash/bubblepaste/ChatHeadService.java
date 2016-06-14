package cat.ash.bubblepaste;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

import jp.co.recruit_lifestyle.android.floatingview.FloatingViewListener;
import jp.co.recruit_lifestyle.android.floatingview.FloatingViewManager;

/**
 * Created by cat on 2016/6/14.
 */
public class ChatHeadService extends Service implements FloatingViewListener {


    private static final String TAG = "ChatHeadService";

    private static final int NOTIFICATION_ID = 9083150;

    private IBinder mChatHeadServiceBinder;

    private FloatingViewManager mFloatingViewManager;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mFloatingViewManager != null) {
            return START_STICKY;
        }

        final DisplayMetrics metrics = new DisplayMetrics();
        final WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        mChatHeadServiceBinder = new ChatHeadServiceBinder(this);
        final LayoutInflater inflater = LayoutInflater.from(this);
        final ImageView iconView = (ImageView) inflater.inflate(R.layout.widget_chathead, null, false);
        iconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick");
                ViewParent parentView = v.getParent();
                if (parentView != null) {
                    final AccessibilityManager a11yManager =
                            (AccessibilityManager) v.getContext().getSystemService(Context.ACCESSIBILITY_SERVICE);

                    if (a11yManager != null && a11yManager.isEnabled()) {
                        final AccessibilityEvent e = AccessibilityEvent.obtain(AccessibilityEventCompat.TYPE_ANNOUNCEMENT);
                        e.setEnabled(v.isEnabled());
                        e.setClassName(getClass().getName());
                        e.setPackageName(v.getContext().getPackageName());
                        e.getText().add("test");
                        final AccessibilityRecordCompat record = new AccessibilityRecordCompat(e);
                        record.setSource(v);
                        a11yManager.sendAccessibilityEvent(e);
                        Log.d(TAG, "event sent");
                    } else {
                        Log.d(TAG, " no manager");
                    }
                } else {
                    Log.d(TAG, "no parent");
                }

//                v.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_CLICKED);
//                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//
//                if (!clipboard.hasPrimaryClip()) {
//                    return;
//                }
//                ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
//
//                CharSequence pasteData = "";
//                pasteData = item.getText();
//
//                if (pasteData != null) {
//                    v.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_CLICKED);
//                    return;
//                }
            }
        });

        mFloatingViewManager = new FloatingViewManager(this, this);
        mFloatingViewManager.setFixedTrashIconImage(R.drawable.ic_trash_fixed);
        mFloatingViewManager.setActionTrashIconImage(R.drawable.ic_trash_action);
        final FloatingViewManager.Options options = new FloatingViewManager.Options();
        options.shape = FloatingViewManager.SHAPE_CIRCLE;
        options.overMargin = (int) (16 * metrics.density);
        mFloatingViewManager.addViewToWindow(iconView, options);

        startForeground(NOTIFICATION_ID, createNotification());

        Intent i = new Intent(this, MyAccessibilityService.class);
        startService(i);


        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        destroy();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mChatHeadServiceBinder;
    }

    @Override
    public void onFinishFloatingView() {
        stopSelf();
    }

    private void destroy() {
        if (mFloatingViewManager != null) {
            mFloatingViewManager.removeAllViewToWindow();
            mFloatingViewManager = null;
        }
    }

    private Notification createNotification() {
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("ContentTitle");
        builder.setContentText("ContentText");
        builder.setOngoing(true);
        builder.setPriority(NotificationCompat.PRIORITY_MIN);
        builder.setCategory(NotificationCompat.CATEGORY_SERVICE);

        final Intent notifyIntent = new Intent(this, ChatHeadActivity.class);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(notifyPendingIntent);

        return builder.build();
    }

    public static class ChatHeadServiceBinder extends Binder {

        private final WeakReference<ChatHeadService> mService;

        ChatHeadServiceBinder(ChatHeadService service) {
            mService = new WeakReference<>(service);
        }

        public ChatHeadService getService() {
            return mService.get();
        }
    }

}