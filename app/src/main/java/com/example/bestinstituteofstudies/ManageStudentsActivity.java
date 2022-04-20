package com.example.bestinstituteofstudies;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class ManageStudentsActivity extends BaseActivity {
    DatabaseHelper mydb;
    TableLayout tableLayout;
    EditText keyword;
    TextView result;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar();
        setContentView(R.layout.activity_manage_students);
        getSupportActionBar().setTitle("Manage Students");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mydb = new DatabaseHelper(this);
        tableLayout = findViewById(R.id.studentTable);
        keyword = findViewById(R.id.searchText);
        result = findViewById(R.id.showingResult);
        linearLayout = findViewById(R.id.manageStudents);

        getAllStudents();
        keyword.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN)
                    && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                System.out.println(keyword.getText());
                searchStudents(keyword.getText().toString());
                return true;
            }
            return false;
        });
    }

    private void getAllStudents() {
        ArrayList<String> students = mydb.allStudents();
        tableLayout.removeAllViews();
        createTableHeader();
        createTableRows(students);
        result.setText("Showing " + students.size() + " of " + students.size() + " students");
    }

    private void searchStudents(String keyword) {
        ArrayList<String> students = mydb.search(keyword);
        ArrayList<String> allStudents = mydb.allStudents();
        tableLayout.removeAllViews();
        createTableHeader();
        if (students.size() == 0) {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            TextView noResult = new TextView(this);
            noResult.setBackgroundResource(R.drawable.border);
            noResult.setTextColor(Color.BLACK);
            noResult.setGravity(Gravity.CENTER);
            noResult.setText("\n" + "No student found by searching for: " + keyword + "\n");
            tableLayout.addView(noResult);
        } else {
            createTableRows(students);
        }
        result.setText("Showing " + students.size() + " of " + allStudents.size() + " students");
    }

    private void createTableRows(ArrayList<String> students) {
        for (String info : students) {
            String studentInfo[] = info.split(",");
            String id = studentInfo[0];
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            for (String eachInfo : studentInfo) {
                TextView tv = new TextView(this);
                tv.setBackgroundResource(R.drawable.border);
                tv.setTextColor(Color.BLACK);
                tv.setGravity(Gravity.CENTER);
                tv.setText(eachInfo);
                if (eachInfo.equals("Edit")) {
                    tv.setClickable(true);
                    ContactUsActivity.styleLink(tv);
                    Intent i = new Intent(this, EditStudentDetailsActivity.class);
                    tv.setOnClickListener(view -> {
                        i.putExtra("ID", id);
                        startActivity(i);
                    });
                }
                tableRow.addView(tv);
            }
            tableLayout.addView(tableRow);
        }
    }

    private void createTableHeader() {
        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));

        TextView id = new TextView(this);
        id.setText("ID");
        id.setTextColor(Color.BLACK);
        id.setGravity(Gravity.CENTER);
        id.setTypeface(Typeface.DEFAULT_BOLD);
        id.setBackgroundResource(R.drawable.border);

        TextView email = new TextView(this);
        email.setText("Email");
        email.setTextColor(Color.BLACK);
        email.setGravity(Gravity.CENTER);
        email.setTypeface(Typeface.DEFAULT_BOLD);
        email.setBackgroundResource(R.drawable.border);

        TextView fname = new TextView(this);
        fname.setText("First Name");
        fname.setTextColor(Color.BLACK);
        fname.setGravity(Gravity.CENTER);
        fname.setTypeface(Typeface.DEFAULT_BOLD);
        fname.setBackgroundResource(R.drawable.border);

        TextView lname = new TextView(this);
        lname.setText("Last Name");
        lname.setTextColor(Color.BLACK);
        lname.setGravity(Gravity.CENTER);
        lname.setTypeface(Typeface.DEFAULT_BOLD);
        lname.setBackgroundResource(R.drawable.border);

        TextView action = new TextView(this);
        action.setText("Action");
        action.setTextColor(Color.BLACK);
        action.setGravity(Gravity.CENTER);
        action.setTypeface(Typeface.DEFAULT_BOLD);
        action.setBackgroundResource(R.drawable.border);

        tableRow.addView(id);
        tableRow.addView(email);
        tableRow.addView(fname);
        tableRow.addView(lname);
        tableRow.addView(action);
        tableLayout.addView(tableRow);
    }
}
