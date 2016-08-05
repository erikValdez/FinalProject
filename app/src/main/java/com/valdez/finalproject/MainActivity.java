package com.valdez.finalproject;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TabActivity;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.valdez.finalproject.CatsActivity;
import com.valdez.finalproject.DogsActivity;
import com.valdez.finalproject.HomeActivity;

import java.util.List;

public class MainActivity extends TabActivity {

    // build the object that is going to be the notification itself
    NotificationCompat.Builder notification;
    private static final int uniqueID = 12345; // the notification has rto be assigned to a unique ID

    private static final String TAG = "MainActivity";
    private EditText inputEditText;

    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // build the new notification
        notification = new NotificationCompat.Builder(this);

        //remove notification once it has been visited
        notification.setAutoCancel(true);

        TabHost tabHost = getTabHost();

        // tab for Home Screen
        TabSpec homeSpec = tabHost.newTabSpec("Home");
        homeSpec.setIndicator("Home"); // set title for the tab
        Intent homeIntent = new Intent(this, HomeActivity.class);
        homeSpec.setContent(homeIntent);

        // tab for Dogs
        TabSpec dogsSpec = tabHost.newTabSpec("Dogs");
        dogsSpec.setIndicator("Dogs"); // set title for the tab
        Intent dogsIntent = new Intent(this, DogsActivity.class);
        dogsSpec.setContent(dogsIntent);

        // tabs for Cats
        TabSpec catsSpec = tabHost.newTabSpec("Cats");
        catsSpec.setIndicator("Cats"); // set title for the tab
        Intent catsIntent = new Intent(this, CatsActivity.class);
        catsSpec.setContent(catsIntent);

        // adding all the TabSpec to TabHost
        tabHost.addTab(homeSpec);
        tabHost.addTab(dogsSpec);
        tabHost.addTab(catsSpec);

        inputEditText = (EditText) findViewById(R.id.inputEditText);

        dbHandler = new MyDBHandler(this, null, null, 1);

    }

    public void addClick(View view) {
        Products product = new Products(inputEditText.getText().toString());
        dbHandler.addProduct(product);
    }

    public void deleteClick(View view) {
        String inputText = inputEditText.getText().toString();
        dbHandler.deleteProduct(inputText);
    }

    public void showAllClick(View view) {
        List<Products> dbString = dbHandler.databaseToString();
        String log ="";
        for(Products pn : dbString){
            log += "Name: " + pn.get_productname() + "\n";
        }

        Log.i(TAG, log);

        Intent i = new Intent(this, DisplayListActivity.class);
        i.putExtra("log", log);
        startActivity(i);

        inputEditText.setText("");
    }

    public void notifyClick(View view){

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        notification.setSmallIcon(R.mipmap.ic_launcher);
        notification.setTicker("This is the ticker");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("Sorry");
        notification.setContentText("This pet has already been adopted");

        notification.setSound(alarmSound);

        // send the notification to the home screen
        Intent i = new Intent(this, MainActivity.class);

        //gice the device access to perform this intent by calling the Pending Intent
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        // send out the notification
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqueID, notification.build());
    }
}
