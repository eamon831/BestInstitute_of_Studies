package com.example.bestinstituteofstudies;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "dbRegister";
    public static final String TABLE_NAME = "tblRegister";
    public static final String COL1_ID = "ID";
    public static final String COL2_EMAIL = "Email";
    public static final String COL3_PASSWORD = "Password";
    public static final String COL4_FIRSTNAME = "FirstName";
    public static final String COL5_LASTNAME = "LastName";
    public static final String COL6_ROLE = "Role";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " ( " + COL1_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2_EMAIL + " TEXT, " + COL3_PASSWORD + " TEXT, " + COL4_FIRSTNAME + " TEXT, " + COL5_LASTNAME + " TEXT, " + COL6_ROLE + " TEXT )");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_NAME + " ( " + COL2_EMAIL + " , " + COL3_PASSWORD + " , " + COL4_FIRSTNAME + " , " + COL5_LASTNAME + " , " + COL6_ROLE + " ) VALUES ('admin@admin.com', 'admin123', 'Admin', 'Admin', 'Administrator')");
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_NAME + " ( " + COL2_EMAIL + " , " + COL3_PASSWORD + " , " + COL4_FIRSTNAME + " , " + COL5_LASTNAME + " , " + COL6_ROLE + " ) VALUES ('test@test.com', 'test123', 'Test', 'Tester', 'Student')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void register(String email, String password, String firstName, String lastName) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL2_EMAIL, email);
        cv.put(COL3_PASSWORD, password);
        cv.put(COL4_FIRSTNAME, firstName);
        cv.put(COL5_LASTNAME, lastName);
        cv.put(COL6_ROLE, "Student");
        sqLiteDatabase.insert(TABLE_NAME, null, cv);
    }

    public boolean isExistingUser(String email) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT " + COL1_ID + " FROM " + TABLE_NAME + " WHERE " + COL2_EMAIL + " = '" + email + "' ", null);
        System.out.println(cursor.getCount());
        if (cursor.getCount() != 0 ) {
            cursor.close();
            sqLiteDatabase.close();
            return true;
        }else {
            cursor.close();
            sqLiteDatabase.close();
            return false;
        }
    }

    public boolean login(String email, String password) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT " + COL1_ID + " FROM " + TABLE_NAME + " WHERE " + COL2_EMAIL + " = '" + email + "' " + " AND " + COL3_PASSWORD + " = '" + password + "' ", null);
        System.out.println(cursor.getCount());
        if (cursor.getCount() != 0 ) {
            cursor.close();
            sqLiteDatabase.close();
            return true;
        }else {
            cursor.close();
            sqLiteDatabase.close();
            return false;
        }
    }

    @SuppressLint("Range")
    public ArrayList<String> allStudents() {
        ArrayList<String> studentInfo = new ArrayList<String>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        cursor.moveToFirst();
        do {
            String info = cursor.getString(cursor.getColumnIndex(COL1_ID)) + "," + cursor.getString(cursor.getColumnIndex(COL2_EMAIL)) + "," + cursor.getString(cursor.getColumnIndex(COL4_FIRSTNAME)) + "," + cursor.getString(cursor.getColumnIndex(COL5_LASTNAME)) + ",Edit";
            studentInfo.add(info);
        }while (cursor.moveToNext());
        cursor.close();
        return studentInfo;
    }

    @SuppressLint("Range")
    public ArrayList<String> search(String keyword) {
        ArrayList<String> studentInfo = new ArrayList<String>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL2_EMAIL + " LIKE '%'||?||'%' OR " + COL4_FIRSTNAME + " LIKE '%'||?||'%' OR " + COL5_LASTNAME + " LIKE '%'||?||'%'", new String[]{keyword});
        if(cursor.moveToFirst()) {
            do {
                String info = cursor.getString(cursor.getColumnIndex(COL1_ID)) + "," + cursor.getString(cursor.getColumnIndex(COL2_EMAIL)) + "," + cursor.getString(cursor.getColumnIndex(COL4_FIRSTNAME)) + "," + cursor.getString(cursor.getColumnIndex(COL5_LASTNAME)) + ",Edit";
                studentInfo.add(info);
            }while (cursor.moveToNext());
        }else {
            System.out.println("No student with " + keyword + " " + studentInfo.size());
        }
        cursor.close();
        return studentInfo;
    }

    @SuppressLint("Range")
    public String getStudent(String id) {
        String info = new String();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL1_ID + " LIKE ? ", new String[]{id});
        if(cursor.moveToFirst()) {
            do {
                info = cursor.getString(cursor.getColumnIndex(COL1_ID)) + "," + cursor.getString(cursor.getColumnIndex(COL2_EMAIL)) + "," + cursor.getString(cursor.getColumnIndex(COL3_PASSWORD)) + "," + cursor.getString(cursor.getColumnIndex(COL4_FIRSTNAME)) + "," + cursor.getString(cursor.getColumnIndex(COL5_LASTNAME));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return info;
    }

    @SuppressLint("Range")
    public String getPassword(String email) {
        String password = new String();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT " + COL3_PASSWORD + " FROM " + TABLE_NAME + " WHERE " + COL2_EMAIL + " LIKE '%'||?||'%'", new String[]{email});
        if(cursor.moveToFirst()) {
            do {
                password = cursor.getString(cursor.getColumnIndex(COL3_PASSWORD));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return password;
    }

    public void updateStudent(String id, String email, String password, String firstName, String lastName) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("UPDATE " + TABLE_NAME + " SET " + COL2_EMAIL + " = '" + email + "' , " + COL3_PASSWORD + " = '" + password + "' , " + COL4_FIRSTNAME + " = '" + firstName + "' , " + COL5_LASTNAME + " = '" + lastName + "' WHERE " + COL1_ID + " = " + id);
    }

    /*
    public void deleteAllUser() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE_NAME);
        sqLiteDatabase.execSQL("DELETE FROM sqlite_sequence WHERE name = '" + TABLE_NAME + "'");
    }
     */
}
