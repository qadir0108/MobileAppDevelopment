package pk.edu.bzu.bzuattendance;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;


public class TimetableActivity extends AppCompatActivity implements View.OnClickListener {
    String RollNumber;
    Button btnMarkattendance;
    ProgressBar pbLoading;
    float distance;
    private LocationRequest locationRequest;
    Location dest_location = new Location("");
    Location myLocation = new Location("");
    Double latitude_dest=29.503332;
    Double longitude_dest=71.637971;
    Integer condition = 200;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        RollNumber = getIntent().getStringExtra("RollNumber");
        btnMarkattendance = (Button)findViewById(R.id.btnMarkattendance);
        pbLoading = (ProgressBar)findViewById(R.id.pbLoading);
        btnMarkattendance.setOnClickListener(this);
        getLoadTimetable();
        locationRequest = com.google.android.gms.location.LocationRequest.create();
        locationRequest.setPriority(com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);
    }
    @Override
    public void onClick(View v) {
        pbLoading.setVisibility(View.VISIBLE);
        getCurrentLocation();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){

                if (isGPSEnabled()) {

                    getCurrentLocation();

                }else {

                    turnOnGPS();
                }
            }
            else{
                pbLoading.setVisibility(View.INVISIBLE);
                Toast.makeText(TimetableActivity.this, "Permission Required!", Toast.LENGTH_SHORT).show();
            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {

                getCurrentLocation();
            }
        }


        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        // if the intentResult is null then
        // toast a message as "cancelled"
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                // if the intentResult is not null we'll set
                // the content and format of scan message
                String tempRes =intentResult.getContents();
                String[] parts = tempRes.split("_");
                String date = parts[0];
                String slot = parts[1];
                LocalDate myDate = LocalDate.parse( date, DateTimeFormatter.ofPattern ( "yyyyMMdd" ).withLocale(Locale.getDefault() ) );
                Integer day = myDate.getDayOfWeek().getValue();
                markAttendance(slot,day);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    public  void getLoadTimetable()
    {
        pbLoading.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(TimetableActivity.this);
        String url = "https://attendance-app-backend.vercel.app/timetable";
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("RollNumber", RollNumber);
        } catch (JSONException e) {
            e.printStackTrace();
            pbLoading.setVisibility(View.INVISIBLE);
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pbLoading.setVisibility(View.INVISIBLE);
                try {
                    ArrayList<Timetable> list = new ArrayList<>();
                    JSONArray array = response.getJSONArray("Timetable");
                    for (int i=0;i<array.length();i++) {
                        JSONObject o = (JSONObject) array.get(i);
                        Timetable obj = new Timetable(
                                o.getString("Room"),
                                o.getString("Teacher"),
                                o.getString("SubjectCode"),
                                o.getString("Subject"),
                                o.getString("TimeSlot"),
                                o.getString("Session")
                        );
                        list.add(obj);
                    }
                    TimetableAdapter adapter = new TimetableAdapter(TimetableActivity.this, list);
                    ListView lvTimetable = (ListView) findViewById(R.id.lvTimetable);
                    lvTimetable.setAdapter(adapter);

                } catch (JSONException e) {
                    pbLoading.setVisibility(View.INVISIBLE);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pbLoading.setVisibility(View.INVISIBLE);
                //Toast.makeText(LoginActivity.this, "Volley Error: "+ error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);

    }
    public  void markAttendance(String slot,Integer day)
    {
        RequestQueue queue = Volley.newRequestQueue(TimetableActivity.this);
        String url = "https://attendance-app-backend.vercel.app/attendance/mark";
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("RollNumber", RollNumber);
            requestBody.put("Status", "1");
            requestBody.put("Day", day);
            requestBody.put("TimeSlot", slot);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean Success = response.getBoolean("Success");
                    if(Success) {
                        //Toast.makeText(TimetableActivity.this, "You Attendance is recorded!", Toast.LENGTH_SHORT).show();
                        builder = new AlertDialog.Builder(TimetableActivity.this);
                        builder.setTitle("Marked")
                                .setMessage("Your attendance is marked successfully. Thank You!")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                })
                                .setCancelable(true)
                                .show();
                    }
                    else {
                        String ErrorMessage = response.getString("ErrorMessage");
                        Toast.makeText(TimetableActivity.this, "Error Occurred: "+ ErrorMessage, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TimetableActivity.this, "Volley Error: "+ error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }
    private void getCurrentLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(TimetableActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {

                    LocationServices.getFusedLocationProviderClient(TimetableActivity.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);

                                    LocationServices.getFusedLocationProviderClient(TimetableActivity.this)
                                            .removeLocationUpdates(this);

                                    if (locationResult != null && locationResult.getLocations().size() >0){

                                        int index = locationResult.getLocations().size() - 1;
                                        double latitude = locationResult.getLocations().get(index).getLatitude();
                                        double longitude = locationResult.getLocations().get(index).getLongitude();
                                        //Toast.makeText(TimetableActivity.this,"Latitude: "+ latitude + "\n" + "Longitude: "+ longitude, Toast.LENGTH_SHORT).show();
                                        dest_location.setLatitude(latitude_dest);
                                        dest_location.setLongitude(longitude_dest);
                                        myLocation.setLatitude(latitude);
                                        myLocation.setLongitude(longitude);
                                        distance = myLocation.distanceTo(dest_location);
                                        pbLoading.setVisibility(View.INVISIBLE);
                                        if(distance<condition){
                                            initiateScan();
                                        }
                                        else{
                                           // Toast.makeText(TimetableActivity.this, "Current distance from university "+Math.round(distance)+" meters.", Toast.LENGTH_SHORT).show();
                                             builder = new AlertDialog.Builder(TimetableActivity.this);
                                            builder.setTitle("Can not mark attendance!")
                                                    .setMessage("You are "+Math.round(distance)/1000+" km away from university!")
                                                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {

                                                        }
                                                    })
                                                    .setCancelable(true)
                                                    .show();
                                        }
                                    }
                                }
                            }, Looper.getMainLooper());

                } else {
                    turnOnGPS();
                }

            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
        else{
            pbLoading.setVisibility(View.INVISIBLE);
        }
    }

    private void turnOnGPS() {

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(TimetableActivity.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {
                    pbLoading.setVisibility(View.INVISIBLE);
                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(TimetableActivity.this, 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });

    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;

    }
    private void  initiateScan(){
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setPrompt("Scan a QR Code");
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.initiateScan();
    }

}