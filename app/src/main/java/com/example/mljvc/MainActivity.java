package com.example.mljvc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements StudentAdapter.Callback {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        initView();
        setAction();
        fakeData();

    }

    private boolean editing = false;
    private int currentIndexEditing = -1;
    private int idIndexEditing = -1;
    private String key = "";

    private ArrayList<Student> currentStudentArrayList;
    private StudentAdapter studentAdapter;
    private RecyclerView recyclerView;

    private ImageView add;
    private ImageView unSort;
    private ImageView find;

    private TextView sortByName;
    private TextView sortByBorn;
    private TextView sortByPhone;
    private TextView titleCurrentSort;

    private EditText searchEditText;
    private EditText name;
    private EditText born;
    private EditText phone;
    private EditText major;
    private RadioGroup tSGroup;
    private RadioButton university;
    private RadioButton college;

    private void changeStatus() {
        editing = false;
        add.setImageResource(R.drawable.white_add);
        clearText();
    }

    private void clearText() {
        name.setText("");
        born.setText("");
        phone.setText("");
        major.setText("");
        searchEditText.setText("");
    }

    private void setAction() {
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key = searchEditText.getText().toString();
                Log.e("Key : ", key);
                findByKey(key);
                hideSoftKeyboard();
            }
        });

        searchEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                key = searchEditText.getText().toString();
                Log.e("Key : ", key);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editing) {
                    if (checkInput()) {
                        updateStudent();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please type full", Toast.LENGTH_SHORT).show();
                        Log.e("Add Student:", "error");
                    }
                } else {
                    if (checkInput()) {
                        addStudent();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please type full", Toast.LENGTH_SHORT).show();
                        Log.e("Add Student:", "error");
                    }
                }
            }

        });

        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        born.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        major.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        sortByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSortByName();
                unSort.setVisibility(View.VISIBLE);

            }
        });

        sortByBorn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSortByBorn();
                unSort.setVisibility(View.VISIBLE);

            }
        });

        sortByPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSortByPhone();
                unSort.setVisibility(View.VISIBLE);
            }
        });

        unSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentKey = searchEditText.getText().toString();
                findByKey(currentKey);
                unSort.setVisibility(View.INVISIBLE);
                titleCurrentSort.setText("Normal sort");

            }
        });
    }

    private void setSortByName() {
        ArrayList<Student> students = studentAdapter.getStudentArrayList();
        Comparator<Student> compareById = (Student o1, Student o2) -> o1.getName().compareTo(o2.getName());
        Collections.sort(students, compareById);
        this.studentAdapter.updateData(students);
        this.titleCurrentSort.setText("Sort by name");

    }

    private void setSortByBorn() {
        ArrayList<Student> students = studentAdapter.getStudentArrayList();
        Comparator<Student> compareById = (Student o1, Student o2) -> o1.getBorn().compareTo(o2.getBorn());
        Collections.sort(students, compareById);
        this.studentAdapter.updateData(students);
        this.titleCurrentSort.setText("Sort by born");

//        Collections.sort(employees, compareById.reversed());
    }

    private void setSortByPhone() {
        ArrayList<Student> students = studentAdapter.getStudentArrayList();
        Comparator<Student> compareById = (Student o1, Student o2) -> o1.getPhone().compareTo(o2.getPhone());
        Collections.sort(students, compareById);
        this.studentAdapter.updateData(students);
        this.titleCurrentSort.setText("Sort by phone");

//        Collections.sort(employees, compareById.reversed());
    }

    private void findByKey(String key) {
        ArrayList<Student> students = this.currentStudentArrayList;
        ArrayList<Student> studentsFiltered = new ArrayList<>();
        for (Student d : students) {
            if (d.getLink().contains(key)) {
                studentsFiltered.add(d);
            }
        }
        studentAdapter.updateData(studentsFiltered);
        Log.e("Size:", studentsFiltered.size() + "");

    }

    private void addStudent() {
        Student student = getInput();
        if (checkInvalidPhone(student)) {
            currentStudentArrayList.add(student);
            studentAdapter.updateData(currentStudentArrayList);
            clearText();
            Toast.makeText(getApplicationContext(), "Complete", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getApplicationContext(), "Duplicate phone", Toast.LENGTH_SHORT).show();
        }

    }

    private void updateStudent() {
        Student student = getInput();
        if (checkInvalidPhone(student)) {
            for (Student d : this.currentStudentArrayList) {
                if (d.getId() == idIndexEditing) {
                    currentIndexEditing = this.currentStudentArrayList.indexOf(d);
                    break;
                }
            }
            currentStudentArrayList.set(currentIndexEditing, getInput());
            studentAdapter.updateData(currentStudentArrayList);
            changeStatus();
            clearText();
            Toast.makeText(getApplicationContext(), "Complete", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getApplicationContext(), "Duplicate phone", Toast.LENGTH_SHORT).show();
        }


    }

    private boolean checkInput() {
        if (name.getText().toString().equals("") || born.getText().toString().equals("") || phone.getText().toString().equals("")
                || major.getText().toString().equals("")) {
            return false;
        }
        return true;
    }

    private Student getInput() {
        String tS;
        if (university.isChecked()) {
            tS = "Dai Hoc";
        } else {
            tS = "Cao Dang";
        }
        Student student = new Student(name.getText().toString(), born.getText().toString(), phone.getText().toString(), major.getText().toString(), tS);
        return student;
    }

    private void fakeData() {
        Student student = new Student("Ngoc Anh", "1999", "0568967660", "CNTT", "Dai Hoc");
        Student student1 = new Student("Bao Anh", "1998", "0768967660", "CNTT", "Dai Hoc");
        Student student2 = new Student("Tram Anh", "1997", "0968967660", "CNTT", "Dai Hoc");
        Student student3 = new Student("Lan Anh", "2000", "0168967660", "CNTT", "Dai Hoc");
        this.currentStudentArrayList.add(student);
        this.currentStudentArrayList.add(student1);
        this.currentStudentArrayList.add(student2);
        this.currentStudentArrayList.add(student3);
        studentAdapter.updateData(currentStudentArrayList);

    }

    private void initView() {
        add = findViewById(R.id.add);
        searchEditText = findViewById(R.id.search);
        find = findViewById(R.id.find);
        unSort = findViewById(R.id.un_sort);
        titleCurrentSort = findViewById(R.id.sort_by);
        name = findViewById(R.id.add_name);
        born = findViewById(R.id.add_born);
        phone = findViewById(R.id.add_phone);
        major = findViewById(R.id.add_major);

        tSGroup = findViewById(R.id.tS_group);
        university = findViewById(R.id.university);
        university.setChecked(true);
        college = findViewById(R.id.college);

        sortByName = findViewById(R.id.sort_by_name);
        sortByBorn = findViewById(R.id.sort_by_born);
        sortByPhone = findViewById(R.id.sort_by_phone);

        recyclerView = findViewById(R.id.recycler_view);

        currentStudentArrayList = new ArrayList<>();

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        studentAdapter = new StudentAdapter(this.currentStudentArrayList, this);
        recyclerView.setAdapter(studentAdapter);
    }

    @Override
    public void onItemEdit(int index, Student student) {
        add.setImageResource(R.drawable.white_check);
        this.editing = true;
        this.idIndexEditing = student.getId();
        this.currentIndexEditing = index;
        fillData(student);

    }

    @Override
    public void onItemDelete(int index, Student student) {
        if (editing) {
            Toast.makeText(this, "Please save after delete", Toast.LENGTH_SHORT).show();
        } else {
            this.currentStudentArrayList.remove(student);
            String currentKey = searchEditText.getText().toString();
            findByKey(currentKey);
            Toast.makeText(getApplicationContext(), "Complete", Toast.LENGTH_SHORT).show();
        }
    }

    private void fillData(Student student) {
        name.setText(student.getName());
        born.setText(student.getBorn());
        phone.setText(student.getPhone());
        major.setText(student.getMajor());
        if (student.gettS().equals("Dai Hoc")) {
            university.setChecked(true);
        } else {
            college.setChecked(true);
        }
    }

    private boolean checkInvalidPhone(Student student) {
        String phone = student.getPhone();
        if (editing) {
            for (Student d : this.currentStudentArrayList) {
                if (d.getId() != student.getId() && d.getId() != this.idIndexEditing) {
                    if (d.getPhone().equals(phone)) {
                        return false;
                    }
                }
            }
        } else {
            for (Student d : this.currentStudentArrayList) {
                if (d.getId() != student.getId()) {
                    if (d.getPhone().equals(phone)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}