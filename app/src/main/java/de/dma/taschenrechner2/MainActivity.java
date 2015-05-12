package de.dma.taschenrechner2;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;


public class MainActivity extends Activity implements SensorEventListener{

    TextView textView;
    String text;
    String rest;
    Character lastItem;
    Button button, clearButton;
    Expression e;
    double result;
    SensorManager mSensorManager;
    Sensor mAccelerometer;

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
                    if(!text.substring(0,1).equals("-")) {
                        textView.setText("-" + "" + text);
                    }
                    else
                    {
                        textView.setText(text.substring(1,textView.length()));
                    }
                }
                break;

            case R.id.buttonComma:
                if (text.length() != 0 && check(text.substring(text.length() - 1, text.length()))) {
                    Toast.makeText(getApplicationContext(), "ungültige Eingabe", Toast.LENGTH_SHORT).show();
                } else {
                    textView.setText(text + ".");
                }
                break;

            case R.id.buttonPlus:
                if (text.length() != 0 && check(text.substring(text.length() - 1, text.length()))) {
                    Toast.makeText(getApplicationContext(), "ungültige Eingabe", Toast.LENGTH_SHORT).show();
                } else {
                    textView.setText(text + "+");
                }
                break;

            case R.id.buttonMinus:
                if (text.length() != 0 && check(text.substring(text.length() - 1, text.length()))) {
                    Toast.makeText(getApplicationContext(), "ungültige Eingabe", Toast.LENGTH_SHORT).show();
                } else {
                    textView.setText(text + "-");
                }
                break;

            case R.id.buttonMultiply:
                if (text.length() != 0 && check(text.substring(text.length() - 1, text.length()))) {
                    Toast.makeText(getApplicationContext(), "ungültige Eingabe", Toast.LENGTH_SHORT).show();
                } else {
                    textView.setText(text + "*");
                }
                break;

            case R.id.buttonDivide:
                if (text.length() != 0 && check(text.substring(text.length() - 1, text.length()))) {
                    Toast.makeText(getApplicationContext(), "ungültige Eingabe", Toast.LENGTH_SHORT).show();
                } else {
                    textView.setText(text + "/");
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
                    text = text + "!";
                    buildExpression(text);
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
                    text = "e^" + text;
                    buildExpression(text);
                }
                break;

            case R.id.buttonPi:
                textView.setText(text + "3.1415926536");
                break;

            default:
                button = (Button) view;
                textView.setText(textView.getText() + "" + button.getText());
                break;
        }

    }

    public void buildExpression(String text)
    {
        e = new ExpressionBuilder(text).build();
        result = e.evaluate();
        textView.setText(result + "");
    }
    public boolean check(String input)
    {
        if(input.equals("/") || input.equals("*")|| input.equals("-")|| input.equals("+")|| input.equals("."))
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

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
