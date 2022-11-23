package com.example.a25_alertdialogs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnShowQuestion = (Button)findViewById(R.id.btnShowQuestion);
        btnShowQuestion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showQuestionDialog();
            }
        });

        Button btnShowAlert = (Button)findViewById(R.id.btnShowAlert);
        btnShowAlert.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                MyDialog myDialog = new MyDialog("Alert Dialog shown as Dialog Fragment");
                myDialog.show(getFragmentManager(), "TAG");

            }
        });

        String[] colors = getResources().getStringArray(R.array.colors_array);
        Button btnShowListDialog = (Button)findViewById(R.id.btnShowListDialog);
        btnShowListDialog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                MyListDialog myDialog = new MyListDialog(colors);
                myDialog.show(getFragmentManager(), "TAG");

            }
        });
    }

    private void showQuestionDialog() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setMessage("Are you sure to open Second Screen?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // Now Opening second screen
                        Intent i = new Intent(MainActivity.this, SecondActivity.class);
                        i.putExtra("name", "BS(IT)");
                        startActivity(i);
                    }
                });
        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,"You clicked NO button",Toast.LENGTH_LONG).show();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}