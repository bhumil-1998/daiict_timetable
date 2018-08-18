package com.example.admin.daiict_timetable;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class New_Timetable extends AppCompatActivity {
    Spinner new_timetable_program,new_timetable_year,new_timetable_day;
    ArrayList arrayList_program,arrayList_year,arrayList_day;
    ArrayAdapter arrayAdapter_program,arrayAdapter_type,arrayAdapter_day;
    EditText subject_new_timetable,timings_new_timetable;
    Button submit_new_timetable;
    ProgressBar progressBar;
    DatabaseReference databaseReference;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__timetable);

        setTitle("New Timetable");

        new_timetable_program=findViewById(R.id.timetable_spinner_program);
        new_timetable_year=findViewById(R.id.timetable_spinner_year);
        new_timetable_day=findViewById(R.id.timetable_spinner_day);
        subject_new_timetable=findViewById(R.id.subject_new_timetable);
        submit_new_timetable=findViewById(R.id.submit_new_timetable);
        timings_new_timetable=findViewById(R.id.timings_new_timetable);
        linearLayout=findViewById(R.id.linear_layout_new_timetable);
        progressBar=findViewById(R.id.progressbar_new_timetable);

        databaseReference= FirebaseDatabase.getInstance().getReference();

        arrayList_program=new ArrayList<String>();
        arrayList_program.addAll(Arrays.asList(getResources().getStringArray(R.array.Program)));
        arrayAdapter_program=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList_program);
        new_timetable_program.setAdapter(arrayAdapter_program);

        arrayList_year=new ArrayList<String>();

        //arrayList_year.addAll(Arrays.asList(getResources().getStringArray(R.array.Year_mscit)));
        new_timetable_program.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Log.d("data",new_timetable_program.getSelectedItemId()+"");
                if (new_timetable_program.getSelectedItemId()==0){
                    arrayAdapter_type=ArrayAdapter.createFromResource(New_Timetable.this,R.array.Year_mscit,android.R.layout.simple_spinner_item);
                    new_timetable_year.setAdapter(arrayAdapter_type);
                    //arrayList_year.addAll(Arrays.asList(getResources().getStringArray(R.array.Year_mscit)));
                }
                else {
                    arrayAdapter_type=ArrayAdapter.createFromResource(New_Timetable.this,R.array.Year_mtech,android.R.layout.simple_spinner_item);
                    new_timetable_year.setAdapter(arrayAdapter_type);
                    //arrayList_year.addAll(Arrays.asList(getResources().getStringArray(R.array.Year_mtech)));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        arrayList_day=new ArrayList();
        arrayList_day.addAll(Arrays.asList(getResources().getStringArray(R.array.day)));
        arrayAdapter_day=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList_day);
        new_timetable_day.setAdapter(arrayAdapter_day);

        submit_new_timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(subject_new_timetable.getText()))
                    subject_new_timetable.setError("Enter subject properly");
                else if (TextUtils.isEmpty(timings_new_timetable.getText()))
                    timings_new_timetable.setError("Enter timinigs");
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    databaseReference.child("TimeTable")
                            .child(new_timetable_program.getSelectedItem().toString())
                            .child(new_timetable_year.getSelectedItem().toString())
                            .child(new_timetable_day.getSelectedItem().toString())
                            .child(subject_new_timetable.getText().toString())
                            .setValue(timings_new_timetable.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Snackbar snackbar=Snackbar.make(linearLayout,"data updated Successfully",Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar snackbar=Snackbar.make(linearLayout,"data updation Failed",Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }
                    });
                    progressBar.setVisibility(View.GONE);

                }
            }
        });
    }

}
