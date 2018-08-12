package com.example.admin.daiict_timetable;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Login extends AppCompatActivity {
    //declaration
    Button sign_in_btn;
    EditText student_id_login,password_login;
    DatabaseReference databaseReference;
    LinearLayout linearLayout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setTitle("Login");
        //type casting
        sign_in_btn=findViewById(R.id.email_sign_in_button);
        student_id_login=findViewById(R.id.student_id_login);
        password_login=findViewById(R.id.password_login);
        linearLayout=findViewById(R.id.linear_login);
        progressBar=findViewById(R.id.progressbar_login);


        databaseReference= FirebaseDatabase.getInstance().getReference("user");

        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(student_id_login.getText()) && (student_id_login.length() < 10 || student_id_login.length()>10))
                    student_id_login.setError("Enter student id properly");
                else if (TextUtils.isEmpty(password_login.getText()))
                    password_login.setError("Enter password");
                else{
                    progressBar.setVisibility(View.VISIBLE);

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            boolean check=false;
                            int type_checking=0;
                            Iterable<DataSnapshot> children=dataSnapshot.getChildren();
                            for (DataSnapshot child:children) {


                                /*Log.d("data", child.child("type").getValue().toString());
                                Log.d("data", child.child("program").getValue().toString());
                                Log.d("data", child.child("password").getValue().toString());*/

                                if (student_id_login.getText().toString().equals(child.child("id").getValue().toString()) && password_login.getText().toString().equals(child.child("password").getValue().toString())) {
                                    if (child.child("type").getValue().toString().equals("Admin")){
                                        type_checking=1;
                                    }
                                    else if (child.child("type").getValue().toString().equals("Student")){
                                        type_checking=2;
                                    }
                                    else if (child.child("type").getValue().toString().equals("Faculty")){
                                        type_checking=3;
                                    }
                                    progressBar.setVisibility(View.GONE);
                                    check=true;
                                    break;
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    check=false;
                                }
                            }
                            if (check==true){
                                if (type_checking==1) {
                                    //Admin
                                    startActivity(new Intent(Login.this, Admin_home.class));
                                } else if (type_checking==2) {
                                    //student
                                    startActivity(new Intent(Login.this, Student_home.class));
                                }
                                else if (type_checking==3){
                                    //faculty
                                    startActivity(new Intent(Login.this, Faculty_home.class));
                                }
                            }
                            else {
                                Snackbar snackbar = Snackbar.make(linearLayout, "Invalid login credentials", Snackbar.LENGTH_SHORT);
                                snackbar.show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Snackbar snackbar=Snackbar.make(linearLayout,"Process Cancelled",Snackbar.LENGTH_SHORT);
                            progressBar.setVisibility(View.GONE);
                            snackbar.show();
                        }
                    });
                }

            }
        });
    }
}
