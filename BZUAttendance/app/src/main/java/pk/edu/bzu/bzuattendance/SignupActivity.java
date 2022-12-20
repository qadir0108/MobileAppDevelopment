package pk.edu.bzu.bzuattendance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SignupActivity extends AppCompatActivity {

    ProgressBar pbLoading;
    EditText tvFullname;
    EditText tvRollno;
    EditText tvEmail;
    EditText tvSession;
    EditText tvPassword;
    Button btnSignup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        pbLoading = (ProgressBar)findViewById(R.id.pbLoading);
        tvFullname = (EditText)findViewById(R.id.tvFullname);
        tvPassword = (EditText)findViewById(R.id.tvPassword);
        tvRollno = (EditText)findViewById(R.id.tvRollno);
        tvEmail = (EditText)findViewById(R.id.tvEmail);
        tvSession = (EditText)findViewById(R.id.tvSession);

        btnSignup = (Button)findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
               if(tvFullname.getText().toString().trim().isEmpty() || tvPassword.getText().toString().trim().isEmpty() || tvRollno.getText().toString().trim().isEmpty() || tvEmail.getText().toString().trim().isEmpty() || tvSession.getText().toString().trim().isEmpty()){
                   Toast.makeText(SignupActivity.this, "Kindly fill all values! ", Toast.LENGTH_SHORT).show();
                   return;
               }
               pbLoading.setVisibility(View.VISIBLE);
               ProcessSignup(tvFullname.getText().toString().trim(),tvPassword.getText().toString().trim(),tvRollno.getText().toString().trim(),tvEmail.getText().toString().trim(),Integer.parseInt(tvSession.getText().toString().trim()));
           }
        });

    }

    public void ProcessSignup(String Fullname, String Password , String Rollno , String Email , Number Session) {

        RequestQueue queue = Volley.newRequestQueue(SignupActivity.this);
        String url = "https://attendance-app-backend.vercel.app/student/register";
        JSONObject request = new JSONObject();
        try {
            request.put("Name", Fullname);
            request.put("RollNumber", Rollno);
            request.put("Session", Session);
            request.put("Email", Email);
            request.put("Password",Password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pbLoading.setVisibility(View.INVISIBLE);
                try {

                    boolean Success = response.getBoolean("Success");
                    if(Success) {
                        Toast.makeText(SignupActivity.this, "New user is successfully registered!", Toast.LENGTH_SHORT).show();
                        String Name = response.getString("Name");
                        Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
                        i.putExtra("Name", Name);
                        startActivity(i);
                    }
                    else {
                        boolean Unique = response.getBoolean("RollNumberError");
                        if(Unique){
                            Toast.makeText(SignupActivity.this, "This Roll Number is already registered!", Toast.LENGTH_SHORT).show();
                        }
//                        String ErrorMessage = response.getString("ErrorMessage");
                        Toast.makeText(SignupActivity.this, "Enter correct data including Email!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pbLoading.setVisibility(View.INVISIBLE);
                Toast.makeText(SignupActivity.this, "Volley Error: "+ error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonObjectRequest);
    }
}