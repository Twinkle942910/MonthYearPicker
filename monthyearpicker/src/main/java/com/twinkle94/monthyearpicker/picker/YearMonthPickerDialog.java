package com.twinkle94.monthyearpicker.picker;

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
 * Created by Twinkle on 6/14/2017.
 */

public class YearMonthPickerDialog implements Dialog.OnClickListener {
    private static final int MIN_YEAR = 1970;
    private static final int MAX_YEAR = 2099;

    private static final String[] MONTHS_LIST = new String[]
            {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    private OnDateSetListener mOnDateSetListener;

    private final Context mContext;
    private AlertDialog.Builder mDialogBuilder;
    private AlertDialog mDialog;
    private int mTheme;
    private int mTextTitleColor;

    private int mYear;
    private int mMonth;


    public YearMonthPickerDialog(Context context, OnDateSetListener onDateSetListener) {
        mContext = context;
        mOnDateSetListener = onDateSetListener;
        mTheme = -1;
        mTextTitleColor = -1;

        build();
    }

    public YearMonthPickerDialog(Context context, OnDateSetListener onDateSetListener, int theme) {
        this(context, onDateSetListener);
        mTheme = theme;
    }

    public YearMonthPickerDialog(Context context, OnDateSetListener onDateSetListener, int theme, int titleTextColor) {
       // this(context, onDateSetListener, theme);
        //TODO: improve this, avoid repeating code.
        mContext = context;
        mOnDateSetListener = onDateSetListener;
        if(theme <= 0) mTheme = -1;
        else mTheme = theme;
        mTextTitleColor = titleTextColor;

        build();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                if (mOnDateSetListener != null)
                    mOnDateSetListener.onYearMonthSet(mYear, mMonth);
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                dialog.cancel();
                break;
        }
    }

    public void setTextTitleColor(int color)
    {
        mTextTitleColor = color;
    }

    private void build() {
        int currentTheme = mTheme;
        if (currentTheme == -1) currentTheme = R.style.MyDialogTheme;

        mDialogBuilder = new AlertDialog.Builder(mContext, currentTheme);

        final LayoutInflater layoutInflater =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View titleView = layoutInflater.inflate(R.layout.view_dialog_title, null, false);
        final View contentView = layoutInflater.inflate(R.layout.view_month_year_picker, null, false);

        mDialogBuilder.setCustomTitle(titleView);
        mDialogBuilder.setView(contentView);

        final NumberPickerWithColor yearPicker =
                (NumberPickerWithColor) contentView.findViewById(R.id.year_picker);
        final NumberPickerWithColor monthPicker =
                (NumberPickerWithColor) contentView.findViewById(R.id.month_picker);

        yearPicker.setMinValue(MIN_YEAR);
        yearPicker.setMaxValue(MAX_YEAR);

        monthPicker.setMinValue(0);
        monthPicker.setMaxValue(MONTHS_LIST.length - 1);
        monthPicker.setDisplayedValues(MONTHS_LIST);


        final TextView monthName = (TextView) titleView.findViewById(R.id.month_name);
        final TextView yearName = (TextView) titleView.findViewById(R.id.year_name);

        if(mTextTitleColor != -1)
        {
            monthName.setTextColor(mTextTitleColor);
            yearName.setTextColor(mTextTitleColor);
        }

        Calendar calendar = Calendar.getInstance();
        mMonth = calendar.get(Calendar.MONTH);
        mYear = calendar.get(Calendar.YEAR);
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");

        monthName.setText(monthFormat.format(calendar.getTime()));
        yearName.setText(Integer.toString(mYear));

        monthPicker.setValue(mMonth);
        yearPicker.setValue(mYear);

        monthName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monthPicker.getVisibility() == View.GONE) {
                    monthPicker.setVisibility(View.VISIBLE);
                    yearPicker.setVisibility(View.GONE);

                    /*monthName.setTextColor(ContextCompat.getColor(mContext, R.color.titleTextColor1));
                    yearName.setTextColor(ContextCompat.getColor(mContext, R.color.titleTextColor2));*/

                    yearName.setAlpha(0.39f);
                    monthName.setAlpha(1f);
                }
            }
        });

        yearName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yearPicker.getVisibility() == View.GONE) {
                    yearPicker.setVisibility(View.VISIBLE);
                    monthPicker.setVisibility(View.GONE);

                    /*monthName.setTextColor(ContextCompat.getColor(mContext, R.color.titleTextColor2));
                    yearName.setTextColor(ContextCompat.getColor(mContext, R.color.titleTextColor1));*/
                    monthName.setAlpha(0.39f);
                    yearName.setAlpha(1f);
                }
            }
        });

        monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mMonth = newVal;
                monthName.setText(MONTHS_LIST[mMonth]);
            }
        });

        yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mYear = newVal;
                yearName.setText(Integer.toString(mYear));
            }
        });

        mDialogBuilder.setPositiveButton("OK", this);
        mDialogBuilder.setNegativeButton("CANCEL", this);

        mDialog = mDialogBuilder.create();
    }

    public void show() {
        mDialog.show();
    }

    public interface OnDateSetListener {
        void onYearMonthSet(int year, int month);
    }
}
