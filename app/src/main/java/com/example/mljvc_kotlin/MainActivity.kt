package com.example.mljvc_kotlin

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import java.util.*

class MainActivity : AppCompatActivity(), StudentAdapter.Callback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)

        initView()
        setAction()
        fakeData()
    }

    private lateinit var add: ImageView
    private lateinit var unSort: ImageView
    private lateinit var find: ImageView

    private lateinit var sortByName: TextView
    private lateinit var sortByBorn: TextView
    private lateinit var sortByPhone: TextView
    private lateinit var titleCurrentSort: TextView

    private lateinit var searchEditText: EditText
    private lateinit var nameEditText: EditText
    private lateinit var bornEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var majorEditText: EditText
    private lateinit var tSGroup: RadioGroup
    private lateinit var university: RadioButton
    private lateinit var college: RadioButton
    private lateinit var recyclerView: RecyclerView

//<<<<<<< HEAD
//=======
    private var backUpStudentArrayList: MutableList<Student> = mutableListOf()
    private var currentStudentArrayList: MutableList<Student> = mutableListOf()

//>>>>>>> convert-kotlin
    private var studentAdapter: StudentAdapter? = null

    private var editing: Boolean = false
    private var indexEditing: Int = -1
    private var idIndexEditing: Int = -1
    private var currentKey: String? = ""


    private fun setAction() {
        sortByName.setOnClickListener {
            setSortByName()
            unSort.visibility = View.VISIBLE

        }

        sortByBorn.setOnClickListener {
            setSortByBorn()
            unSort.visibility = View.VISIBLE


        }
        sortByPhone.setOnClickListener {
            setSortByPhone()
            unSort.visibility = View.VISIBLE

        }

//      no action
        unSort.setOnClickListener {
            val currentKey = searchEditText.text.toString()
            recyclerView.adapter = StudentAdapter(currentStudentArrayList, this)
//            findWithKey(currentKey)
            unSort.visibility = View.INVISIBLE
            titleCurrentSort.text = "Normal sort"

        }

        find.setOnClickListener {
            findWithKey(searchEditText.text.toString())
            hideKeyboard()
        }

        add.setOnClickListener {
            if (checkInput()){
                if (editing){
                    updateStudent()
                } else{
                    addStudent()
                }

            } else{
                Toast.makeText(applicationContext, "Please type full", Toast.LENGTH_SHORT)
                    .show()
                Log.e("Add Student:", "error")

            }
        }
    }

    private fun setSortByName() {
        var students: MutableList<Student> = mutableListOf()
        students = currentStudentArrayList
        students.sortBy { it.name }
        recyclerView.adapter = StudentAdapter(students,this)
        titleCurrentSort.text = "Sort by name"
    }
    private fun setSortByBorn() {
        var students: MutableList<Student> = mutableListOf()
        students = currentStudentArrayList
        students.sortBy { it.born }
        recyclerView.adapter = StudentAdapter(students,this)
        titleCurrentSort.text = "Sort by born"
    }
    private fun setSortByPhone() {
        var students: MutableList<Student> = mutableListOf()
        students = currentStudentArrayList
        students.sortBy { it.phone }
        recyclerView.adapter = StudentAdapter(students,this)
        titleCurrentSort.text = "Sort by phone"
    }

    private fun addStudent() {
        var student: Student = getInput()
        if (checkInvalidPhone(student)) {
            currentStudentArrayList.add(student)
            Log.e("Size:", currentStudentArrayList.size.toString() + "")

//           ************* KHONG RELOAD
            studentAdapter?.updateData(currentStudentArrayList)

//           ************* CO RELOAD
            recyclerView.adapter = StudentAdapter(currentStudentArrayList,this)


            clearText()
            Toast.makeText(applicationContext, "Complete", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "Duplicate phone", Toast.LENGTH_SHORT).show()
        }
    }

