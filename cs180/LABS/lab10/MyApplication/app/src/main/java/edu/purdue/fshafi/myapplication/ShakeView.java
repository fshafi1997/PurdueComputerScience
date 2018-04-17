package edu.purdue.fshafi.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShakeView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake_view);
    }

    TextView message;

    public ShakeView(TextView message) {
     this.message = message;
        // TO-DO: initialize instance variables
    }

    public void changeMessage(String message) {
        // TO-DO: change the message of the TextView
        // example: someTextView.setText("someMessage")
        this.message.setText(message);
    }
}
