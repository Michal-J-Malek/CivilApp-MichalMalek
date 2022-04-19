package com.example.civiladvocacymichalmalek;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class InfoPage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_page);
        getSupportActionBar().setTitle("Civil Advocacy");
        TextView goog = findViewById(R.id.google);

        goog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL = "https://developers.google.com/civic-information/";
                Intent googleAPI = new Intent(Intent.ACTION_VIEW);
                googleAPI.setData(Uri.parse(URL));
                startActivity(googleAPI);
            }
        });
    }

}
