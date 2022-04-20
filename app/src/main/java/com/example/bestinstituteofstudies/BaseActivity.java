package com.example.bestinstituteofstudies;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        MenuItem logOrReg = menu.findItem(R.id.LogOrReg);
        MenuItem logout = menu.findItem(R.id.logout);
        MenuItem manageStudents = menu.findItem(R.id.manageStudents);
        if (!LoginActivity.isLoggedIn) {
            logOrReg.setVisible(true);
            logout.setVisible(false);
            manageStudents.setVisible(false);
        } else {
            logOrReg.setVisible(false);
            logout.setVisible(true);
            if (LoginActivity.isAdminLoggedIn) {
                manageStudents.setVisible(true);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.LogOrReg:
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            case R.id.logout:
                LoginActivity.isLoggedIn = false;
                startActivity(new Intent(this, MainActivity.class));
                Toast.makeText(this, "Logged Out Successfully!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.manageStudents:
                startActivity(new Intent(this, ManageStudentsActivity.class));
                return true;
            case R.id.about:
                startActivity(new Intent(this, AboutUsActivity.class));
                return true;
            case R.id.contact:
                startActivity(new Intent(this, ContactUsActivity.class));
                return true;
            case R.id.gallery:
                startActivity(new Intent(this, GalleryActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
