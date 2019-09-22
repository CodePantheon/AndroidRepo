package org.codepantheon.takenotes.view;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageReceiver extends BroadcastReceiver {

    private String billDefaultTitle = "Bill";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("MessageReceiver ", "called");

        Bundle bundle = intent.getExtras();
        if (bundle == null)
            return;

        SmsMessage[] messageParts = null;
        String messageFrom = "";
        StringBuilder message = new StringBuilder();
        Object[] pdus = (Object[]) bundle.get("pdus");
        messageParts = new SmsMessage[pdus.length];
        messageFrom = SmsMessage.createFromPdu((byte[]) pdus[0]).getOriginatingAddress();
        for (int i = 0; i < messageParts.length; i++) {
            messageParts[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
            message.append(messageParts[i].getMessageBody());
        }

        Toast.makeText(context, messageFrom + ": " + message.toString(), Toast.LENGTH_LONG).show();
        if (context.checkSelfPermission(Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            Toast.makeText(context, "No permission", Toast.LENGTH_LONG).show();
            return;
        }

        boolean isBillNotification = message.toString().toLowerCase().contains("bill") &&
                message.toString().toLowerCase().contains("due");

        if (!isBillNotification) {
            Log.d("MessageReceiver: ", "Not a bill notification");
            return;
        }

        BillDetails billDetails = null;
        try {
            billDetails = this.getBillDetails(message.toString());
        } catch (ParseException e) {
            Log.d("MessageReceiver: ", "bill details cannot be parsed");
            return;
        }

        if (billDetails == null) {
            return;
        }

        ContentResolver cr = context.getContentResolver();
        cr.insert(CalendarContract.Events.CONTENT_URI, this.createCalendarEventContentValue(billDetails));
        Log.d("MessageReceiver: ", "Bill event added.");
    }

    private BillDetails getBillDetails(String message) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Pattern datePattern = Pattern.compile("\\d\\d-\\d\\d-\\d\\d\\d\\d");

        Matcher m = datePattern.matcher(message);
        Date billDate = null;
        if (m.find()) {
            billDate = format.parse(m.group());
            Pattern billPattern = Pattern.compile("(\\w+)\\W+bill\\W+(\\w+)");
            Matcher billMatcher = billPattern.matcher(message);
            String title = billMatcher.find() ? billMatcher.group(1) : this.billDefaultTitle;
            String description = message;
            return new BillDetails(title, description, billDate);
        }
        return null;
    }

    private ContentValues createCalendarEventContentValue(BillDetails billDetails) {
        Log.d("MessageReceiver: ", "createCalendarEventContentValue called");
        if (billDetails == null) {
            return null;
        }

        ContentValues cv = new ContentValues();

        cv.put(CalendarContract.Events.TITLE, billDetails.title);
        cv.put(CalendarContract.Events.DESCRIPTION, billDetails.description);
        cv.put(CalendarContract.Events.DTSTART, billDetails.dueDate.getTime());
        cv.put(CalendarContract.Events.DTEND, billDetails.dueDate.getTime());
        cv.put(CalendarContract.Events.CALENDAR_ID, 1);
        cv.put(CalendarContract.Events.EVENT_TIMEZONE, Calendar.getInstance().getTimeZone().getID());

        Log.d("MessageReceiver: ", "createCalendarEventContentValue end");
        return cv;
    }

    static class BillDetails {
        private String title;
        private String description;
        private Date dueDate;

        private BillDetails(String title, String description, Date dueDate) {
            this.title = title;
            this.description = description;
            this.dueDate = dueDate;
        }
    }
}
