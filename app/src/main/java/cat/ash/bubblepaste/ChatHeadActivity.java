package cat.ash.bubblepaste;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

/**
 * Created by cat on 2016/6/14.
 */
public class ChatHeadActivity extends Activity implements ServiceConnection, ChatHeadFragment.ChatHeadActionCallback {

    private static final String FRAGMENT_TAG_CHATHEAD = "chathead";

    private ChatHeadService mChatHeadService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chathead);

        if (savedInstanceState == null) {
            final FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.container, ChatHeadFragment.newInstance(), FRAGMENT_TAG_CHATHEAD);
            ft.commit();
        }

    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mChatHeadService = ((ChatHeadService.ChatHeadServiceBinder) service).getService();
        if (mChatHeadService != null) {
            unbindService(this);
            mChatHeadService.stopSelf();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mChatHeadService = null;
    }

    @Override
    public void clearChatHead() {
        bindService(new Intent(this, ChatHeadService.class), this, Context.BIND_AUTO_CREATE);
    }
}