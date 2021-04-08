package com.example.mljvc;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.Holder>  {

    private ArrayList<Student> studentArrayList;
    private Callback callback;

    public ArrayList<Student> getStudentArrayList() {
        return studentArrayList;
    }

    public StudentAdapter(ArrayList<Student> studentArrayList, Callback callback) {
        this.studentArrayList = studentArrayList;
        this.callback = callback;
    }


    public void updateData(ArrayList<Student> data) {
        this.studentArrayList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        view = layoutInflater.inflate(R.layout.student_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        View view = holder.getView();
        final Student student = studentArrayList.get(position);

        TextView name;
        TextView born;
        TextView phone;
        TextView major;
        TextView tS;

        ImageView edit;
        ImageView delete;


        name = view.findViewById(R.id.name);
        born = view.findViewById(R.id.born);
        phone = view.findViewById(R.id.phone);
        major = view.findViewById(R.id.major);
        tS = view.findViewById(R.id.tS);
        edit = view.findViewById(R.id.edit);
        delete = view.findViewById(R.id.delete);

        name.setText(student.getName());
        born.setText(student.getBorn());
        phone.setText(student.getPhone());
        major.setText(student.getMajor());
        tS.setText(student.gettS());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onItemDelete(position, student);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onItemEdit(position, student);
            }
        });
    }


    @Override
    public int getItemCount() {
        return studentArrayList.size();
    }

        static class Holder extends RecyclerView.ViewHolder {
            View view;

            public Holder(@NonNull View itemView) {
                super(itemView);
                this.view = itemView;
            }

            View getView() {
                return view;
            }
        }

    public interface Callback {
        void onItemEdit(int index, Student student);

        void onItemDelete(int index, Student student);
    }
}
