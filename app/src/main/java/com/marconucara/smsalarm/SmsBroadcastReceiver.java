package com.marconucara.smsalarm;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class SmsBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsBroadcastReceiver";

    private SharedPreferences mSharedPreferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                String smsBody = smsMessage.getMessageBody();
                String smsSender = smsMessage.getOriginatingAddress();

                mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                String mUserMobilePhone = mSharedPreferences.getString(MainActivity.PREF_USER_MOBILE_PHONE, "");

                if (smsSender.contains(mUserMobilePhone)) {
                    context.startService(new Intent(context.getApplicationContext(), BackgroundSoundService.class));

                    Intent i = new Intent();
                    i.setClassName("com.marconucara.smsalarm", "com.marconucara.smsalarm.MainActivity");
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);

                    Toast.makeText(context, "SMS: " + smsBody + ", from: " + smsSender, Toast.LENGTH_LONG).show();

                }
            }
        }
    }
}
