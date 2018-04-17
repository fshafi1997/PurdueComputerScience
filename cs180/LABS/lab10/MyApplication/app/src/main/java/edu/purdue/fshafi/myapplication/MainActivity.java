package edu.purdue.fshafi.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TO-DO: insert these two lines into your method
        Intent intent = new Intent(this, ShakeActivity.class);
        startActivity(intent);



    }
}
