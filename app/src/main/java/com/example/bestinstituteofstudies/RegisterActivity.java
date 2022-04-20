package com.example.bestinstituteofstudies;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends BaseActivity {
    static EditText firstName, lastName, email, password;
    Button register;
    DatabaseHelper mydb;

    public static boolean checkName() {
        boolean isValid = true;
        if (LoginActivity.isEmpty(email) || !LoginActivity.isEmail(email)) {
            email.setError("Enter valid email!");
            isValid = false;
        }
        if (LoginActivity.isEmpty(password)) {
            password.setError("Enter password!");
            isValid = false;
        } else {
            if (password.getText().toString().length() < 4) {
                password.setError("Password must be at least 4 chars long!");
                isValid = false;
            }
        }
        if (LoginActivity.isEmpty(firstName)) {
            firstName.setError("Enter First Name!");
            isValid = false;
        }
        if (LoginActivity.isEmpty(lastName)) {
            lastName.setError("Enter Last Name!");
            isValid = false;
        }
        return isValid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar();
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mydb = new DatabaseHelper(this);
        firstName = findViewById(R.id.registerFirstName);
        lastName = findViewById(R.id.registerLastName);
        email = findViewById(R.id.registerEmail);
        password = findViewById(R.id.registerPassword);
        register = findViewById(R.id.registerRegister);

        register.setOnClickListener(view -> {
            if (checkName()) {
                if (!mydb.isExistingUser(email.getText().toString())) {
                    mydb.register(email.getText().toString(), password.getText().toString(), firstName.getText().toString(), lastName.getText().toString());
                    LoginActivity.isLoggedIn = true;
                    Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity.class));
                }
            }
        });
    }
}
