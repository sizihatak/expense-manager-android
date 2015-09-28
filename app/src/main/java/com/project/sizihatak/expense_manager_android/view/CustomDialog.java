package com.project.sizihatak.expense_manager_android.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.project.sizihatak.expense_manager_android.R;

public class CustomDialog extends Dialog implements
        View.OnClickListener {

    private boolean isInteger = true;
    Context mContext;

    private int[] IDs = {R.id.dialog_ok,
            R.id.dialog_cancel,
            R.id.dialog_one,
            R.id.dialog_two,
            R.id.dialog_three,
            R.id.dialog_four,
            R.id.dialog_five,
            R.id.dialog_six,
            R.id.dialog_seven,
            R.id.dialog_eight,
            R.id.dialog_nine,
            R.id.dialog_zero,
            R.id.dialog_comma,
            R.id.dialog_erase_rl};

    private TextView tvTotalCost;

    private String mCurrencyUnit = " грн.";
    private boolean isStop = false;

    private int mAddedIndexDecimal = 0;
    private int mEraseIndexDecimal = -1;

    public CustomDialog(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog);
        initViews();
    }

    private void initViews() {
        for (int id : IDs)
            findViewById(id).setOnClickListener(this);
        tvTotalCost = (TextView) findViewById(R.id.dialog_integer);
        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                tvTotalCost.setWidth((int) (getWindow().getDecorView().getWidth() -
                        findViewById(R.id.dialog_erase_iv).getMeasuredWidth() -
                        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, mContext.getResources().getDisplayMetrics())));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        tvTotalCost.getMeasuredWidth();
    }

    @Override
    public void onClick(View v) {
        String currentCost;
        switch (v.getId()) {
            case R.id.dialog_ok:
            case R.id.dialog_cancel:
                this.cancel();
                break;
            case R.id.dialog_erase_rl:
                currentCost = tvTotalCost.getText().toString();
                tvTotalCost.setText(eraseCost(currentCost, isInteger));
                break;
            case R.id.dialog_comma:
                isInteger = false;
                break;
            default:
                currentCost = tvTotalCost.getText().toString();
                tvTotalCost.setText(setCost(v.getTag().toString(), currentCost, isInteger));
                break;
        }
    }

    public String setCost(String num, String currentCost, boolean isInteger) {
        StringBuilder integerString = new StringBuilder("");
        StringBuilder decimalString = new StringBuilder("");
        separateIntegerAndDecimal(currentCost, integerString, decimalString);
        if (isInteger) {
            if (currentCost.length() < 19) {
                if (integerString.length() == 1 && integerString.charAt(0) == '0')
                    integerString.setCharAt(0, num.charAt(0));
                else
                    integerString.append(num);
            } else {
                return currentCost;
            }
        } else {
            if (mAddedIndexDecimal == 0) {
                decimalString.setCharAt(0, num.charAt(0));
                mAddedIndexDecimal++;
                mEraseIndexDecimal = 0;
            } else {
                if (!isStop) {
                    decimalString.setCharAt(1, num.charAt(0));
                    isStop = true;
                    mEraseIndexDecimal = 1;
                } else
                    return currentCost;
            }
        }
        integerString = addSpaces(integerString);
        return integerString.toString() + "," + decimalString.toString() + mCurrencyUnit;
    }

    public String eraseCost(String currentCost, boolean isInteger) {
        StringBuilder integerString = new StringBuilder("");
        StringBuilder decimalString = new StringBuilder("");
        separateIntegerAndDecimal(currentCost, integerString, decimalString);
        if (isInteger) {
            if (integerString.length() == 1)
                integerString.setCharAt(0, '0');
            else {
                integerString.deleteCharAt(integerString.length() - 1);
            }
        } else {
            isStop = false;
            if (mEraseIndexDecimal == -1) {
                this.isInteger = true;
                return eraseCost(currentCost, true);
            } else if (mEraseIndexDecimal == 0) {
                decimalString.setCharAt(0, '0');
                mEraseIndexDecimal--;
                mAddedIndexDecimal = 0;
            } else {
                decimalString.setCharAt(1, '0');
                mEraseIndexDecimal--;
                mAddedIndexDecimal = 1;
            }
        }
        integerString = addSpaces(integerString);
        return integerString.toString() + "," + decimalString.toString() + mCurrencyUnit;
    }

    public void separateIntegerAndDecimal(String currentCost, StringBuilder integerString, StringBuilder decimalString) {
        int indexComa = currentCost.indexOf(",");
        integerString.append(currentCost.substring(0, indexComa));
        deleteSpaces(integerString);
        decimalString.append(currentCost.substring(indexComa + 1));
        decimalString.delete(decimalString.length() - 5, decimalString.length());
    }

    public void deleteSpaces(StringBuilder integerString) {
        for (int index = 0; index < integerString.length(); index++) {
            if (integerString.charAt(index) == ' ')
                integerString.deleteCharAt(index);
        }
    }

    public StringBuilder addSpaces(StringBuilder integerCost) {
        if (integerCost.length() == 4) {
            integerCost = new StringBuilder(integerCost.substring(0, 1) + ' ' + integerCost.substring(1, integerCost.length()));
        } else if (integerCost.length() == 5) {
            integerCost = new StringBuilder(integerCost.substring(0, 2) + ' ' + integerCost.substring(2, integerCost.length()));
        } else if (integerCost.length() == 6) {
            integerCost = new StringBuilder(integerCost.substring(0, 3) + ' ' + integerCost.substring(3, integerCost.length()));
        } else if (integerCost.length() == 7) {
            integerCost = new StringBuilder(integerCost.substring(0, 1) + ' ' + integerCost.substring(1, integerCost.length()));
            integerCost = new StringBuilder(integerCost.substring(0, 5) + ' ' + integerCost.substring(5, integerCost.length()));
        } else if (integerCost.length() == 8) {
            integerCost = new StringBuilder(integerCost.substring(0, 2) + ' ' + integerCost.substring(2, integerCost.length()));
            integerCost = new StringBuilder(integerCost.substring(0, 6) + ' ' + integerCost.substring(6, integerCost.length()));
        } else if (integerCost.length() == 9) {
            integerCost = new StringBuilder(integerCost.substring(0, 3) + ' ' + integerCost.substring(3, integerCost.length()));
            integerCost = new StringBuilder(integerCost.substring(0, 7) + ' ' + integerCost.substring(7, integerCost.length()));
        }
        return integerCost;
    }
}