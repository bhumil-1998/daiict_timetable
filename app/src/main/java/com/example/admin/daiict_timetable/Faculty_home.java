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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Faculty_home extends AppCompatActivity {

    ListView listView_faculty;
    DatabaseReference databaseReference;
    Spinner program_spinner, year_spinner;
    ArrayAdapter arrayAdapter_program;
    ArrayList arrayList_program, year_list;
    final ArrayList<Faculty_list> faculty_lists = new ArrayList<>();
    static int separator = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_home);
        setTitle("Faculty Home");

        databaseReference = FirebaseDatabase.getInstance().getReference("TimeTable");

        listView_faculty = findViewById(R.id.listview_faculty);
        program_spinner = findViewById(R.id.program_spinner_faculty);
        year_spinner = findViewById(R.id.year_spinner_faculty);

        arrayList_program = new ArrayList<String>();
        arrayList_program.addAll(Arrays.asList(getResources().getStringArray(R.array.Program)));
        arrayAdapter_program = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList_program);
        program_spinner.setAdapter(arrayAdapter_program);




        year_list = new ArrayList();
        year_list.addAll(Arrays.asList(getResources().getStringArray(R.array.Year_mscit)));
        program_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (program_spinner.getSelectedItem().equals("MTech")) {
                    year_list.clear();
                    year_list.addAll(Arrays.asList(getResources().getStringArray(R.array.Year_mtech)));
                }

                getData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        year_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getData();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, year_list);
        year_spinner.setAdapter(arrayAdapter);




    }

    public void getData() {
        databaseReference.child(program_spinner.getSelectedItem().toString()).child(year_spinner.getSelectedItem().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String[] day_str = getResources().getStringArray(R.array.day);
                faculty_lists.clear();
                for (int i = 0; i < day_str.length; i++) {


                    Iterable<DataSnapshot> children = dataSnapshot.child(day_str[i]).getChildren();
                    for (DataSnapshot child : children) {
                        //Log.d("data12", child.toString());
                        faculty_lists.add(new Faculty_list(day_str[i],child.getKey().toString(),child.getValue().toString()));
                    }
                }
                Faculty_adapter faculty_adapter = new Faculty_adapter(Faculty_home.this, R.layout.list_row, faculty_lists);
                listView_faculty.setAdapter(faculty_adapter);
                //faculty_lists.add(new Faculty_list(dataSnapshot.child("MSC-IT").child("First Year").getValue().toString(),"Maths","1-2"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public class Faculty_list {
        String day, subject, timings;

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

    public class Faculty_adapter extends ArrayAdapter<Faculty_list> {
        List faculty_lists = new ArrayList<Faculty_list>();

        public Faculty_adapter(@NonNull Context context, int resource, ArrayList<Faculty_list> faculty_lists) {
            super(context, resource, faculty_lists);
            this.faculty_lists = faculty_lists;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = convertView;

            if (view == null) {
                if (Faculty_home.separator == 0) {
                    view = LayoutInflater.from(Faculty_home.this).inflate(R.layout.list_row, parent, false);
                    Faculty_list current_list = (Faculty_list) faculty_lists.get(position);
                    TextView day_text = view.findViewById(R.id.day_row);
                    TextView subject_text = view.findViewById(R.id.subject_row);
                    TextView timings_text = view.findViewById(R.id.timings_row);
                    Button cancel_row = view.findViewById(R.id.cancel_row);
                    day_text.setText(current_list.getDay());
                    subject_text.setText(current_list.getSubject());
                    timings_text.setText(current_list.getTimings());
                    
                }
            }
            return view;
        }
    }


}