//      no action
    private fun checkInvalidPhone(student: Student): Boolean {
        return true
    }

    private fun initView() {
        add = findViewById(R.id.add)
        searchEditText = findViewById(R.id.search)
        find = findViewById(R.id.find)
        unSort = findViewById(R.id.un_sort)
        titleCurrentSort = findViewById(R.id.sort_by)

        nameEditText = findViewById(R.id.add_name)
        bornEditText = findViewById(R.id.add_born)
        phoneEditText = findViewById(R.id.add_phone)
        majorEditText = findViewById(R.id.add_major)

        tSGroup = findViewById(R.id.tS_group)
        university = findViewById(R.id.university)
        university.isChecked = true
        college = findViewById(R.id.college)

        sortByName = findViewById(R.id.sort_by_name)
        sortByBorn = findViewById(R.id.sort_by_born)
        sortByPhone = findViewById(R.id.sort_by_phone)

        recyclerView = findViewById(R.id.recycler_view)

        currentStudentArrayList = java.util.ArrayList()

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(1, RecyclerView.VERTICAL)
        recyclerView.layoutManager = staggeredGridLayoutManager
        recyclerView.adapter = StudentAdapter(currentStudentArrayList, this)
    }

    private fun Context.hideKeyboard() {
        val view: View?= getCurrentFocus()
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun fillData(student: Student) {
        nameEditText.setText(student.name)
        bornEditText.setText(student.born)
        phoneEditText.setText(student.phone)
        majorEditText.setText(student.major)
        if (student.tS == "Dai Hoc") {
            university.isChecked = true
        } else {
            college.isChecked = true
        }
    }

    private fun fakeData() {
        val student = Student("Ngoc Anh", "1999", "0568967660", "CNTT", "Dai Hoc")
        val student1 = Student("Bao Anh", "1998", "0768967660", "CNTT", "Dai Hoc")
        val student2 = Student("Tram Anh", "1997", "0968967660", "CNTT", "Dai Hoc")
        val student3 = Student("Lan Anh", "2000", "0168967660", "CNTT", "Dai Hoc")
        currentStudentArrayList.add(student)
        currentStudentArrayList.add(student1)
        currentStudentArrayList.add(student2)
        currentStudentArrayList.add(student3)
        studentAdapter?.updateData(currentStudentArrayList)
    }

    private fun clearText() {
        nameEditText.text.clear()
        bornEditText.text.clear()
        phoneEditText.text.clear()
        majorEditText.text.clear()
        searchEditText.text.clear()
    }

    private fun changeStatus() {
        editing = false
        add.setImageResource(R.drawable.white_add)
        clearText()
    }

    private fun checkInput(): Boolean {
        return !(nameEditText.text.isEmpty() || bornEditText.text.isEmpty() || phoneEditText.text.isEmpty() || majorEditText.text.isEmpty())
    }

    private fun getInput(): Student{
        var tS = if (university.isChecked) {
            "Dai Hoc"
        } else {
            "Cao Dang"
        }
        return Student(nameEditText.text.toString(), bornEditText.text.toString(), phoneEditText.text.toString(), majorEditText.text.toString(),tS)
    }

    override fun onDelete(index: Int, student: Student) {
        if(editing) Toast.makeText(this, "Please save after delete", Toast.LENGTH_SHORT).show()
        else {
            currentStudentArrayList.remove(student)
            recyclerView.adapter = StudentAdapter(currentStudentArrayList,this)
            findWithKey(searchEditText.text.toString())

        }
    }

    override fun onEdit(index: Int, student: Student) {
        add.setImageResource(R.drawable.white_check)
        editing = true
        idIndexEditing = index
        indexEditing = index
        fillData(student)
        Log.e("Edit", " done")

    }

    private fun findWithKey(key: String){
        val students = currentStudentArrayList
        val studentsFiltered: MutableList<Student>?= mutableListOf()
        for (d in students) {
            if (d.link.contains(key)) {
                studentsFiltered?.add(d)
            }
        }
        recyclerView.adapter = studentsFiltered?.let { StudentAdapter(it,this) }
        Log.e("Size:", studentsFiltered?.size.toString() + "")
    }

    private fun updateStudent(){
        val student = getInput()
        if (checkInvalidPhone(student)) {
//            for (d in currentStudentArrayList) {
//                if (d.getId() === idIndexEditing) {
//                    currentIndexEditing = currentStudentArrayList.indexOf(d)
//                    break
//                }
//            }
            currentStudentArrayList[indexEditing] = getInput()
            recyclerView.adapter = StudentAdapter(currentStudentArrayList, this)
            changeStatus()
            clearText()
            Toast.makeText(applicationContext, "Complete", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "Duplicate phone", Toast.LENGTH_SHORT).show()
        }

    }

}


