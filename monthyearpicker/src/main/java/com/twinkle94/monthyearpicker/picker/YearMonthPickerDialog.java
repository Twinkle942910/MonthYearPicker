package com.twinkle94.monthyearpicker.picker;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.twinkle94.monthyearpicker.R;
import com.twinkle94.monthyearpicker.custom_number_picker.NumberPickerWithColor;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Creates a dialog for picking the year and month.
 */

public class YearMonthPickerDialog implements Dialog.OnClickListener {
    /**
     * The minimal year value.
     */
    private static final int MIN_YEAR = 1970;

    /**
     * The maximum year value.
     */
    private static final int MAX_YEAR = 2099;

    /**
     * Array of months.
     */
    private static final String[] MONTHS_LIST = new String[]
            {
                    "January", "February", "March",
                    "April", "May", "June", "July",
                    "August", "September", "October",
                    "November", "December"
            };

    /**
     * Listener for user's date picking.
     */
    private OnDateSetListener mOnDateSetListener;

    /**
     * Application's context.
     */
    private final Context mContext;

    /**
     * The builder for our dialog.
     */
    private AlertDialog.Builder mDialogBuilder;

    /**
     * Resulting dialog.
     */
    private AlertDialog mDialog;

    /**
     * Custom user's theme for dialog.
     */
    private int mTheme;

    /**
     * Custom user's color for title text.
     */
    private int mTextTitleColor;

    /**
     * Picked year.
     */
    private int mYear;

    /**
     * Picked month.
     */
    private int mMonth;

    /**
     * Creates a new YearMonthPickerDialog object that represents the dialog for
     * picking year and month.
     * @param context The application's context.
     * @param onDateSetListener Listener for user's date picking.
     */
    public YearMonthPickerDialog(Context context, OnDateSetListener onDateSetListener) {
        this(context, onDateSetListener, -1, -1);
    }

    /**
     * Creates a new YearMonthPickerDialog object that represents the dialog for
     * picking year and month. Specifies custom user's theme
     * @param context The application's context.
     * @param onDateSetListener Listener for user's date picking.
     * @param theme Custom user's theme for dialog.
     */
    public YearMonthPickerDialog(Context context, OnDateSetListener onDateSetListener, int theme) {
        this(context, onDateSetListener, theme, -1);
    }

    /**
     * Creates a new YearMonthPickerDialog object that represents the dialog for
     * picking year and month. Specifies custom user's theme and title text color
     * @param context The application's context.
     * @param onDateSetListener Listener for user's date picking.
     * @param theme Custom user's theme for dialog.
     * @param titleTextColor Custom user's color for title text.
     */
    public YearMonthPickerDialog(Context context, OnDateSetListener onDateSetListener, int theme, int titleTextColor) {
        mContext = context;
        mOnDateSetListener = onDateSetListener;
        mTheme = theme;
        mTextTitleColor = titleTextColor;

        //Builds the dialog using listed parameters.
        build();
    }

    /**
     * Listens for user's actions.
     * @param dialog Current instance of dialog.
     * @param which Id of pressed button.
     */
    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            //If user presses positive button
            case DialogInterface.BUTTON_POSITIVE:
                //Check if user gave us a listener
                if (mOnDateSetListener != null)
                    //Set picked year and month to the listener
                    mOnDateSetListener.onYearMonthSet(mYear, mMonth);
                break;

