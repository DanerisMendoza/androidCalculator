package com.danetech.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //global variables
    String operatorButton = "",firstValueString = "";
    boolean operatorButtonPerformed = false;
    double result = 0;
    TextView tvFirstInput,tvDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvDisplay = (TextView) findViewById(R.id.tvDisplay);
        tvFirstInput = (TextView) findViewById(R.id.tvFirstInput);
    }

    //1st method *add Number to tvDisplay
    public void numberMethod(View buttonPressed) {
        if(tvDisplay.getText().toString().equals("0") || operatorButtonPerformed)
            tvDisplay.setText("");	//make display zero if finish calculating first and second input
        Button buttonLocal = (Button)buttonPressed;
        if(buttonLocal.getText().toString().equals(".") && tvDisplay.getText().toString().contains("."))
            return;		//return if two or more decimal pressed by user
        tvDisplay.setText(tvDisplay.getText().toString() + buttonLocal.getText().toString());
        operatorButtonPerformed = false;
    }

    //2nd method *operator button to use
    public void operatorMethod(View buttonPressed){
        if(operatorButtonPerformed || tvDisplay.getText().toString().equals(".")) return; //return when operator is already pressed to avoid force exit
        Button buttonLocal = (Button)buttonPressed;
        operatorButton = buttonLocal.getText().toString();
        result = Double.parseDouble(tvDisplay.getText().toString());
        operatorButtonPerformed = true;
        tvDisplay.setText("0");
        //round off if number has no decimal and concatenate the operator that been used
        tvFirstInput.setText((result%1 == 0)? (int) Math.round(result) +" "+ operatorButton: result +" "+ operatorButton);
        firstValueString = ((result%1 == 0)? String.valueOf((int) Math.round(result)): String.valueOf(result));
    }

    //3rd method *Clear all, backSpace and clear equation
    public void clearMethod(View buttonPressed) {
        switch (buttonPressed.getId()) {
            //AC
            case R.id.btnAC:
                tvDisplay.setText("0");
                tvFirstInput.setText("");
                result=0;
                break;
            //CE
            case R.id.btnCE:
                tvDisplay.setText("0");
                break;
            //BACKSPACE
            case R.id.btnBackSpace:
                if(tvDisplay.getText().toString().equals("0") || tvDisplay.getText().toString().equals(""))
                    return;
                StringBuffer sb = new StringBuffer(tvDisplay.getText().toString());
                sb.deleteCharAt(sb.length()-1);
                if(sb.length()==0) {
                    tvDisplay.setText("0");
                    return;
                }
                tvDisplay.setText(sb.toString());
                break;
        }
    }

    //4th method *triggerResult
    public void triggerResult(View buttonPressed) {
        String displayString = tvDisplay.getText().toString();
        if(displayString.equals(".") || displayString.equals("0") || firstValueString.equals("") || operatorButton.equals(""))  //return when display has no value to avoid force exit
            return;
        double finalResult = 0;
        if(operatorButton.equals("+"))
            finalResult = result+Double.parseDouble(displayString);
        else if(operatorButton.equals("-"))
            finalResult = result-Double.parseDouble(displayString);
        else if(operatorButton.equals("×"))
            finalResult = result*Double.parseDouble(displayString);
        else if(operatorButton.equals("/"))
            finalResult = result/Double.parseDouble(displayString);
        //round off if number has no decimal then convert to string
        String secondValue = (result%1 ==0)?String.valueOf((int) Math.round(result)):String.valueOf(result);
        tvFirstInput.setText(secondValue+ " "+operatorButton+" "+displayString  + " =");
        tvDisplay.setText((finalResult % 1 == 0)? String.valueOf((int) Math.round(finalResult)):String.valueOf(finalResult));
        operatorButton = firstValueString = "";
    }
}