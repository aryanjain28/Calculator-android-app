package com.example.calculator;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView aryansTextView;
    Button b;
    static String Eq = "";
    static int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aryansTextView = findViewById(R.id.display);
    }

    public void buttonPressed(View view) {

        if(flag == 1) {
            aryansTextView.setText("");
            flag  =0;
        }
        aryansTextView.setTextColor(Color.BLACK);
        b = findViewById(view.getId());
        String x = aryansTextView.getText().toString();
        String y = b.getText().toString();

        aryansTextView.setText(x + y);
        Eq = x + y;
    }

    public void ClearAll(View view) {
        aryansTextView.setText("");
    }

    public void printAnswer(View view) {
        try {
            aryansTextView.setText(Double.toString(calculate(Eq)));
        }
        catch (Exception e){
            aryansTextView.setTextColor(Color.WHITE);
            aryansTextView.setText("INVALID");
        }
        flag = 1;
    }

    public static Double calculate(String expression){
        if (expression == null || expression.length() == 0) {
            return null;
        }
        return calc(expression.replace(" ", ""));
    }
    public static Double calc(String expression) {

        if (expression.startsWith("(") && expression.endsWith(")")) {
            return calc(expression.substring(1, expression.length() - 1));
        }
        String[] containerArr = new String[]{expression};
        double leftVal = getNextOperand(containerArr);
        expression = containerArr[0];
        if (expression.length() == 0) {
            return leftVal;
        }
        char operator = expression.charAt(0);
        expression = expression.substring(1);

        while (operator == '*' || operator == '/') {
            containerArr[0] = expression;
            double rightVal = getNextOperand(containerArr);
            expression = containerArr[0];
            if (operator == '*') {
                leftVal = leftVal * rightVal;
            } else {
                leftVal = leftVal / rightVal;
            }
            if (expression.length() > 0) {
                operator = expression.charAt(0);
                expression = expression.substring(1);
            } else {
                return leftVal;
            }
        }
        if (operator == '+') {
            return leftVal + calc(expression);
        } else {
            return leftVal - calc(expression);
        }

    }

    private static double getNextOperand(String[] exp){
        double res;
        if (exp[0].startsWith("(")) {
            int open = 1;
            int i = 1;
            while (open != 0) {
                if (exp[0].charAt(i) == '(') {
                    open++;
                } else if (exp[0].charAt(i) == ')') {
                    open--;
                }
                i++;
            }
            res = calc(exp[0].substring(1, i - 1));
            exp[0] = exp[0].substring(i);
        } else {
            int i = 1;
            if (exp[0].charAt(0) == '-') {
                i++;
            }
            while (exp[0].length() > i && isNumber((int) exp[0].charAt(i))) {
                i++;
            }
            res = Double.parseDouble(exp[0].substring(0, i));
            exp[0] = exp[0].substring(i);
        }
        return res;
    }


    private static boolean isNumber(int c) {
        int zero = (int) '0';
        int nine = (int) '9';
        return (c >= zero && c <= nine) || c =='.';
    }
}
