package cat.ash.bubblepaste;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonOnClick(View v) {
        Button button = (Button)v;

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        if (!clipboard.hasPrimaryClip()) {
            return;
        }
        ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);

        CharSequence pasteData = "";
        pasteData = item.getText();

        if (pasteData != null) {
            button.setText(pasteData);
            return;
        }
    }
}
