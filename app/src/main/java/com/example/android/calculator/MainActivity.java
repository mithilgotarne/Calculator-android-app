package com.example.android.calculator;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

    double value = 0;
    String expression = "0";
    boolean flag = false;
    int count = 0;  // count for division of 10 for double nos.
    int minusCount = 0; //counting minus sign;
    ArrayList<String> equation = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        equation.add("0");
        equation.add("+");
    }

    public boolean errorhandle() {
        return !expression.equals("0") &&
                !expression.endsWith("-")&&
                !expression.endsWith("+") &&
                !expression.endsWith("/") &&
                !expression.endsWith("*") ;
    }

    public void AC(){
        TextView maintext = (TextView) findViewById(R.id.main_text);
        TextView anstext = (TextView) findViewById(R.id.ans_textView);
        expression = "0";
        equation.clear();
        value = 0;
        flag = false;
        count = 0;
        minusCount = 0;
        maintext.setText(expression);
        anstext.setText(expression);
        equation.add("0");
        equation.add("+");
    }

    public void allClear(View v) {
        AC();
    }

    public void clear(View v) {
        if (expression.endsWith("-") && minusCount > 0) {
            expression = expression.substring(0, expression.length() - 1);
            TextView maintext = (TextView) findViewById(R.id.main_text);
            maintext.setText(expression);
        } else {
            if (equation.get(equation.size() - 1).length() > 1) {

                if (equation.get(equation.size() - 1).substring(equation.get(equation.size() - 1).length() - 2).equals(".0"))
                    equation.set(equation.size() - 1, equation.get(equation.size() - 1).substring(0, equation.get(equation.size() - 1).length() - 2));
            }
            if (equation.get(equation.size() - 1).length() == 1)
                equation.remove(equation.size() - 1);
            if (expression.length() > 1) {
                if (expression.endsWith("+") ||
                        expression.endsWith("-") ||
                        expression.endsWith("*") ||
                        expression.endsWith("/")) {
                    expression = expression.substring(0, expression.length() - 1);
                    equation.remove(equation.size() - 1);
                } else {
                    expression = expression.substring(0, expression.length() - 1);
                    String last = equation.get(equation.size() - 1);
                    last = last.substring(0, last.length() - 1);
                    equation.set(equation.size() - 1, last);
                }
                TextView maintext = (TextView) findViewById(R.id.main_text);
                maintext.setText(expression);
            } else {
               AC();
            }
        }
    }


    public void operand(View v) {
        if (minusCount % 2 == 1) {
            value = -value;
            equation.set(equation.size() - 1, String.valueOf(value));
            minusCount = 0;
        }
        solve();
        TextView maintext = (TextView) findViewById(R.id.main_text);
        if (errorhandle()) {
            switch (v.getId()) {
                case R.id.plus_button:
                    expression = expression + "+";
                    equation.add("+");
                    break;
                case R.id.divide_button:
                    expression = expression + "/";
                    equation.add("/");
                    break;
                case R.id.multiply_button:
                    expression = expression + "*";
                    equation.add("*");
                    break;
            }
            value = 0;
            flag = false;
            count = 0;
            minusCount = 0;
        }
        maintext.setText(expression);
    }

    public void minus(View v) {
        TextView maintext = (TextView) findViewById(R.id.main_text);
        if (expression.equals("0")) {
            expression = "-";
            minusCount++;
        } else if (expression.endsWith("+") || expression.endsWith("*") || expression.endsWith("/") || expression.endsWith("-")) {
            expression = expression + "-";
            minusCount++;
        } else {
            expression = expression + "-";
            equation.add("-");
        }
        maintext.setText(expression);
    }

    public void num(View v) {
        TextView maintext = (TextView) findViewById(R.id.main_text);
        int n = 0;
        if (v.equals(findViewById(R.id.one_button)))
            n = 1;
        if (v.equals(findViewById(R.id.two_button)))
            n = 2;
        if (v.equals(findViewById(R.id.three_button)))
            n = 3;
        if (v.equals(findViewById(R.id.four_button)))
            n = 4;
        if (v.equals(findViewById(R.id.five_button)))
            n = 5;
        if (v.equals(findViewById(R.id.six_button)))
            n = 6;
        if (v.equals(findViewById(R.id.seven_button)))
            n = 7;
        if (v.equals(findViewById(R.id.eigth_button)))
            n = 8;
        if (v.equals(findViewById(R.id.nine_button)))
            n = 9;
        if (v.equals(findViewById(R.id.zero_button)))
            n = 0;
        if (expression.equals("0")) {
            expression = String.valueOf(n);
            if (n != 0)
                equation.add(String.valueOf(n));
            value = n;
        } else {
            if (expression.endsWith("+") ||
                    expression.endsWith("-") ||
                    expression.endsWith("/") ||
                    expression.endsWith("*")) {
                value = n;
                equation.add(String.valueOf(value));
            } else {
                if (flag) {
                    value = value + n / Math.pow(10, count);
                    count++;
                } else value = value * 10 + n;
                if (equation.size() != 0)
                    equation.set(equation.size() - 1, String.valueOf(value));
                else equation.add(String.valueOf(value));
            }
            expression = expression + n;
        }
        maintext.setMovementMethod(new ScrollingMovementMethod());
        maintext.setText(expression);
    }

    public void point(View v) {

        if (!flag) {
            expression = expression + ".";
            flag = true;
            count++;
            equation.add("0");
        }
        TextView maintext = (TextView) findViewById(R.id.main_text);
        maintext.setText(expression);
    }

    public void equals(View v) {
        solve();
    }

    public void solve() {
        if (minusCount % 2 == 1) {
            value = -value;
            this.equation.set(this.equation.size() - 1, String.valueOf(value));
            minusCount=0;
        }
        ArrayList<String> equation = (ArrayList<String>) this.equation.clone();
        if (expression.endsWith("+") ||
                expression.endsWith("-") ||
                expression.endsWith("/") ||
                expression.endsWith("*")||
                expression.equals("0")) {
            equation.remove(equation.size() - 1);
        }
        TextView anstext = (TextView) findViewById(R.id.ans_textView);
        double ans = 0;
        int i = 0;
        while (i < equation.size()) {
            if (equation.get(i).equals("*") || equation.get(i).equals("/")) {
                if (equation.get(i).equals("*"))
                    ans = Double.parseDouble(equation.get(i - 1))
                            * Double.parseDouble(equation.get(i + 1));
                else
                    ans = Double.parseDouble(equation.get(i - 1))
                            / Double.parseDouble(equation.get(i + 1));

                equation.set(i + 1, String.valueOf(ans));
                equation.remove(i);
                equation.remove(i - 1);
            } else i++;
        }
        i = 0;
        while (i < equation.size()) {
            if (equation.get(i).equals("+") || equation.get(i).equals("-")) {
                if (equation.get(i).equals("+"))
                    ans = Double.parseDouble(equation.get(i - 1))
                            + Double.parseDouble(equation.get(i + 1));
                else
                    ans = Double.parseDouble(equation.get(i - 1))
                            - Double.parseDouble(equation.get(i + 1));

                equation.set(i + 1, String.valueOf(ans));
                equation.remove(i);
                equation.remove(i - 1);
            } else i++;
        }
        anstext.setText(equation.get(0));

    }

}
