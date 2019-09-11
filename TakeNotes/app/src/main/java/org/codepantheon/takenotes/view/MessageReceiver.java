package org.codepantheon.takenotes.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class MessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("MessageReceiver ", "called");

        Bundle bundle = intent.getExtras();
        SmsMessage[] messageParts = null;
        String messageFrom = "";
        StringBuilder message = new StringBuilder();
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            messageParts = new SmsMessage[pdus.length];
            messageFrom = SmsMessage.createFromPdu((byte[]) pdus[0]).getOriginatingAddress();
            for (int i = 0; i < messageParts.length; i++) {
                messageParts[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                message.append(messageParts[i].getMessageBody());
            }
        }

        Toast.makeText(context, messageFrom + ": " + message.toString(), Toast.LENGTH_LONG).show();
    }
}
