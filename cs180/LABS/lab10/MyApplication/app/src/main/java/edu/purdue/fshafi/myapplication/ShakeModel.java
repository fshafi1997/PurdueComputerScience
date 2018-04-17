package edu.purdue.fshafi.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Random;

public class ShakeModel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake_model);
    }

    String[] messages = new String[8];
    ShakeView view;

    public ShakeModel(ShakeView view) {
        //TO-DO: initialize instance variables
        this.view = view;
        messages[0] = "Hello";
        messages[1] = "Bye";
        messages[2] = "Farhan";
        messages[3] = "Purdue";
        messages[4] = "Pakistan";
        messages[5] = "USA";
        messages[6] = "CS";
        messages[7] = "Project";
        //create different response messages in all positions of the messages array
    }

    public void displayMessage() {
        //TO-DO: choose a random message of the array
        Random random = new Random();
        int x = random.nextInt(8);
        this.view.changeMessage(messages[x]);
        //make a call to view.changeMessages using the random message chosen
    }
}
