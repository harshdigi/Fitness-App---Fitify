/*
 * Creator: Harsh Ahuja and Chinmaya Padey on 18/06/21, 9:43 AM Last modified: 17/06/21, 10:47 PM Copyright: All rights reserved Ⓒ 2021 http://digitaldealsolution.in
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

import com.google.android.material.radiobutton.MaterialRadioButton;

public class CaloriesActivity extends AppCompatActivity {
    private RadioGroup radioGroupGender;
    private RadioButton radioButtonGender;
    private EditText  editTextAge,editTextWeight, editTextHeight;
    private Spinner spinner;
    private Button button;
    private TextView result;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories);
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.toolbar_calories);
        setSupportActionBar(myChildToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        //Hooks
         radioGroupGender = findViewById(R.id.gender_group);
         editTextAge = findViewById(R.id.age);
         editTextHeight= findViewById(R.id.height);
         editTextWeight= findViewById(R.id.weight);
         spinner = findViewById(R.id.spinner);
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
               int position= spinner.getSelectedItemPosition();
               double answer;
               if(gender.toLowerCase().equals("male")){
                    if(position ==0){
                        answer =  66.47 + (13.75 * weight) + (5.003 * height) - (6.755 * age)* 1.375;
                    }
                    else if(position==1){
                        answer = 66.47 + (13.75 * weight) + (5.003 * height) - (6.755 * age)* 1.55;
                    }
                    else {
                        answer = 66.47 + (13.75 * weight) + (5.003 * height) - (6.755 * age)* 1.9;
                    }
                    }
               else{
                   if(position ==0){
                       answer =  655.1 + (9.563 * weight) + (1.850 * height) - (4.676 * age)* 1.375;
                   }
                   else if(position==1){
                       answer = 655.1 + (9.563 * weight) + (1.850 * height) - (4.676 * age)* 1.55;
                   }
                   else {
                       answer= 655.1 + (9.563 * weight) + (1.850 * height) - (4.676 * age)* 1.9;
                   }
               }
                result.setText(String.format("%.2f", answer));
         }

         }
}