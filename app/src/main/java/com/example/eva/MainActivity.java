package com.example.eva;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    Button b1, b2;
    EditText e1, e2, e3, user;
    TextView passStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        b1 = (Button) findViewById(R.id.register);
        b2 = (Button) findViewById(R.id.button2);

        e1 = (EditText) findViewById(R.id.email);
        e2 = (EditText) findViewById(R.id.pass);
        e3 = (EditText) findViewById(R.id.cpass);
        user = (EditText) findViewById(R.id.user);

        passStatus = (TextView) findViewById(R.id.tv_pass);


        e2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String psw = e2.getText().toString().trim();
                validatePassword(psw);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = e1.getText().toString();
                String s2 = e2.getText().toString();
                String s3 = e3.getText().toString();
                String usr = user.getText().toString();

                if (s1.equals("") || s2.equals("") || s3.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                } else {
                    if (s2.equals(s3)) {
                        Boolean chkemail = db.chkemail(s1);
                        if (chkemail == true) {
                            Boolean insert = db.insert(s1, usr, s2);
                            if (insert == true) {
                                Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Email Already exists", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Password donot match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    // Validate password
    public void validatePassword(String pass) {
        Pattern CapsLetter = Pattern.compile("[A-Z]");
        Pattern SmallLetter = Pattern.compile("[a-z]");
        Pattern numCase = Pattern.compile("[0-9]");
        if (!CapsLetter.matcher(pass).find() || !SmallLetter.matcher(pass).find() || !numCase.matcher(pass).find()) {
            passStatus.setText("Password is weak");
            // passStatus.setTextColor(android.R.color.holo_red_dark);
        } else if (pass.length() < 5) {
            passStatus.setText("Length is short");
            //passStatus.setTextColor(android.R.color.holo_red_dark);
        } else {
            passStatus.setText("Password is Strong");
            //passStatus.setTextColor(android.R.color.holo_green_light);
        }

    }
}