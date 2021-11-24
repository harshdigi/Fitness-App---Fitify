/*
 * Creator: Chinmaya Pandey and Harsh Ahuja on 18/06/21, 9:43 AM Last modified: 18/06/21, 1:16 AM Copyright: All rights reserved Ⓒ 2021 http://digitaldealsolution.in
 *
 */

package in.digitaldealsolution.fitify;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import io.armcha.elasticview.ElasticView;

public class BMICalculator extends AppCompatActivity {
    private Button caluculate_btn;
    private EditText editTextHeight,editTextWeight;
    private double height, weight, answer;
    private TextView result, grade;
    private ElasticView gradeCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmicalculator);

        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.toolbar_bmi);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        caluculate_btn= (Button)findViewById(R.id.button);
        editTextHeight =(EditText) findViewById(R.id.height);
        editTextWeight =(EditText) findViewById(R.id.weight);
        result = (TextView) findViewById(R.id.result);
        grade = (TextView) findViewById(R.id.grade);
        gradeCard = findViewById(R.id.grade_card);
        caluculate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBMIdata();
            }
        });

}

    private Boolean validateWeight(){
        String val = editTextWeight.getText().toString();
        if(val.isEmpty()){
            editTextWeight.setError("Field cannot be empty");
            return false;
        }
        if ( val.length()> 3){
            editTextWeight.setError("Weight cannot be greater than 999 kg");
            return false;
        }

        editTextWeight.setError(null);
        return true;

    }
    private Boolean validatHeight(){
        String val = editTextHeight.getText().toString();
        if(val.isEmpty()){
            editTextHeight.setError("Field cannot be empty");
            return false;
        }
        if ( val.length()> 3){
            editTextHeight.setError("Height cannot be greater than 999 cm");
            return false;
        }

        editTextHeight.setError(null);
        return true;

    }

    private void getBMIdata() {
        if (!validateWeight() | !validatHeight()) {
            return;
        } else {
            weight = Double.parseDouble(editTextWeight.getText().toString());
            height = Double.parseDouble(editTextHeight.getText().toString()) / 100;
            answer = weight / (height * height);

            if (answer <= 18.5) {
                gradeCard.setBackgroundColor(Color.YELLOW);
                grade.setText("You are Under weight");
            } else if (answer <= 24.9 && answer >= 18.5) {
                gradeCard.setBackgroundColor(Color.GREEN);
                grade.setText("You weight is normal");
            } else if (answer <= 29.9 && answer >= 25) {
                gradeCard.setBackgroundColor(Color.YELLOW);
                grade.setText("You are Over weight");
            } else if (answer >= 30) {
                gradeCard.setBackgroundColor(Color.RED);
                grade.setText("You are obese");
            }
            result.setText(String.format("%.2f", answer));
        }
    }

}
