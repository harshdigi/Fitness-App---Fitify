/*
 * Creator: Harsh Ahuja and Chinmaya Pandey on 18/06/21, 9:43 AM Last modified: 17/06/21, 11:07 PM Copyright: All rights reserved Ⓒ 2021 http://digitaldealsolution.in
 *
 */

package in.digitaldealsolution.fitify;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class BMRActivity extends AppCompatActivity {
    private RadioGroup radioGroupGender;
    private RadioButton radioButtonGender;
    private EditText editTextAge,editTextWeight, editTextHeight;
    private Button button;
    private TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmractivity);
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.toolbar_bmr);
        setSupportActionBar(myChildToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        //Hooks
        radioGroupGender = findViewById(R.id.gender_group);
        editTextAge = findViewById(R.id.age);
        editTextHeight= findViewById(R.id.height);
        editTextWeight= findViewById(R.id.weight);
        button = findViewById(R.id.button);
        result = findViewById(R.id.result);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = (int) radioGroupGender.getCheckedRadioButtonId();
                radioButtonGender= radioGroupGender.findViewById(selectedId);
                getResult();
            }
        });

    }
    private Boolean validateAge(){
        String val = editTextAge.getText().toString();
        if(val.isEmpty()){
            editTextAge.setError("Field cannot be empty");
            return false;
        }

        if(Integer.parseInt(val)<=13 || Integer.parseInt(val)>= 90){
            editTextAge.setError("Age must be between 13 to 90");
            return false;
        }

        editTextAge.setError(null);
        return true;

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
    private Boolean validateGender(){
        String val = radioButtonGender.getText().toString();
        if(val.isEmpty()){
            radioButtonGender.setError("Gender must be selected");
            return false;
        }
        else{
            radioButtonGender.setError(null);
            return true;
        }
    }
    private void getResult() {
        if(!validateAge() | !validateGender() |! validateWeight() |! validatHeight()){
            return;
        }
        else{
            double height = Double.parseDouble(editTextHeight.getText().toString());
            double weight = Double.parseDouble(editTextWeight.getText().toString());
            int age = Integer.parseInt(editTextWeight.getText().toString());
            String gender = radioButtonGender.getText().toString();
            double answer;
            if(gender.toLowerCase().equals("male")){

                    answer =  66.47 + (13.75 * weight) + (5.003 * height) - (6.755 * age);

            }
            else{

                    answer =  655.1 + (9.563 * weight) + (1.850 * height) - (4.676 * age);

            }
            result.setText(String.format("%.2f", answer));
        }

    }
}