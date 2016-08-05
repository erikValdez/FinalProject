package com.valdez.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list);

        Bundle data = getIntent().getExtras();
        if(data == null){
            return;
        }

        String msg = data.getString("log");
        final TextView displayTextView = (TextView) findViewById(R.id.displayTextView);
        displayTextView.setText(msg);
    }
}
