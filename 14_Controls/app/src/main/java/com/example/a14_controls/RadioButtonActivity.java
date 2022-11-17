package com.example.a14_controls;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RadioButtonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radiobutton);

        RadioGroup rdoGroupGender = (RadioGroup) findViewById(R.id.rdoGroupGender);
        //RadioButton rdoMale = (RadioButton) findViewById(R.id.rdoMale); // rdoMale.isChecked()
        //RadioButton rdoFemale = (RadioButton) findViewById(R.id.rdoFemale); // rdoFemale.isChecked()
        //RadioButton rdoThird = (RadioButton) findViewById(R.id.rdoThird); // rdoThird.isChecked()
        Button btnShow = (Button) findViewById(R.id.btnShow);
        btnShow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int selectedId = rdoGroupGender.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = (RadioButton) findViewById(selectedId);
                String message;
                if (selectedRadioButton == null)
                    message = "Gender not selected";
                else
                    message = "You Selected Gender: " + selectedRadioButton.getText().toString();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }
}