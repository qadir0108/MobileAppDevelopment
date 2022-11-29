package pk.edu.bzu.bzuattendance;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private static final String TAG = "qadir";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        String Name = getIntent().getStringExtra("Name");

        TextView txtName = (TextView) findViewById(R.id.txtName);
        txtName.setText("Welcome " + Name);
    }
}
