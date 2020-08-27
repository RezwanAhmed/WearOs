package com.example.simplecalculator;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends WearableActivity {

    private TextView mTextView;
    private int[] numericButtons = {R.id.btnZero, R.id.btnOne, R.id.btnTwo, R.id.btnThree, R.id.btnFour, R.id.btnFive, R.id.btnSix, R.id.btnSeven, R.id.btnEight, R.id.btnNine};
    private int[] operatorButtons = {R.id.btnPlus, R.id.btnMinus, R.id.btnProduct, R.id.btnDivide};
    private TextView text_view_left;
    private TextView text_view_right;
    private boolean lastNumeric;
    private boolean stateError;
    private boolean lastDot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.text_view_left = (TextView) findViewById(R.id.text_view_left);
        this.text_view_right = (TextView) findViewById(R.id.text_view_right);
        setNumericOnClickListener();
        setOperatorOnClickListener();

        // Enables Always-on
        setAmbientEnabled();
    }
    private void setNumericOnClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if (stateError) {
                    text_view_left.setText(button.getText());
                    stateError = false;
                } else {
                    text_view_left.append(button.getText());
                }
                lastNumeric = true;
            }
        };
        for (int id : numericButtons) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setOperatorOnClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastNumeric && !stateError) {
                    Button button = (Button) v;
                    text_view_left.append(button.getText());
                    lastNumeric = false;
                    lastDot = false;
                }
            }
        };
        for (int id : operatorButtons) {
            findViewById(id).setOnClickListener(listener);
        }
        findViewById(R.id.btnDot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastNumeric && !stateError && !lastDot) {
                    text_view_left.append(".");
                    lastNumeric = false;
                    lastDot = true;
                }
            }
        });
        findViewById(R.id.btnClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_view_left.setText("");
                text_view_right.setText("");
                lastNumeric = false;
                stateError = false;
                lastDot = false;
            }
        });
        findViewById(R.id.btnEqual).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEqual();
            }
        });
    }
    private void onEqual() {
        if (lastNumeric && !stateError) {
            String txt = text_view_left.getText().toString();
            Expression expression = new ExpressionBuilder(txt).build();
            try {
                double result = expression.evaluate();
                text_view_right.setText(Double.toString(result));
                lastDot = true;
            } catch (ArithmeticException ex) {
                text_view_right.setText("Error");
                stateError = true;
                lastNumeric = false;
            }
        }
    }
}

