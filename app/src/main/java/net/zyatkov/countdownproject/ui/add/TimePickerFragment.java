package net.zyatkov.countdownproject.ui.add;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

//TimePicker to select a time
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    onTimeSelectedListener mListener;

    public interface onTimeSelectedListener{
        void onTimeSelected(String formattedTime);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (onTimeSelectedListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement onTimeSelectedListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), this, hour, minute, true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(0, 0, 0, hourOfDay, minute);

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", new Locale("ru,RU"));
        String formattedTime = timeFormat.format(calendar.getTime());
        mListener.onTimeSelected(formattedTime);
    }
}
