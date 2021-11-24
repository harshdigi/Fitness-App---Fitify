/*
 * Creator: Harsh Ahuja on 18/06/21, 9:43 AM Last modified: 18/06/21, 3:57 AM Copyright: All rights reserved Ⓒ 2021 http://digitaldealsolution.in
 *
 */

package in.digitaldealsolution.fitify;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import in.digitaldealsolution.fitify.helper.UserHelper;

public class DeatailsForm extends AppCompatActivity {
    private EditText editTextAge, editTextMobile, editTextWeight;
    private RadioGroup radioGroupGender;
    private Button submit_btn;
    private FirebaseDatabase rootNode;
    private DatabaseReference rootRefrence;
    private MaterialRadioButton radioButtonGender;
    private FirebaseAuth mAuth;
    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deatails_form);
        editTextAge = (EditText) findViewById(R.id.age_form);
        editTextMobile = (EditText) findViewById(R.id.mobile_form);
        editTextWeight = (EditText) findViewById(R.id.weight_form);
        submit_btn = (Button) findViewById(R.id.submit_form);
        radioGroupGender = (RadioGroup) findViewById(R.id.radioGender_form);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                int selectedId = (int) radioGroupGender.getCheckedRadioButtonId();
                radioButtonGender = (MaterialRadioButton) radioGroupGender.findViewById(selectedId);

                if (InternetConnection.checkConnection(getApplicationContext())) {
                    submitdetails(v);
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(DeatailsForm.this);
                    builder.setMessage("Please Connect to Internet to proceed further")
                            .setCancelable(false)
                            .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
                                }
                            })
                            .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(DeatailsForm.this, NoInternetAcitivity.class));
                                    finish();
                                }
                            }).show();
                }
            }
        });


    }


    private Boolean validateMobile() {
        String val = editTextMobile.getText().toString();
        if(val.isEmpty()){
            editTextMobile.setError("Field cannot be empty");
            return false;
        }
        if(val.length()!=10){
            editTextMobile.setError("Enter valid 10 digit Mobile Number");
            return false;
        }
        editTextMobile.setError(null);
        return true;

    }

    private Boolean validateAge() {
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

    private Boolean validateWeight() {
        String val = editTextWeight.getText().toString();
        if (val.isEmpty()) {
            editTextWeight.setError("Field cannot be empty");
            return false;
        }
        else if ( val.length()> 3){
            editTextWeight.setError("Weight cannot be greater than 999 kg");
            return false;
        }
        else {
            editTextWeight.setError(null);
            return true;
        }
    }

    private Boolean validateGender() {
        String val = radioButtonGender.getText().toString();
        if (val.isEmpty()) {
            radioButtonGender.setError("Gender must be selected");
            return false;
        } else {
            radioButtonGender.setError(null);
            return true;
        }
    }

    public void submitdetails(View view) {
        if (!validateMobile() | !validateAge() | !validateWeight() | !validateGender()) {
            return;
        } else {
            rootNode = FirebaseDatabase.getInstance();
            rootRefrence = rootNode.getReference("Users");
            FirebaseUser user = mAuth.getCurrentUser();
            String name = user.getDisplayName();
            String email = user.getEmail();
            String mobile = editTextMobile.getText().toString();
            String age = editTextAge.getText().toString();
            String weight = editTextWeight.getText().toString();
            String gender = radioButtonGender.getText().toString();
            UserHelper userHelper = new UserHelper(name, email, mobile, weight, age, gender);
            rootRefrence.child(FirebaseAuth.getInstance().getUid()).setValue(userHelper);
            Toast.makeText(this, "Successfully Subitted", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}