package com.example.cgpa_calcmate;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvObject;
    Button button;



    @Override
    public <T extends View> T findViewById(int id) {
        return super.findViewById(id);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvObject=findViewById(R.id.textView);
        button =findViewById(R.id.Gpa_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Create an Intent to start the "GPA" activity
                Intent intent = new Intent(MainActivity.this, GPA.class);
                startActivity(intent);
            }
        });

    }
}