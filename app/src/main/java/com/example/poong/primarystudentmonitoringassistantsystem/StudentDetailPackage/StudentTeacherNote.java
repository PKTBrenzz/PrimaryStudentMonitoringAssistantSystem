package com.example.poong.primarystudentmonitoringassistantsystem.StudentDetailPackage;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.poong.primarystudentmonitoringassistantsystem.R;
import com.example.poong.primarystudentmonitoringassistantsystem.Teacher.Teacher;
import com.example.poong.primarystudentmonitoringassistantsystem.Teacher.TeacherProfile;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentTeacherNote extends Fragment {

    ArrayList<TeacherNote> teacherNotes = new ArrayList<>();
    RecyclerView recyclerView;
    NoteAdapter noteAdapter;

    public StudentTeacherNote() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_teacher_note, container, false);

        recyclerView = view.findViewById(R.id.note_list);
        noteAdapter = new NoteAdapter(teacherNotes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(noteAdapter);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("student_note").child(getArguments().getString("studentID"));
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                TeacherNote note = dataSnapshot.getValue(TeacherNote.class);
                teacherNotes.add(note);
                noteAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }



    private class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder>{

        public ArrayList<TeacherNote> teacherNotes = new ArrayList<>();

        public NoteAdapter(ArrayList<TeacherNote> list){
            teacherNotes = list;
        }

        @Override
        public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_teacher_note, parent, false);

            return new NoteViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull NoteViewHolder holder, final int position) {
            holder.teacher_name.setText(teacherNotes.get(position).name);
            holder.note.setText(teacherNotes.get(position).content);
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), TeacherProfile.class);
                    intent.putExtra("ID", teacherNotes.get(position).id);
                    view.getContext().startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return teacherNotes.size();
        }
    }

    private class NoteViewHolder extends RecyclerView.ViewHolder{
        TextView teacher_name, note;
        View mView;

        public NoteViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            teacher_name = itemView.findViewById(R.id.teacher_name);
            note = itemView.findViewById(R.id.note);
        }
    }
}
