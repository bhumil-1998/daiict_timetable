package com.example.admin.daiict_timetable;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Student_home extends AppCompatActivity {
    String program="MSC-IT";
    ListView listView;
    Spinner year_spinner;
    ArrayList year_list;
    DatabaseReference databaseReference;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        setTitle("Student Home");
        //Intent intent=getIntent();
        //program=intent.getStringExtra("program");

        year_spinner=findViewById(R.id.spinner_student_year);
        listView=findViewById(R.id.list_view_student);


        databaseReference= FirebaseDatabase.getInstance().getReference("TimeTable");

        year_list=new ArrayList();
        if (program.equals("MSC-IT"))
            year_list.addAll(Arrays.asList(getResources().getStringArray(R.array.Year_mscit)));
        else
            year_list.addAll(Arrays.asList(getResources().getStringArray(R.array.Year_mtech)));
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,year_list);
        year_spinner.setAdapter(arrayAdapter);

        final ArrayList<Student_list> student_lists=new ArrayList<>();
    


        year_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, final int i, long l) {
                progressDialog=new ProgressDialog(Student_home.this);
                progressDialog.setMessage("Please Wait....");
                progressDialog.show();
                student_lists.clear();
                databaseReference.child(program).child(year_spinner.getSelectedItem().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String[] day_str=getResources().getStringArray(R.array.day);

                        for (int i=0;i<day_str.length;i++){
                            Iterable<DataSnapshot> children=dataSnapshot.child(day_str[i]).getChildren();
                            for (DataSnapshot child: children) {
                                student_lists.add(new Student_list(day_str[i],child.getKey(),child.getValue().toString()));
                            }
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                progressDialog.dismiss();
                Student_adapter student_adapter=new Student_adapter(Student_home.this,R.layout.list_row_student,student_lists);
                listView.setAdapter(student_adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
    }
    public class Student_list{
        String day,subject,timings;


        public Student_list(String day, String subject, String timings) {
            this.day = day;
            this.subject = subject;
            this.timings = timings;

        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getTimings() {
            return timings;
        }

        public void setTimings(String timings) {
            this.timings = timings;
        }
    }

    public class Student_adapter extends ArrayAdapter<Student_list>{
        List students_lists=new ArrayList();
        public Student_adapter(@NonNull Context context, int resource, @NonNull ArrayList<Student_list> student_lists) {
            super(context, resource, student_lists);
            this.students_lists=student_lists;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view=convertView;

            if (view==null){
                view = LayoutInflater.from(Student_home.this).inflate(R.layout.list_row_student,parent,false);
                Student_list current_list= (Student_list) students_lists.get(position);
                TextView  day_row_student=view.findViewById(R.id.day_row_student);
                TextView subject_row_student=view.findViewById(R.id.subject_row_student);
                TextView timings_row_student=view.findViewById(R.id.timings_row_student);
                day_row_student.setText(current_list.getDay());
                subject_row_student.setText(current_list.getSubject());
                timings_row_student.setText(current_list.getTimings());
            }
            return view;

        }
    }
}
