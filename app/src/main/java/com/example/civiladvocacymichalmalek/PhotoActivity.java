package com.example.civiladvocacymichalmalek;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

public class PhotoActivity extends OfficialActivity{

    ImageView pic, logo;
    TextView na, loc, pos;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.other_menu, menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_page);

        Intent intent = getIntent();

        String p = intent.getStringExtra("position");
        String name = intent.getStringExtra("name");
        String location = intent.getStringExtra("location");
        String party = intent.getStringExtra("party");
        String url = intent.getStringExtra("photourls");

        //Log.d(TAG, photourl);

        pic = findViewById(R.id.image);
        logo = findViewById(R.id.partyLogo);
        na = findViewById(R.id.n);
        loc = findViewById(R.id.place);
        pos = findViewById(R.id.o);

        cl = findViewById(R.id.cl);

        if(party.equals("Republican Party")){
            cl.setBackgroundColor(getResources().getColor(R.color.purple_200));
            logo.setImageResource(R.drawable.rep_logo);
        }else if(party.equals("Democratic Party")){
            cl.setBackgroundColor(getResources().getColor(R.color.teal_200));
            logo.setImageResource(R.drawable.dem_logo);
        }else{
            cl.setBackgroundColor(getResources().getColor(R.color.black));
            logo.setImageResource(0);
        }

        loc.setText(location);
        pos.setText(p);
        na.setText(name);

        if(Objects.equals(url, "") || url == null){
            Picasso.get().load(R.drawable.missing).into(pic);
        }
        else{
            Picasso.get().load(url)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.brokenimage)
                    .into(pic);

        }

    }
}
