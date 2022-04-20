package com.example.bestinstituteofstudies;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.ll_menu) != null) {
            this.getSupportActionBar().hide();
            initMenu();
        } else {
            this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setCustomView(R.layout.custom_action_bar);
            getSupportActionBar().setElevation(0);
            getSupportActionBar().getCustomView();
        }

        TextView readMoreTrafficLight = findViewById(R.id.readMoreTrafficLight);
        ContactUsActivity.styleLink(readMoreTrafficLight);
        Intent i = new Intent(this, ReadMoreActivity.class);
        readMoreTrafficLight.setOnClickListener(view -> {
            i.putExtra("link", "https://covid19.govt.nz/traffic-lights/");
            startActivity(i);
        });
    }

    private void initMenu() {
        View tvAboutUs = findViewById(R.id.tv_about_us);
        tvAboutUs.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AboutUsActivity.class)));
        View tvContactUs = findViewById(R.id.tv_contact_us);
        tvContactUs.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, ContactUsActivity.class)));
        View tvGallery = findViewById(R.id.tv_gallery);
        tvGallery.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, GalleryActivity.class)));
        View tvLoginRegister = findViewById(R.id.tv_login_register);
        tvLoginRegister.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, LoginActivity.class)));
        View tvManageStudents = findViewById(R.id.tv_manage_students);
        tvManageStudents.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, ManageStudentsActivity.class)));
        View tvLogout = findViewById(R.id.tv_logout);
        tvLogout.setOnClickListener(view -> {
            LoginActivity.isLoggedIn = false;
            startActivity(new Intent(this, MainActivity.class));
            Toast.makeText(this, "Logged Out Successfully!", Toast.LENGTH_SHORT).show();
        });
        if (!LoginActivity.isLoggedIn) {
            tvLoginRegister.setVisibility(View.VISIBLE);
            tvLogout.setVisibility(View.GONE);
            tvManageStudents.setVisibility(View.GONE);
        } else {
            tvLoginRegister.setVisibility(View.GONE);
            tvLogout.setVisibility(View.VISIBLE);
            if (LoginActivity.isAdminLoggedIn) {
                tvManageStudents.setVisibility(View.VISIBLE);
            }
        }
    }
}