package com.example.a12_intents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnOpenActivity = (Button) findViewById(R.id.btnOpenActivity);
        btnOpenActivity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SecondActivity.class);
                i.putExtra("name", "BS(IT)");
                i.putExtra("Key1", "ABC");
                i.putExtra("Key2", "123");
                startActivity(i);
            }
        });

        Button btnOpenURL = (Button) findViewById(R.id.btnOpenURL);
        btnOpenURL.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                startActivity(i);
            }
        });

        Button btnStartPhone = (Button) findViewById(R.id.btnStartPhone);
        btnStartPhone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("tel:+923216334272"));
                startActivity(i);
            }
        });

        Button btnSendEmail = (Button) findViewById(R.id.btnSendEmail);
        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String[] to = { "kamran.qadir@bzu.edu.pk" };
                String[] cc = { "kamran.qadir@bzu.edu.pk" };
                Intent i = new Intent(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_EMAIL, to);
                i.putExtra(Intent.EXTRA_CC, cc);
                i.putExtra(Intent.EXTRA_SUBJECT, "Email Subject");
                i.putExtra(Intent.EXTRA_TEXT, "Email Body");
                i.setType("message/rfc822");

                startActivity(Intent.createChooser(i, "Choose an Email client :"));
            }
        });
    }
}