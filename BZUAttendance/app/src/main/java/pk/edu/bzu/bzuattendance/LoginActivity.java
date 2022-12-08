package pk.edu.bzu.bzuattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    }

    public void ProcessLogin(String Username, String Password) {
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        String url = "http://10.0.2.2/students/login";
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("RollNumber", Username);
            requestBody.put("Password", Password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pbLoading.setVisibility(View.INVISIBLE);
                try {
                    boolean Success = response.getBoolean("Success");
                    if(Success) {
                        String Name = response.getString("Name");
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

        queue.add(request);
    }
}