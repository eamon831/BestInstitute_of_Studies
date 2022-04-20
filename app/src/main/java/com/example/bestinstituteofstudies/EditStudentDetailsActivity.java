package com.example.bestinstituteofstudies;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditStudentDetailsActivity extends BaseActivity {
    EditText studentEmail, studentPassword, studentFirstName, studentLastName;
    TextView studentFullName, studentId;
    DatabaseHelper mydb;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar();
        setContentView(R.layout.activity_edit_student_details);
        getSupportActionBar().setTitle("Update Student's Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mydb = new DatabaseHelper(this);
        studentFullName = findViewById(R.id.studentFullName);
        studentId = findViewById(R.id.idNotEditable);
        studentEmail = findViewById(R.id.editStudentEmail);
        studentPassword = findViewById(R.id.editStudentPassword);
        studentFirstName = findViewById(R.id.editStudentFirstName);
        studentLastName = findViewById(R.id.editStudentLastName);
        update = findViewById(R.id.updateStudentDetails);

        String id = getIntent().getStringExtra("ID");
        getStudentDetails(id);
        update.setOnClickListener(view -> {
            if (RegisterActivity.checkName()) {
                updateStudentDetails();
                getStudentDetails(studentId.getText().toString());
                Toast.makeText(this, "Student Details updated!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getStudentDetails(String id) {
        String studentInfo[] = mydb.getStudent(id).split(",");
        System.out.println(studentInfo);
        studentId.setText(studentInfo[0]);
        studentEmail.setText(studentInfo[1]);
        studentPassword.setText(studentInfo[2]);
        studentFirstName.setText(studentInfo[3]);
        studentLastName.setText(studentInfo[4]);
        studentFullName.setText(studentInfo[3] + " " + studentInfo[4]);
    }

    public void updateStudentDetails() {
        mydb.updateStudent(studentId.getText().toString(), studentEmail.getText().toString(), studentPassword.getText().toString(), studentFirstName.getText().toString(), studentLastName.getText().toString());
    }
}