            //If user presses negative button
            case DialogInterface.BUTTON_NEGATIVE:
                //Exit the dialog
                dialog.cancel();
                break;
        }
    }

    /**
     * Creates and customizes a dialog.
     */
    private void build() {
        //Applying user's theme
        int currentTheme = mTheme;
        //If there is no custom theme, using default.
        if (currentTheme == -1) currentTheme = R.style.MyDialogTheme;

        //Initializing dialog builder.
        mDialogBuilder = new AlertDialog.Builder(mContext, currentTheme);

        //Creating View inflater.
        final LayoutInflater layoutInflater =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Inflating custom title view.
        final View titleView = layoutInflater.inflate(R.layout.view_dialog_title, null, false);
        //Inflating custom content view.
        final View contentView = layoutInflater.inflate(R.layout.view_month_year_picker, null, false);

        //Initializing year and month pickers.
        final NumberPickerWithColor yearPicker =
                (NumberPickerWithColor) contentView.findViewById(R.id.year_picker);
        final NumberPickerWithColor monthPicker =
                (NumberPickerWithColor) contentView.findViewById(R.id.month_picker);

        //Initializing title text views
        final TextView monthName = (TextView) titleView.findViewById(R.id.month_name);
        final TextView yearValue = (TextView) titleView.findViewById(R.id.year_name);

        //If there is user's title color,
        if(mTextTitleColor != -1)
        {
            //Then apply it.
            setTextColor(monthName);
            setTextColor(yearValue);
        }

        //Setting custom title view and content to dialog.
        mDialogBuilder.setCustomTitle(titleView);
        mDialogBuilder.setView(contentView);

        //Setting year's picker min and max value
        yearPicker.setMinValue(MIN_YEAR);
        yearPicker.setMaxValue(MAX_YEAR);

        //Setting month's picker min and max value
        monthPicker.setMinValue(0);
        monthPicker.setMaxValue(MONTHS_LIST.length - 1);

        //Setting month list.
        monthPicker.setDisplayedValues(MONTHS_LIST);

        //Applying current date.
        setCurrentDate(yearPicker, monthPicker, monthName, yearValue);

        //Setting all listeners.
        setListeners(yearPicker, monthPicker, monthName, yearValue);

        //Setting titles and listeners for dialog buttons.
        mDialogBuilder.setPositiveButton("OK", this);
        mDialogBuilder.setNegativeButton("CANCEL", this);

        //Creating dialog.
        mDialog = mDialogBuilder.create();
    }

    /**
     * Sets color to given TextView.
     * @param titleView Given TextView.
     */
    private void setTextColor(TextView titleView)
    {
        titleView.setTextColor(ContextCompat.getColor(mContext, mTextTitleColor));
    }

    /**
     * Sets current date for title and pickers.
     * @param yearPicker year picker.
     * @param monthPicker month picker.
     * @param monthName month name in the dialog title.
     * @param yearValue year value in the dialog title.
     */
    private void setCurrentDate(NumberPickerWithColor yearPicker, NumberPickerWithColor monthPicker, TextView monthName, TextView yearValue) {
        //Getting current date values from Calendar instance.
        Calendar calendar = Calendar.getInstance();
        mMonth = calendar.get(Calendar.MONTH);
        mYear = calendar.get(Calendar.YEAR);

        //Setting output format.
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");

        //Setting current date values to dialog title views.
        monthName.setText(monthFormat.format(calendar.getTime()));
        yearValue.setText(Integer.toString(mYear));

        //Setting current date values to pickers.
        monthPicker.setValue(mMonth);
        yearPicker.setValue(mYear);
    }

    /**
     * Sets current date for title and pickers.
     * @param yearPicker year picker.
     * @param monthPicker month picker.
     * @param monthName month name in the dialog title.
     * @param yearValue year value in the dialog title.
     */
    private void setListeners(final NumberPickerWithColor yearPicker, final NumberPickerWithColor monthPicker, final TextView monthName, final TextView yearValue) {
        //Setting listener to month name view.
        monthName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If there's no month picker visible
                if (monthPicker.getVisibility() == View.GONE) {
                    //Set it visible
                    monthPicker.setVisibility(View.VISIBLE);

                    //And hide year picker.
                    yearPicker.setVisibility(View.GONE);

                    //Change title views alpha to picking effect.
                    yearValue.setAlpha(0.39f);
                    monthName.setAlpha(1f);
                }
            }
        });

        //Setting listener to year value view.
        yearValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If there's no year picker visible
                if (yearPicker.getVisibility() == View.GONE) {
                    //Set it visible
                    yearPicker.setVisibility(View.VISIBLE);

                    //And hide year picker.
                    monthPicker.setVisibility(View.GONE);

                    //Change title views alpha to picking effect.
                    monthName.setAlpha(0.39f);
                    yearValue.setAlpha(1f);
                }
            }
        });

        //Setting listener to month picker. So it can change title text value.
        monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mMonth = newVal;

                //Set title month text to picked month.
                monthName.setText(MONTHS_LIST[newVal]);
            }
        });

        //Setting listener to year picker. So it can change title text value.
        yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mYear = newVal;

                //Set title year text to picked year.
                yearValue.setText(Integer.toString(newVal));
            }
        });
    }

    /**
     * Allows user to show created dialog.
     */
    public void show() {
        mDialog.show();
    }

    /**
     * Interface for implementing user's pick listener.
     */
    public interface OnDateSetListener {
        /**
         * Listens for user's actions.
         */
        void onYearMonthSet(int year, int month);
    }
}
