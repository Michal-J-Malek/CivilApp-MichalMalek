package com.example.civiladvocacymichalmalek;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.Objects;

public class OfficialActivity extends MainActivity{

    //public static final String EXTRA_ID = "bruh";

    ImageView fb, tw, yt, partyLogo, pic;
    TextView office, ofname, ofparty, ofadd, ofphone, ofweb, ofem, loca, emailtext, phonetext, webtext;
    String TAG = "OfficialACtivity";
    ConstraintLayout cl;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.other_menu, menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.official_page);


        Intent intent = getIntent();

        String p = intent.getStringExtra("position");
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        String party = intent.getStringExtra("party");
        String phone = intent.getStringExtra("phone");
        String url = intent.getStringExtra("url");
        String email = intent.getStringExtra("email");
        String photourl = intent.getStringExtra("photo");
        String FID = intent.getStringExtra("FID");
        String TID = intent.getStringExtra("TID");
        String YID = intent.getStringExtra("YID");
        String location = intent.getStringExtra("location");

        //Log.d(TAG, photourl);

        webtext = findViewById(R.id.website_textview);
        phonetext = findViewById(R.id.phone_textView);
        emailtext = findViewById(R.id.email_textview);
        loca = findViewById(R.id.loc);
        ofem = findViewById(R.id.of_email);
        fb = findViewById(R.id.of_face);
        tw = findViewById(R.id.of_twit);
        yt = findViewById(R.id.of_you);
        pic = findViewById(R.id.imageView);
        partyLogo = findViewById(R.id.partyImage);
        office = findViewById(R.id.of_office);
        ofname = findViewById(R.id.of_name);
        ofparty = findViewById(R.id.of_party);
        ofadd = findViewById(R.id.of_address);
        ofphone = findViewById(R.id.of_phone);
        ofweb = findViewById(R.id.of_website);

        cl = findViewById(R.id.conlay);

        if(party.equals("Republican Party")){
            cl.setBackgroundColor(getResources().getColor(R.color.purple_200));
            partyLogo.setImageResource(R.drawable.rep_logo);
        }else if(party.equals("Democratic Party")){
            cl.setBackgroundColor(getResources().getColor(R.color.teal_200));
            partyLogo.setImageResource(R.drawable.dem_logo);
        }else{
            cl.setBackgroundColor(getResources().getColor(R.color.black));
            partyLogo.setImageResource(0);
            pic.setBackgroundColor(getResources().getColor(R.color.white));
        }

        pic.setImageResource(R.drawable.missing);

        //for now
        fb.setImageResource(R.drawable.facebook);
        tw.setImageResource(R.drawable.twitter);
        yt.setImageResource(R.drawable.youtube);

        Log.d(TAG, "URL: "+ photourl);

        loca.setText(location);

        office.setText(p);
        ofname.setText(name);
        ofparty.setText(party);
        ofadd.setText(address);

        if(Objects.equals(email, "")){
            ofem.setVisibility(View.GONE);
            emailtext.setVisibility(View.GONE);
        }else{
            ofem.setText(email);
        }

        if(Objects.equals(phone, "")){
            ofphone.setVisibility(View.GONE);
            phonetext.setVisibility(View.GONE);
        }else{
            ofphone.setText(phone);
        }

        if(Objects.equals(url, "")){
            ofweb.setVisibility(View.GONE);
            webtext.setVisibility(View.GONE);
        }else{
            ofweb.setText(url);
        }



        if(Objects.equals(photourl, "") || photourl == null){
            pic.setImageResource(R.drawable.missing);

        }
        else{
            Picasso.get().load(photourl)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.brokenimage)
                    .into(pic);

            if(((ImageView)pic).getDrawable().getConstantState() == OfficialActivity.this
                    .getResources().getDrawable(R.drawable.brokenimage).getConstantState()){
                pic.setImageResource(R.drawable.brokenimage);
            }
        }


        if(((ImageView)pic).getDrawable().getConstantState() == OfficialActivity.this
                .getResources().getDrawable(R.drawable.brokenimage)
                .getConstantState() || ((ImageView)pic).getDrawable().getConstantState() == OfficialActivity.this
                .getResources().getDrawable(R.drawable.missing)
                .getConstantState()){
            pic.setClickable(false);
        }else{
            pic.setOnClickListener(new View.OnClickListener() {
                //@Override
                public void onClick(View v) {
                    Intent intent = new Intent(OfficialActivity.this, PhotoActivity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("position", p);
                    intent.putExtra("location", location);
                    intent.putExtra("party", party);
                    intent.putExtra("photourls", photourl);
                    startActivity(intent);
                }
            });
        }

        if(Objects.equals(FID, "")){
            fb.setImageResource(0);
        }else{
            fb.setOnClickListener(new View.OnClickListener() {
                //@Override
                public void onClick(View v) {
                    facebookClicked(fb, FID);
                }
            });
        }

        if(Objects.equals(TID, "")){
            tw.setImageResource(0);
        }else{
            tw.setOnClickListener(new View.OnClickListener() {
                //@Override
                public void onClick(View v) {
                    twitterClicked(tw, TID);
                }
            });
        }

        if(Objects.equals(YID, "")){
            yt.setImageResource(0);
        }else{
            yt.setOnClickListener(new View.OnClickListener() {
                //@Override
                public void onClick(View v) {
                    youTubeClicked(yt, YID);
                }
            });
        }
    }

    public void twitterClicked(View v, String TID) {
        Intent intent = null;
        String name = TID;
        try {
// get the Twitter app if possible
            getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + name));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
// no Twitter app, revert to browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + name));
        }
        startActivity(intent);
    }
    //Facebook (example onClick method to be associated with the Facebook ImageView icon):
    public void facebookClicked(View v, String FID) {
        String FACEBOOK_URL = "https://www.facebook.com/" + FID;
        String urlToUse;
        PackageManager packageManager = getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                urlToUse = "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                urlToUse = "fb://page/" + FID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            urlToUse = FACEBOOK_URL; //normal web url
        }
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        facebookIntent.setData(Uri.parse(urlToUse));
        startActivity(facebookIntent);
    }
    public void youTubeClicked(View v, String YID) {
        String name = YID;
        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse("https://www.youtube.com/" + name));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/" + name)));
        }
    }
}
