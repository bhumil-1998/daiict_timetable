package com.example.admin.daiict_timetable;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Student_home extends AppCompatActivity {
    String program;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        Intent intent=getIntent();
        program=intent.getStringExtra("program");
    }
}
