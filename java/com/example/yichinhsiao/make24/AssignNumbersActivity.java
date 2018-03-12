package com.example.yichinhsiao.make24;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

public class AssignNumbersActivity extends AppCompatActivity {

    private TextView numberView1, numberView2, numberView3, numberView4;
    private Button btnClear, btnDone;
    private NumberPicker numberPicker1, numberPicker2, numberPicker3, numberPicker4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_numbers);

        numberView1 = (TextView) findViewById(R.id.numberview1);
        numberView2 = (TextView) findViewById(R.id.numberview2);
        numberView3 = (TextView) findViewById(R.id.numberview3);
        numberView4 = (TextView) findViewById(R.id.numberview4);

        btnClear = (Button) findViewById(R.id.npClear);
        btnDone = (Button) findViewById(R.id.npDone);

        numberPicker1 = (NumberPicker) findViewById(R.id.numberpicker1);
        numberPicker2 = (NumberPicker) findViewById(R.id.numberpicker2);
        numberPicker3 = (NumberPicker) findViewById(R.id.numberpicker3);
        numberPicker4 = (NumberPicker) findViewById(R.id.numberpicker4);

        getNumber(numberPicker1, numberView1);
        getNumber(numberPicker2, numberView2);
        getNumber(numberPicker3, numberView3);
        getNumber(numberPicker4, numberView4);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetNP();
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssignNumbersActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("number1", Integer.parseInt(numberView1.getText().toString()));
                intent.putExtra("number2", Integer.parseInt(numberView2.getText().toString()));
                intent.putExtra("number3", Integer.parseInt(numberView3.getText().toString()));
                intent.putExtra("number4", Integer.parseInt(numberView4.getText().toString()));
                startActivity(intent);
            }
        });
    }

    private void getNumber(NumberPicker numberPicker, final TextView view) {
        numberPicker.setDisplayedValues(null);
        numberPicker.setMaxValue(9);
        numberPicker.setMinValue(1);
        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                String result = String.valueOf(newVal);
                view.setText(result);
            }
        });
    }

    private void resetNP() {
        String default1 = "1";
        numberPicker1.setValue(1);
        numberView1.setText(default1);
        numberPicker2.setValue(1);
        numberView2.setText(default1);
        numberPicker3.setValue(1);
        numberView3.setText(default1);
        numberPicker4.setValue(1);
        numberView4.setText(default1);
    }
}