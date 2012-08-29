package net.bexton.android.device_id;

import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.widget.TextView;

public class MainActivity extends Activity {

    private static final Uri URI = Uri.parse("content://com.google.android.gsf.gservices");
    private static final String ID_KEY = "android_id";

    private String GsfAndroidId;
    private String SystemAndroidId;

    public static String getGsfAndroidId(Context context) {
        String params[] = {ID_KEY};
        Cursor c = context.getContentResolver().query(URI, null, null, params, null);
        if (!c.moveToFirst() || c.getColumnCount() < 2)
          return null;
        try {
          return Long.toHexString(Long.parseLong(c.getString(1)));
        } catch (NumberFormatException e) {
          return null;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         *  A 64-bit number (as a hex string) that is randomly generated on the device's first
         *  GSF login and should remain constant for the lifetime of the device.
         *
         *  (The value may change if a factory reset is performed on the device.)
         */
        GsfAndroidId = getGsfAndroidId(getApplication());

        /**
         *  A 64-bit number (as a hex string) that is randomly generated on the device's first boot
         *  and should remain constant for the lifetime of the device.
         *
         *  (The value may change if a factory reset is performed on the device.)
         *
         *  Constant Value: "android_id"
         */
        SystemAndroidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        TextView text_device_id = (TextView) findViewById(R.id.textview);
        text_device_id.setTextSize(28);

        text_device_id.setText("GSF Device-ID:\n" + GsfAndroidId + "\n\nSystem Device-ID:\n" + SystemAndroidId);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
