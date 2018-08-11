package com.example.admin.daiict_timetable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class Registration extends AppCompatActivity {

    DatabaseReference databaseReference;
    ArrayList arrayList_program;
    ArrayAdapter arrayAdapter_program;
    Spinner program_spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setTitle("New Student Registration");

        arrayList_program=new ArrayList<String>();
        arrayList_program.addAll(Arrays.asList(getResources().getStringArray(R.array.Program)));
        arrayAdapter_program=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList_program);
        spinner_question.setAdapter(arrayAdapter_program);

        databaseReference=FirebaseDatabase.getInstance().getReference("user");
    }
}
