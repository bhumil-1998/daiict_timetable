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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Faculty_home extends AppCompatActivity {
    String program;
    ListView listView_faculty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_home);
        setTitle("Faculty Home");
        Intent intent=getIntent();
        program=intent.getStringExtra("program");

        listView_faculty=findViewById(R.id.listview_faculty);
        ArrayList<Faculty_list> faculty_lists=new ArrayList<>();
        faculty_lists.add(new Faculty_list("Tuesday","Maths","1-2"));

        Faculty_adapter faculty_adapter=new Faculty_adapter(this,R.layout.list_row,faculty_lists);
        listView_faculty.setAdapter(faculty_adapter);
    }

    public class Faculty_list{
        String day,subject,timings;

        public Faculty_list(String day, String subject, String timings) {
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

    public class Faculty_adapter extends ArrayAdapter<Faculty_list>{
        List faculty_lists=new ArrayList<Faculty_list>();

        public Faculty_adapter(@NonNull Context context, int resource, ArrayList<Faculty_list> faculty_lists) {
            super(context, resource,faculty_lists);
            this.faculty_lists = faculty_lists;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view=convertView;
            if (view==null)
                view=LayoutInflater.from(Faculty_home.this).inflate(R.layout.list_row,parent,false);
            Faculty_list current_list= (Faculty_list) faculty_lists.get(position);
            Log.d("msg1","view method");
            TextView day_text=view.findViewById(R.id.day_row);
            TextView subject_text=view.findViewById(R.id.subject_row);
            TextView timings_text=view.findViewById(R.id.timings_row);
            Button cancel_row=view.findViewById(R.id.cancel_row);
            day_text.setText(current_list.getDay());
            subject_text.setText(current_list.getSubject());
            timings_text.setText(current_list.getTimings());

            return view;
        }
    }
}