package com.example.cgpa_calcmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GPA extends AppCompatActivity {

    private Button btnContinue;
    private boolean isContinueButtonEnabled = false;
    private List<Double> creditHoursList = new ArrayList<>();
    private List<Double> obtainedMarksList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa);

        Spinner spinner = findViewById(R.id.spinner1);
        String[] spinnerValues = {"Choose", "1", "2", "3", "4", "5", "6", "7", "8"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerValues);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        LinearLayout layout = findViewById(R.id.subjectsLayout);

        btnContinue = findViewById(R.id.btn_continue);
        btnContinue.setEnabled(isContinueButtonEnabled);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedValue = parent.getItemAtPosition(position).toString();
                if (position == 0) {
                    layout.removeAllViews();
                    isContinueButtonEnabled = false;
                    btnContinue.setEnabled(false);
                } else {
                    subjects(selectedValue);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isContinueButtonEnabled) {
                    double totalCreditHours = 0.0;
                    double totalQualityPoints = 0.0;

                    for (int i = 0; i < obtainedMarksList.size(); i++) {
                        double creditHours = creditHoursList.get(i);
                        double obtainedMarks = obtainedMarksList.get(i);
                        double Qualitypoints=calculateQualityPoints(obtainedMarks)*creditHours;
                        totalCreditHours =totalCreditHours +  creditHours;
                        totalQualityPoints =totalQualityPoints+ Qualitypoints;
                    }

                    double gpa = totalQualityPoints / totalCreditHours;

                    // Add this code inside the onClick method of btnContinue.setOnClickListener

                    StringBuilder creditHoursData = new StringBuilder("Credit Hours: ");
                    StringBuilder qualityPointsData = new StringBuilder("Quality Points: ");
                    StringBuilder obtainedmarksdata = new StringBuilder("Obtained marks: ");


                    for (int i = 0; i < creditHoursList.size(); i++) {
                        double creditHours = creditHoursList.get(i);
                        double obtainedMarks = obtainedMarksList.get(i);
                        double qualityPoints = calculateQualityPoints(obtainedMarks) * creditHours;

                        creditHoursData.append(creditHours).append(", ");
                        qualityPointsData.append(qualityPoints).append(", ");
                        obtainedmarksdata.append(obtainedMarks).append(",");
                    }

// Remove the last ", " from the strings
                    creditHoursData.setLength(creditHoursData.length() - 2);
                    qualityPointsData.setLength(qualityPointsData.length() - 2);
                    obtainedmarksdata.setLength(obtainedmarksdata.length() - 2);

// Display the data in a Toast message
                    Toast.makeText(GPA.this, creditHoursData.toString() + "\n" + qualityPointsData.toString()+"\n"+obtainedmarksdata.toString(), Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(GPA.this, Continue.class);
                    intent.putExtra("gpa", gpa);
                    startActivity(intent);
                } else {
                    Toast.makeText(GPA.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void subjects(String sub) {
        LinearLayout layout = findViewById(R.id.subjectsLayout);
        int numSubjects = Integer.parseInt(sub);

        String[] spinnerValues = {"1", "2", "3", "4"};
       // layout.removeAllViews();
      //  creditHoursList.clear();
     //   obtainedMarksList.clear();

        for (int i = 1; i <= numSubjects; i++) {
            TextView textView = new TextView(this);
            LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            textViewParams.gravity = Gravity.START;
            textView.setLayoutParams(textViewParams);
            textView.setText("Subject " + i + ": ");
            layout.addView(textView);

            Spinner spinner = new Spinner(this);
            LinearLayout.LayoutParams spinnerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            spinnerParams.gravity = Gravity.END;
            spinner.setLayoutParams(spinnerParams);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerValues);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            layout.addView(spinner);

            EditText editText = new EditText(this);
            LinearLayout.LayoutParams editTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            editTextParams.gravity = Gravity.END;
            editText.setLayoutParams(editTextParams);
            editText.setHint("Obtained Marks");
            editText.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
            layout.addView(editText);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // Check if a valid item is selected
                    if (position >= 0) {
                        String creditHours = parent.getItemAtPosition(position).toString();
                        try {
                            double creditHoursValue = Double.parseDouble(creditHours);
                            creditHoursList.add(creditHoursValue);
                        } catch (NumberFormatException e) {
                            // Handle invalid number format
                            Toast.makeText(getApplicationContext(), "Invalid credit hours value", Toast.LENGTH_SHORT).show();
                        }
                    }
                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String obtainedMarksStr = s.toString();
                    if (!TextUtils.isEmpty(obtainedMarksStr)) {
                        try {
                            NumberFormat format = NumberFormat.getInstance(Locale.US);
                            double obtainedMarks = format.parse(obtainedMarksStr).doubleValue();
                            if (obtainedMarks > 0.0 && obtainedMarks <= 100) {
                                if (obtainedMarksList.size() < 3) {
                                    obtainedMarksList.add(obtainedMarks);
                                }
                            }else {
                                Toast.makeText(getApplicationContext(), "Obtained marks should be between 0.0 and 100.0", Toast.LENGTH_SHORT).show();
                            }
                        } catch (ParseException e) {
                            // Handle exception when the obtained marks cannot be parsed to a valid double
                            Toast.makeText(getApplicationContext(), "Invalid input for Obtained marks", Toast.LENGTH_SHORT).show();
                            e.printStackTrace(); // You can log the exception or take appropriate action as needed
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Enter Obtained marks", Toast.LENGTH_SHORT).show();
                    }
                    checkContinueButtonStatus();
                }



            });
        }

    }

                private void checkContinueButtonStatus() {
        isContinueButtonEnabled = true;
        for (Double obtainedMarks : obtainedMarksList) {
            if (obtainedMarks == 0.0) {
                isContinueButtonEnabled = false;
                break;
            }
        }
        btnContinue.setEnabled(isContinueButtonEnabled);
    }

    public double calculateQualityPoints(double obtainedMarks) {
        if (obtainedMarks >= 85 && obtainedMarks <= 100) {
            return 4.0;
        } else if (obtainedMarks >= 80 && obtainedMarks < 85) {
            return 3.7;
        } else if (obtainedMarks >= 75 && obtainedMarks < 80) {
            return 3.3;
        } else if (obtainedMarks >= 70 && obtainedMarks < 75) {
            return 3.0;
        } else if (obtainedMarks >= 65 && obtainedMarks < 70) {
            return 2.7;
        } else if (obtainedMarks >= 61 && obtainedMarks < 65) {
            return 2.3;
        } else if (obtainedMarks >= 58 && obtainedMarks < 61) {
            return 2.0;
        } else if (obtainedMarks >= 55 && obtainedMarks < 58) {
            return 1.7;
        } else if (obtainedMarks >= 50 && obtainedMarks < 55) {
            return 1.0;
        } else {
            return 0.0;
        }
    }
}
