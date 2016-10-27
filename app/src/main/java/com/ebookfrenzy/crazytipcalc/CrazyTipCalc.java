package com.ebookfrenzy.crazytipcalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;

public class CrazyTipCalc extends AppCompatActivity {

    private static String TOTAL_BILL = "TOTAL_BILL";
    private static String CURRENT_TIP = "CURRENT_TIP";
    private static String BILL_WITHOUT_TIP = "BILL_WITHOUT_TIP";

    private double billBeforeTip;
    private double tipAmount;
    private double finalBill;

    EditText billBeforeTipET;
    EditText tipAmountET;
    EditText finalBillET;

    private SeekBar tipSeekBar;
    //private SeekBar.OnSeekBarChangeListener tipSeekBarListener;

    //SeekBar tipSeekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crazy_tip_calc);


        tipSeekBar = (SeekBar) findViewById(R.id.changeTipSeekBar);

        tipSeekBar.setOnSeekBarChangeListener(tipSeekBarListener);

        if (savedInstanceState == null) {
            billBeforeTip = 0.0;
            tipAmount = .15;
            finalBill = 0.0;
        } else
        {
            billBeforeTip = savedInstanceState.getDouble(BILL_WITHOUT_TIP);
            tipAmount = savedInstanceState.getDouble(CURRENT_TIP);
            finalBill = savedInstanceState.getDouble(TOTAL_BILL);
        }

        billBeforeTipET = (EditText) findViewById(R.id.billEditText);
        tipAmountET = (EditText) findViewById(R.id.tipEditText);

        finalBillET = (EditText) findViewById(R.id.finalBillEditText);


        billBeforeTipET.addTextChangedListener(billBeforeTipListener);

    }

    private TextWatcher billBeforeTipListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                billBeforeTip = Double.parseDouble(s.toString());
            } catch (NumberFormatException e) {
                billBeforeTip = 0.0;
            }

            updateTipAndFinalBill();

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

        private void updateTipAndFinalBill() {
            double tipAmount = Double.parseDouble(tipAmountET.getText().toString());

            double finalBill = billBeforeTip + (billBeforeTip * tipAmount);

            finalBillET.setText(String.format("%.02f", finalBill));
        }

        protected void onSaveInstanceState(Bundle outState) {
            CrazyTipCalc.super.onSaveInstanceState(outState);
            outState.putDouble(TOTAL_BILL, finalBill);
            outState.putDouble(CURRENT_TIP, tipAmount);
            outState.putDouble(BILL_WITHOUT_TIP, billBeforeTip);
        }


        private SeekBar.OnSeekBarChangeListener tipSeekBarListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                tipAmount = (tipSeekBar.getProgress()) * .01;
                tipAmountET.setText(String.format("%.02f", tipAmount));
                updateTipAndFinalBill();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };

    }



