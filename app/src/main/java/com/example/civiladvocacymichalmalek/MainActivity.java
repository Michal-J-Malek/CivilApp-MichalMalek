package com.example.civiladvocacymichalmalek;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements CustomAdapter.OfficialListener{

    private FusedLocationProviderClient mFusedLocationClient;
    private static final String TAG = "MainActivity";
    private static final int REQUEST_LOCATION_PERMISSION = 99;
    private String place;
    String myLoc;
    private String queryCall;
    private TextView local;
    private RecyclerView listRV;
    CustomAdapter adapter;
    private OnItemClickListener OnItemClickListener;
    ArrayList<GovData> govDataArrayList;


    //Creates Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    //Menu Selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            //Goes back to Home/Main Activity
            case android.R.id.home:
                onBackPressed();
                return true;

            //Goes to Information Activity
            case R.id.info:

                startActivity(new Intent(MainActivity.this, InfoPage.class));
                return true;

            //Opens Search Alert box
            case R.id.search:
                EditText editText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Enter Location").setView(editText).setPositiveButton("OK", (dialogInterface, i) -> {
                    place = editText.getText().toString();
                    getOfficials(place);
                    //shared preference stuff not needed rn
                    //editor.putString("loc", location);
                    //editor.apply();

                }).setNegativeButton("Cancel", null).create();
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Main Activity Execution
    @SuppressLint({"MissingPermission", "NotifyDataSetChanged"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestLocationPermission();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // initializing our variable for our recycler view.
        local = findViewById(R.id.location_textview);
        govDataArrayList = new ArrayList<>();

        // setting layout manager to our adapter class.
        listRV = findViewById(R.id.list);
        listRV.setHasFixedSize(true);
        listRV.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CustomAdapter(govDataArrayList, this);
        listRV.setAdapter(adapter);
        adapter.notifyDataSetChanged();
            // initializing adapter for recycler view.

            // setting adapter class for recycler view.
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    // Got last known location. In some situations this can be null.
                    if (location != null) {

                        getPlace(location);
                        getOfficials(place);
                    }
                })
                .addOnFailureListener(this, e -> Toast.makeText(MainActivity.this,
                        e.getMessage(), Toast.LENGTH_LONG).show());
        }

        //Function for Calling to API
        public void getOfficials (String loco){
            queryCall = "https://www.googleapis.com/civicinfo/v2/representatives?key=AIzaSyDv0q5LNc8RSyMMQfNipBLgDAMzA5aDYnw&address=" + loco;
            RequestQueue requested = Volley.newRequestQueue(MainActivity.this);

            Log.d(TAG, "IN getOfficial");

            JsonObjectRequest jsonOb = new JsonObjectRequest(Request.Method.GET, queryCall, null, new Response.Listener<JSONObject>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(JSONObject response) {
                    govDataArrayList.clear();


                    try {
                        JSONObject ad = response.getJSONObject("normalizedInput");
                        String l = ad.getString("line1");
                        String c = ad.getString("city");
                        String st = ad.getString("state");
                        String zip = ad.getString("zip");
                        if(l == null || l.equals("")){
                            myLoc = c + ", " + st + " " + zip;
                            local.setText(myLoc);
                        }else{
                            myLoc = l+ ", "+c + ", " + st + " " + zip;
                            local.setText(myLoc);
                        }

                        String p;
                        String name;
                        String line1 = "";
                        String line2 = "";
                        String line3 = "";
                        String cities = "";
                        String state = "";
                        String zipper = "";
                        String party = "";
                        String phone = "";
                        String url = "";
                        String email = "";
                        String photourl = "";
                        String FID = "";
                        String TID = "";
                        String YID = "";
                        String address ="";

                        int length = response.getJSONArray("offices").length();

                        for(int i = 0; i<length; i++) {//change back to length
                            JSONObject of = response.getJSONArray("offices").getJSONObject(i);
                            p = of.getString("name");
                            //Integer id = of.getInt("officeIndices");
                            JSONObject nam = response.getJSONArray("officials").getJSONObject(i);
                            name = nam.getString("name");

                            address = "";
                            if(nam.has("address")){
                                JSONArray add = nam.getJSONArray("address");
                                line1 = add.getJSONObject(0).getString("line1");
                                address = address + line1;
                                if(add.getJSONObject(0).has("line2")){
                                    line2= add.getJSONObject(0).getString("line2");
                                    address = address + " "+line2;
                                }
                                if(add.getJSONObject(0).has("line3")){
                                    line3= add.getJSONObject(0).getString("line3");
                                    address = address+" "+line3;
                                }

                                cities= add.getJSONObject(0).getString("city");
                                address = address+", " +cities;
                                state= add.getJSONObject(0).getString("state");
                                address= address +", "+state;
                                zipper= add.getJSONObject(0).getString("zip");
                                address = address +" "+zipper;
                            }

                            party = nam.getString("party");

                            if(nam.has("phones")){
                                phone = nam.getJSONArray("phones").getString(0);
                            }

                            if(nam.has("urls")){
                                url = nam.getJSONArray("urls").getString(0);
                            }

                            if(nam.has("emails")){
                                email = nam.getJSONArray("emails").getString(0);
                            }
                            if(nam.has("photoUrl")){
                                photourl = nam.getString("photoUrl").replace("http","https");
                            }
                            if(nam.has("channels")){
                                JSONArray channels = nam.getJSONArray("channels");
                                for(int j = 0; j<channels.length(); j++) {
                                    if(channels.getJSONObject(j).get("type").equals("Twitter")){
                                        TID = channels.getJSONObject(j).getString("id");
                                    }
                                    if(channels.getJSONObject(j).get("type").equals("Facebook")){
                                        FID = channels.getJSONObject(j).getString("id");
                                    }
                                    if(channels.getJSONObject(j).get("type").equals("Youtube")){
                                        YID = channels.getJSONObject(j).getString("id");
                                    }
                                }
                            }




                            GovData date = new GovData(name, p, address, party, phone, url, email, photourl, FID, TID, YID);
                            govDataArrayList.add(date);
                        }

                        /*JSONObject og = response.getJSONArray("offices").getJSONObject(1);
                        String pa = og.getString("name");
                        JSONObject nan = response.getJSONArray("officials").getJSONObject(1);
                        String na = nan.getString("name");
                        govDataArrayList.add(date);

                        String p = "Senator";
                        String name = "Tammy Duckworth";
                        String party = "Democratic Party";
                        String phone = "(202)224-2854";
                        String url = "https://www.duckworth.com.senate.gov/";
                        String email = "some email";
                        String photourl = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/dc/Tammy_Duckworth%2C_official_portrait%2C_115th_Congress.jpg/1024px-Tammy_Duckworth%2C_official_portrait%2C_115th_Congress.jpg";
                        String FID = "https://www.facebook.com/SenDuckworth";
                        String TID = "https://twitter.com/SenDuckworth";
                        String YID = "https://www.youtube.com/senduckworth";

                        String address = "524 Hart Senate Office Building, Washington, DC 20510";

                        GovData date = new GovData(name, p, address, party, phone, url, email, photourl, FID, TID, YID);
                        govDataArrayList.add(date);
                        */

                        listRV.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        //listRV.setAdapter(adapter);
                        Log.d(TAG, "This should be first");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            requested.add(jsonOb);
        }

        private boolean hasNetworkConnection () {
            ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isConnectedOrConnecting());
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            // Forward results to EasyPermissions
            EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        }

        @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
        public void requestLocationPermission() {
            String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            if(EasyPermissions.hasPermissions(this, perms)) {

            }
            else {
                EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, perms);
            }
        }

        private void getPlace(Location loc) {

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses;

            try {
                addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String zip = addresses.get(0).getPostalCode();
                place = zip;
                Log.d(TAG, "whatIwant " + place);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    @Override
    public void onOfficialClick(int position) {
        govDataArrayList.get(position);
        Intent intent = new Intent(MainActivity.this, OfficialActivity.class);
        intent.putExtra("name", govDataArrayList.get(position).getName());
        intent.putExtra("position", govDataArrayList.get(position).getOffice());
        intent.putExtra("address", govDataArrayList.get(position).getAddress());
        intent.putExtra("party", govDataArrayList.get(position).getParty());
        intent.putExtra("phone", govDataArrayList.get(position).getPhone());
        intent.putExtra("url", govDataArrayList.get(position).getUrl());
        intent.putExtra("email", govDataArrayList.get(position).getEmail());
        intent.putExtra("photo", govDataArrayList.get(position).getPhoto());
        intent.putExtra("FID", govDataArrayList.get(position).getFID());
        intent.putExtra("TID", govDataArrayList.get(position).getTID());
        intent.putExtra("YID", govDataArrayList.get(position).getYID());
        intent.putExtra("location", myLoc);
        startActivity(intent);
    }

    public class OnItemClickListener {
    }

}
