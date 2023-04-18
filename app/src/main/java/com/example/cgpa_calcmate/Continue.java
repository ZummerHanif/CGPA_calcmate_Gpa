package com.example.cgpa_calcmate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class Continue extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue);


        double gpa = getIntent().getDoubleExtra("gpa", 0.0);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GPA");
        builder.setMessage("Your GPA is: " + gpa);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog when OK button is clicked
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}