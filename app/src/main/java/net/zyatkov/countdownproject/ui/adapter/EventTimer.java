package net.zyatkov.countdownproject.ui.adapter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.Date;
import java.util.concurrent.TimeUnit;

// Class countdown time before \ after event
public class EventTimer {

    private TextView timerTextView;
    private Date eventDate;
    private final String BUNDLE_KEY = "EventTimer";


    public EventTimer(TextView timerTextView, Date eventDate) {
        this.timerTextView = timerTextView;
        this.eventDate = eventDate;
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String counter = bundle.getString(BUNDLE_KEY);
            timerTextView.setText(counter);
        }
    };

    final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            Date currentDate = new Date();
            if (currentDate.getTime() < eventDate.getTime()) {
                countdownBeforeEvent();
            } else {
                countdownAfterEvent();
            }
        }
    };

    final Thread mThread = new Thread(mRunnable);

    public void start() {
        mThread.start();
        mHandler.obtainMessage();
    }

    private void countdownBeforeEvent() {
        DateTime eventDateTime = new DateTime(eventDate);
        DateTime currentDateTime = new DateTime();
        while (currentDateTime.getMillis() < eventDateTime.getMillis()) {
            Period period = new Period(currentDateTime, eventDateTime);
            countdownBase(period);
            currentDateTime = new DateTime();
        }
    }

    private void countdownAfterEvent() {
        DateTime eventDateTime = new DateTime(eventDate);
        DateTime currentDateTime = new DateTime();
        while (currentDateTime.getMillis() > eventDateTime.getMillis()) {
            Period period = new Period(eventDateTime, currentDateTime);
            countdownBase(period);
            currentDateTime = new DateTime();
        }
    }

    private void countdownBase(Period period){
        int month = period.getMonths();
        int day = period.getDays();
        int hour = period.getHours();
        int minute = period.getMinutes();
        int second = period.getSeconds();
        String counter =Integer.toString(month) + " mon. " + Integer.toString(day) + " d. " + Integer.toString(hour) + " h. "
                + Integer.toString(minute) + " m. " + Integer.toString(second) + " s.";
        Message msg = mHandler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY, counter);
        msg.setData(bundle);
        mHandler.sendMessage(msg);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
