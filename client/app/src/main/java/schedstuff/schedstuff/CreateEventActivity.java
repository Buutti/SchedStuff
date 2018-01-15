package schedstuff.schedstuff;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.sql.Time;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateEventActivity extends AppCompatActivity {

    EditText deadlineEditText, beginAfterEditText, beginBeforeEditText;

    final static int BEGIN_AFTER = 0;
    final static int BEGIN_BEFORE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Code for setting dates is from url https://stackoverflow.com/questions/11360915/how-to-set-date-in-edit-text
        deadlineEditText = (EditText) findViewById(R.id.deadlineEditText);
        deadlineEditText.setOnClickListener(deadlineOnClickListener);

        beginAfterEditText = (EditText) findViewById(R.id.beginAfterEditText);
        beginAfterEditText.setOnClickListener(beginAfterOnClickListener);

        beginBeforeEditText = (EditText) findViewById(R.id.beginBeforeEditText);
        beginBeforeEditText.setOnClickListener(beginBeforeOnClicListener);
    }

    View.OnClickListener deadlineOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DialogFragment dialogFragment = new DatePickerFragment();

            dialogFragment.show(getFragmentManager(), "Date Picker");
        }
    };

    View.OnClickListener beginAfterOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DialogFragment dialogFragment = new TimePickerFragment();

            Bundle args = new Bundle();
            args.putInt("field", BEGIN_AFTER);
            dialogFragment.setArguments(args);

            dialogFragment.show(getFragmentManager(), "Time Picker");
        }
    };

    View.OnClickListener beginBeforeOnClicListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DialogFragment dialogFragment = new TimePickerFragment();

            Bundle args = new Bundle();
            args.putInt("field", BEGIN_BEFORE);
            dialogFragment.setArguments(args);

            dialogFragment.show(getFragmentManager(), "Time Picker");
        }
    };

    /*
        Code for date and time pickers from url
         https://android--code.blogspot.fi/2015/08/android-datepickerdialog-example.html
     */

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar mCalendar = Calendar.getInstance();
            int year = mCalendar.get(Calendar.YEAR);
            int month = mCalendar.get(Calendar.MONTH);
            int day = mCalendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog mDPickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
            return mDPickerDialog;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            Calendar mCalendar = Calendar.getInstance();
            mCalendar.setTimeInMillis(0);
            mCalendar.set(year, month, dayOfMonth, 0, 0, 0);
            Date chosenDate = mCalendar.getTime();

            //Format and display the date
            DateFormat dateFormat = DateFormat.getDateInstance();
            String formattedDate = dateFormat.format(chosenDate);

            EditText deadlineText = (EditText) getActivity().findViewById(R.id.deadlineEditText);
            deadlineText.setText(formattedDate);

        }
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
             final Calendar mCalendar = Calendar.getInstance();
             int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
             int minute = mCalendar.get(Calendar.MINUTE);

             return new TimePickerDialog(getActivity(), this, hour, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));

        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Calendar mCalendar = Calendar.getInstance();
            mCalendar.setTimeInMillis(0);
            mCalendar.set(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH),
                            hourOfDay, minute, 0);
            Date chosenTime = mCalendar.getTime();

            DateFormat dateFormat = DateFormat.getTimeInstance();
            String formattedTime = dateFormat.format(chosenTime);

            EditText editText;
            int field = getArguments().getInt("field");
            switch (field) {
                case BEGIN_AFTER:
                    editText = (EditText) getActivity().findViewById(R.id.beginAfterEditText);
                    editText.setText(formattedTime);
                    break;
                case BEGIN_BEFORE:
                    editText = (EditText) getActivity().findViewById(R.id.beginBeforeEditText);
                    editText.setText(formattedTime);
                    break;
            }


        }
    }


}
