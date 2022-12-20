package pk.edu.bzu.bzuattendance;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        RollNumber = getIntent().getStringExtra("RollNumber");
        btnMarkattendance = (Button)findViewById(R.id.btnMarkattendance);
        btnMarkattendance.setOnClickListener(this);
        getLoadTimetable();

    }
    @Override
    public void onClick(View v) {
        // we need to create the object
        // of IntentIntegrator class
        // which is the class of QR library
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setPrompt("Scan a barcode or QR Code");
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
        RequestQueue queue = Volley.newRequestQueue(TimetableActivity.this);
        String url = "https://attendance-app-backend.vercel.app/timetable";
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("RollNumber", RollNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //pbLoading.setVisibility(View.INVISIBLE);
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
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //pbLoading.setVisibility(View.INVISIBLE);
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
                        Toast.makeText(TimetableActivity.this, "You Attendance is recorded!", Toast.LENGTH_SHORT).show();
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


}