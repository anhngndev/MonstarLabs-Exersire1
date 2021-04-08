package com.example.mljvc_kotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class StudentAdapter(
    var studentArrayList: MutableList<Student>,
    var callback: Callback
) : RecyclerView.Adapter<StudentAdapter.Holder>() {


    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.name)
        var born: TextView = view.findViewById(R.id.born)
        var phone: TextView = view.findViewById(R.id.phone)
        var major: TextView = view.findViewById(R.id.major)
        var tS: TextView = view.findViewById(R.id.tS)

        var edit: ImageView = view.findViewById(R.id.edit)
        var delete: ImageView = view.findViewById(R.id.delete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.student_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return studentArrayList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val student: Student = studentArrayList[position]
        holder.name.text = student.name
        holder.born.text = student.born
        holder.phone.text = student.phone
        holder.major.text = student.major
        holder.tS.text = student.tS

        holder.edit.setOnClickListener {
            callback.onEdit(position, student)
        }
        holder.delete.setOnClickListener {
            callback.onDelete(position, student)
        }
    }

    fun updateData(data: MutableList<Student>) {
        studentArrayList = data
        notifyDataSetChanged()
    }

    fun getMutableList(): MutableList<Student>{
        return studentArrayList
    }

    interface Callback {
        fun onDelete(index: Int, student: Student)
        fun onEdit(index: Int, student: Student)
    }

}