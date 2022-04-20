package com.example.bestinstituteofstudies;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {
    public static boolean isLoggedIn = false;
    public static boolean isAdminLoggedIn = false;
    static EditText email, password;
    Button register, login;
    DatabaseHelper mydb;

    static boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    static boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public static boolean checkValidation() {
        boolean isValid = true;
        if (isEmpty(email) || !isEmail(email)) {
            email.setError("Enter valid email!");
            isValid = false;
        }
        if (isEmpty(password)) {
            password.setError("Enter password!");
            isValid = false;
        } else {
            if (password.getText().toString().length() < 4) {
                password.setError("Password must be at least 4 chars long!");
                isValid = false;
            }
        }
        return isValid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar();
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login or Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mydb = new DatabaseHelper(this);
        email = findViewById(R.id.studentEmail);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);

        register.setOnClickListener(view -> startActivity(new Intent(this, RegisterActivity.class)));
        login.setOnClickListener(view -> {
            if (checkValidation()) {
                if (mydb.login(email.getText().toString(), password.getText().toString())) {
                    isLoggedIn = true;
                    if (email.getText().toString() == "admin@admin.com") {
                        isAdminLoggedIn = true;
                    }
                    Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity.class));
                } else {
                    email.setError("Please check your email!");
                    password.setError("Please check your password!");
                    Toast.makeText(this, "Wrong email or password!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
