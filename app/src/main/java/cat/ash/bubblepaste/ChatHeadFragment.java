package cat.ash.bubblepaste;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ChatHeadFragment extends Fragment {

    private ChatHeadActionCallback mChatHeadActionCallback;

    public static ChatHeadFragment newInstance() {
        final ChatHeadFragment fragment = new ChatHeadFragment();
        return fragment;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mChatHeadActionCallback = (ChatHeadActionCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement " + ChatHeadActionCallback.class.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_chathead, container, false);

        final View clearFloatingButton = rootView.findViewById(R.id.clearDemo);
        clearFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChatHeadActionCallback.clearChatHead();
            }
        });
        return rootView;
    }

    public interface ChatHeadActionCallback {

        void clearChatHead();

    }
}
