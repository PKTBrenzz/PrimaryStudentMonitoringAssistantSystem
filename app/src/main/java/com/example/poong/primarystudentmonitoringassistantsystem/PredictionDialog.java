package com.example.poong.primarystudentmonitoringassistantsystem;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PredictionDialog extends android.support.v4.app.DialogFragment {

    private EditText mGenderText, mAttendanceText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        setContentView(R.layout.activity_prediction_dialog);
        final String studentID = getArguments().getString("studentID");

        getAbsencesCount(studentID);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_prediction_dialog, null);

        mGenderText = view.findViewById(R.id.gender);
        String gender = getArguments().getString("gender");
        if(gender.equals("Male")){
            gender = "M";
        }else{
            gender = "F";
        }

        mGenderText.setText(gender);

        builder.setView(view);
        builder.setPositiveButton("Predict", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                Toast.makeText(
                        getActivity().getApplicationContext(),
                        studentID,
                        Toast.LENGTH_LONG
                ).show();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        return builder.create();

    }

    private void getAbsencesCount(String studentID) {
    }
}
