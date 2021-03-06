package com.example.admin.daiict_timetable;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class Registration extends AppCompatActivity {
    //declarations
    DatabaseReference databaseReference;
    ArrayList arrayList_program,arrayList_type;
    ArrayAdapter arrayAdapter_program,arrayAdapter_type;
    Spinner program_spinner,type_spinner;
    Button reset_btn,sign_up_btn;
    EditText student_registration,password_registration;
    ProgressBar progressBar;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setTitle("New Student Registration");
        //type casting
        program_spinner=findViewById(R.id.program_spinner_registration);
        type_spinner=findViewById(R.id.type_spinner_registration);
        reset_btn=findViewById(R.id.reset_registration);
        sign_up_btn=findViewById(R.id.registration_button);
        student_registration=findViewById(R.id.student_registration);
        password_registration=findViewById(R.id.password_registration);
        progressBar=findViewById(R.id.registration_progress);
        linearLayout=findViewById(R.id.linear_layout);

        progressBar.setVisibility(View.GONE);
        //inserting data into spinner
        arrayList_program=new ArrayList<String>();
        arrayList_program.addAll(Arrays.asList(getResources().getStringArray(R.array.Program)));
        arrayAdapter_program=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList_program);
        program_spinner.setAdapter(arrayAdapter_program);

        arrayList_type=new ArrayList<String>();
        arrayList_type.addAll(Arrays.asList(getResources().getStringArray(R.array.Type)));
        arrayAdapter_type=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList_type);
        type_spinner.setAdapter(arrayAdapter_type);

        databaseReference=FirebaseDatabase.getInstance().getReference();

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                student_registration.setText("");
                password_registration.setText("");
            }
        });


        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(student_registration.getText()) && (student_registration.length() < 10 || student_registration.length()>10))
                    student_registration.setError("Enter student id properly");
                else if (TextUtils.isEmpty(password_registration.getText()))
                    password_registration.setError("Enter password");
                else{

                   newUser(student_registration.getText().toString(),password_registration.getText().toString(),program_spinner.getSelectedItem().toString(),type_spinner.getSelectedItem().toString());
                }
            }
        });

    }
    private void newUser(String student_id,String password,String program,String type){
        Register register=new Register(Long.parseLong(student_id),password,program,type);
        progressBar.setVisibility(View.VISIBLE);
        databaseReference.child("user").push().setValue(register).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Snackbar snackbar=Snackbar.make(linearLayout,"SuccessFull",Snackbar.LENGTH_SHORT);
                snackbar.show();
                progressBar.setVisibility(View.GONE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar snackbar=Snackbar.make(linearLayout,"failure",Snackbar.LENGTH_SHORT);
                snackbar.show();
                progressBar.setVisibility(View.GONE);
            }
        });
        
    }
    public static class Register{
        long id;
        String password,program,type;

        public Register(long student_id_register,String password_register,String program_register,String type_register){
            this.id=student_id_register;
            this.password=password_register;
            this.program=program_register;
            this.type=type_register;
        }
    }
}
