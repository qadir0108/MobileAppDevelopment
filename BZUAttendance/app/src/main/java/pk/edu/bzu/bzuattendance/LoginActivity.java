package pk.edu.bzu.bzuattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    ProgressBar pbLoading;
    EditText tvUsername;
    EditText tvPassword;
    Button btnLogin;
    Button btnSignup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pbLoading = (ProgressBar)findViewById(R.id.pbLoading);
        tvUsername = (EditText)findViewById(R.id.tvUsername);
        tvPassword = (EditText)findViewById(R.id.tvPassword);

        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                pbLoading.setVisibility(View.VISIBLE);
                ProcessLogin(tvUsername.getText().toString(), tvPassword.getText().toString());
            }
        });
        btnSignup = (Button)findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                 Intent i = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(i);
            }
        });
    }

    public void ProcessLogin(String Username, String Password) {
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        String url = "https://attendance-app-backend.vercel.app/student/login";
        JSONObject request = new JSONObject();
        try {
            request.put("rollnumber", Username);
            request.put("password", Password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pbLoading.setVisibility(View.INVISIBLE);
                try {
                    boolean Success = response.getBoolean("status");
                    if(Success) {
                        String Name = response.getString("fullName");
                        Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
                        i.putExtra("Name", Name);
                        startActivity(i);
                    }
                    else {
                        String ErrorMessage = response.getString("ErrorMessage");
                        Toast.makeText(LoginActivity.this, "Server Error: "+ ErrorMessage, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pbLoading.setVisibility(View.INVISIBLE);
                Toast.makeText(LoginActivity.this, "Volley Error: "+ error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonObjectRequest);
    }
}