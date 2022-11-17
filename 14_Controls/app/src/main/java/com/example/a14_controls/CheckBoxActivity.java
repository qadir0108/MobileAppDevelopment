package com.example.a14_controls;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CheckBoxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkbox);

        CheckBox chkBSIT = (CheckBox) findViewById(R.id.chkBSIT);
        CheckBox chkBSEng = (CheckBox) findViewById(R.id.chkBSEng);
        CheckBox chkBSPA = (CheckBox) findViewById(R.id.chkBSPA);
        CheckBox chkBSSoci = (CheckBox) findViewById(R.id.chkBSSoci);
        Button btnShow = (Button) findViewById(R.id.btnShow);
        btnShow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String message = "Programs Applied: ";
                if(chkBSIT.isChecked())
                    message += " BS(IT)";
                if(chkBSEng.isChecked())
                    message += " BS(English)";
                if(chkBSPA.isChecked())
                    message += " BS(PA)";
                if(chkBSSoci.isChecked())
                    message += " BS(Sociology)";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }
}