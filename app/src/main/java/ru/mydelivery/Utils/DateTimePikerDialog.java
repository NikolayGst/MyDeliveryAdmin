package ru.mydelivery.Utils;

import android.app.Activity;
import android.widget.EditText;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class DateTimePikerDialog implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private Activity mActivity;
    private EditText mEditText;
    private int mHour;
    private int mMinute;
    private int mMonth;
    private String mTime;
    private String mDateAndTime;

    public DateTimePikerDialog(Activity mActivity, EditText mEditText) {
        this.mActivity = mActivity;
        this.mEditText = mEditText;
    }

    public void showData() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show(mActivity.getFragmentManager(), "Datepickerdialog");
    }

    public void showTime() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        timePickerDialog.show(mActivity.getFragmentManager(), "Timepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        mMonth = monthOfYear + 1;
        if (mMonth < 10 && dayOfMonth < 10) {
            mDateAndTime = "0" + dayOfMonth + "." + "0" + mMonth + "." + year;
        } else if (mMonth < 10) {
            mDateAndTime = dayOfMonth + "." + "0" + mMonth + "." + year;
        } else if (dayOfMonth < 10) {
            mDateAndTime = "0" + dayOfMonth + "." + mMonth + "." + year;
        } else {
            mDateAndTime = dayOfMonth + "." + mMonth + "." + year;
        }
        showTime();
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        mHour = hourOfDay;
        mMinute = minute;
        if (minute < 10) {
            mTime = " | " + hourOfDay + ":" + "0" + minute;
        } else {
            mTime = " | " + hourOfDay + ":" + minute;
        }
        mDateAndTime += mTime;
        mEditText.setText(mDateAndTime);
    }
}
