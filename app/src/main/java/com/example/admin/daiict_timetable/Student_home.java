package com.example.admin.daiict_timetable;

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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Student_home extends AppCompatActivity {
    //String program;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        setTitle("Student Home");
        //Intent intent=getIntent();
        //program=intent.getStringExtra("program");

        listView=findViewById(R.id.list_view_student);
        ArrayList<Student_list> student_lists=new ArrayList<>();
        student_lists.add(new Student_list("asdf","qwer","12-13"));
        Student_adapter student_adapter=new Student_adapter(this,R.layout.list_row_student,student_lists);
        listView.setAdapter(student_adapter);
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
