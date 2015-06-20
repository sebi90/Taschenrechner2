package de.dma.taschenrechner2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;


public class MainActivity extends Activity implements SensorEventListener{

    TextView textView;
    String text, rest;
    Button button, clearButton;
    Expression e;
    double result;
    SensorManager mSensorManager;
    Sensor mAccelerometer;
    int buttonFontSize = R.style.FontSizeMedium;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView1);
        clearButton = (Button) findViewById(R.id.buttonClear);
        clearButton.setOnLongClickListener(new View.OnLongClickListener()
        {

            @Override
            public boolean onLongClick(View v) {
                textView.setText("");
                return true;
            }
        });
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id)
        {
            case R.id.itemSmall:
                setTextButton(R.style.FontSizeSmall);
                return true;

            case R.id.itemMedium:
                setTextButton(R.style.FontSizeMedium);
                return true;

            case R.id.itemLarge:
                setTextButton(R.style.FontSizeLarge);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void setTextButton(int i)
    {
        buttonFontSize = i;
        textView.setTextAppearance(this, buttonFontSize);
        for (int j = 0; j <= 9; j++)
        {
            int id = getResources().getIdentifier("button_"+j, "id", getPackageName());
            Button but = (Button) findViewById(id);
            but.setTextAppearance(this, i);
        }
        Button but = (Button) findViewById(R.id.buttonPoint);
        but.setTextAppearance(this, buttonFontSize);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("DISPLAY_TEXT", textView.getText().toString());
        outState.putInt("BUTTON_SIZE", buttonFontSize );

    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        textView.setText(savedInstanceState.getString("DISPLAY_TEXT"));
        setTextButton(savedInstanceState.getInt("BUTTON_SIZE"));
    }


    public void buttonOnClick(View view)
    {
        text = (String) textView.getText();
        switch (view.getId()){
            case R.id.buttonClear:
                if(text.length() != 0)
                {
                    rest = text.substring(0 , text.length() -1);
                    textView.setText(rest);
                }
                break;

            case R.id.buttonEval:
                if(text.length() != 0)
                {
                    buildExpression(text);
                }break;

            case R.id.buttonPlusMinus:
                if(text.length() != 0)
                {
                    if(!text.substring(0,1).equals(getResources().getString(R.string.minus))) {
                        textView.setText(getResources().getString(R.string.minus) + "" + text);
                    }
                    else
                    {
                        textView.setText(text.substring(1,textView.length()));
                    }
                }
                break;

            case R.id.buttonPoint:
                if (text.length() != 0 && check(text.substring(text.length() - 1, text.length()))) {
                    Toast.makeText(getApplicationContext(), "ungültige Eingabe", Toast.LENGTH_SHORT).show();
                } else {
                    textView.setText(text + getResources().getString(R.string.point));
                }
                break;

            case R.id.buttonPlus:
                if (text.length() != 0 && check(text.substring(text.length() - 1, text.length()))) {
                    Toast.makeText(getApplicationContext(), "ungültige Eingabe", Toast.LENGTH_SHORT).show();
                } else {
                    textView.setText(text + getResources().getString(R.string.plus));
                }
                break;

            case R.id.buttonMinus:
                if (text.length() != 0 && check(text.substring(text.length() - 1, text.length()))) {
                    Toast.makeText(getApplicationContext(), "ungültige Eingabe", Toast.LENGTH_SHORT).show();
                } else {
                    textView.setText(text + getResources().getString(R.string.minus));
                }
                break;

            case R.id.buttonMultiply:
                if (text.length() != 0 && check(text.substring(text.length() - 1, text.length()))) {
                    Toast.makeText(getApplicationContext(), "ungültige Eingabe", Toast.LENGTH_SHORT).show();
                } else {
                    textView.setText(text + getResources().getString(R.string.multiply));
                }
                break;

            case R.id.buttonDivide:
                if (text.length() != 0 && check(text.substring(text.length() - 1, text.length()))) {
                    Toast.makeText(getApplicationContext(), "ungültige Eingabe", Toast.LENGTH_SHORT).show();
                } else {
                    textView.setText(text + getResources().getString(R.string.divide));
                }
                break;

            case R.id.buttonCos:
                if(text.length() != 0) {
                    text = "cos(" + text + ")";
                    buildExpression(text);
                }
                break;

            case R.id.buttonSin:
                if(text.length() != 0) {
                    text = "sin(" + text + ")";
                    buildExpression(text);
                }
                break;

            case R.id.button1_x:
                if(text.length() != 0) {
                    text = "1/" + text + "";
                    buildExpression(text);
                }
                break;

            case R.id.buttonX2:
                if(text.length() != 0) {
                    text = text + "^2";
                    buildExpression(text);
                }
                break;

            case R.id.buttonFak:
                if(text.length() != 0) {
                    double value = 1.0;
                    try
                    {
                        double number = Double.parseDouble(text);
                        for (double i = 1.0; i <= number; i+=1.0)
                        {
                            value *= i;
                        }
                        textView.setText(value +"");
                    }
                    catch (IllegalArgumentException e)
                    {
                        Toast.makeText(getApplicationContext(), "ungültige Eingabe", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.buttonSqrt:
                if(text.length() != 0) {
                    text = "sqrt(" + text + ")";
                    buildExpression(text);
                }
                break;

            case R.id.buttonTan:
                if(text.length() != 0) {
                    text = "tan(" + text + ")";
                    buildExpression(text);
                }
                break;

            case R.id.buttonLog:
                if(text.length() != 0) {
                    text = "log(" + text + ")";
                    buildExpression(text);
                }
                break;

            case R.id.buttonEx:
                if(text.length() != 0) {
                    text = "exp(" + text +")";
                    buildExpression(text);
                }
                break;

            case R.id.buttonPi:
                textView.setText(text + Math.PI);
                break;

            default:
                button = (Button) view;
                textView.setText(textView.getText() + "" + button.getText());
                break;
        }

    }

    public void buildExpression(String text)
    {
        try {
            e = new ExpressionBuilder(text).build();
            result = e.evaluate();
            textView.setText(result + "");
        }
        catch (IllegalArgumentException e)
        {
            Toast.makeText(getApplicationContext(), "ungültige Eingabe", Toast.LENGTH_SHORT).show();
        }

    }
    public boolean check(String input)
    {
        if(input.equals("/") || input.equals("*")|| input.equals("-")|| input.equals("+")|| input.equals("."))
        {
            return true;
        }
            return false;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        /*if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            long curTime = System.currentTimeMillis();

            if((curTime - lastUpdate) > 100)
            {
                long diffTime = (curTime -lastUpdate);
                lastUpdate = curTime;

                float x = event.values[0];


                float speed = Math.abs(x + y + z - last_x -last_y - last_y) / diffTime * 10000;

                last_x = x;


            }
        }
        //Toast.makeText(getApplicationContext(), "sensor" , Toast.LENGTH_SHORT).show();

        /*text = (String) textView.getText();
        if(text.length() != 0) {
            buildExpression(text);
        }
        */
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {


    }
    protected void onResume() {
        super.onResume();
        //bei der Rückkehr aus der zweiten Aktivität ist der Sensor wieder aktiv
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this); }
}
