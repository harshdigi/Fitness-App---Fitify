/*
 * Creator: Harsh Ahuja on 18/06/21, 9:43 AM Last modified: 18/06/21, 3:55 AM Copyright: All rights reserved Ⓒ 2021 http://digitaldealsolution.in
 *
 */

package in.digitaldealsolution.fitify.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import in.digitaldealsolution.fitify.InternetConnection;
import in.digitaldealsolution.fitify.NoInternetAcitivity;
import in.digitaldealsolution.fitify.R;
import in.digitaldealsolution.fitify.helper.UserHelper;

public class SignupTabFragment extends Fragment {

    private EditText editTextFullname, editTextEmail, editTextPassword, editTextAge, editTextMobile, editTextCPassword, editTextWeight;
    private RadioGroup radioGroupGender;
    private Button signup_btn;
    private FirebaseDatabase rootNode;
    private DatabaseReference rootRefrence;
    private FirebaseAuth mAuth;
    private MaterialRadioButton radioButtonGender;
    TabLayout tabLayout;
    private static final String TAG = "MyActivity";
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextFullname = (EditText) getActivity().findViewById(R.id.full_name);
        editTextEmail = (EditText) getActivity().findViewById(R.id.email);
        editTextPassword = (EditText)  getActivity().findViewById(R.id.pass);
        editTextPassword.setRawInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editTextCPassword = (EditText)  getActivity().findViewById(R.id.confirm_pass);
        editTextCPassword.setRawInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editTextAge = (EditText)  getActivity().findViewById(R.id.age);
        editTextMobile = (EditText)  getActivity().findViewById(R.id.mobile);
        editTextWeight = (EditText) getActivity().findViewById(R.id.weight);
        signup_btn = (Button) getActivity().findViewById(R.id.sigup_btn);
        radioGroupGender = (RadioGroup) getActivity().findViewById(R.id.radioGender);
        tabLayout= getActivity().findViewById(R.id.login_tablayout);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                int selectedId = (int) radioGroupGender.getCheckedRadioButtonId();
                radioButtonGender= (MaterialRadioButton) radioGroupGender.findViewById(selectedId);
                if (InternetConnection.checkConnection(getActivity())) {

                    registerUser(v);;
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                                    startActivity(new Intent(getActivity(), NoInternetAcitivity.class));
                                    getActivity().finish();
                                }
                            }).show();
                }
            }
        });
    }

    private Boolean validateName() {
        String val = editTextFullname.getText().toString();
        if (val.isEmpty()) {
            editTextFullname.setError("Field cannot be empty");
            return false;
        } else {
            editTextFullname.setError(null);
            return true;
        }
    }
    private Boolean validateEmail(){
        String val = editTextEmail.getText().toString();
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        if(val.isEmpty()){
            editTextEmail.setError("Field cannot be empty");
            return false;
        }
        if(!pattern.matcher(val).matches()){
            editTextEmail.setError("Enter Valid Email");
            return false;
        }
            editTextEmail.setError(null);
            return true;

    }
    private Boolean validateMobile(){
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
    private Boolean validatePassword(){
        String val = editTextPassword.getText().toString();
        editTextCPassword = getActivity().findViewById(R.id.confirm_pass);
        String cpass = editTextCPassword.getText().toString();
        String passPatter = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        if(val.isEmpty()){
            editTextPassword.setError("Field cannot be empty");
            return false;
        }
        else if(!val.matches(passPatter)) {
            editTextPassword.setError("Password too weak, Password must be 4 characters.\nPassword must not contain whitespace.\nPassword must contain at least 1 special character. ");
            return false;
        }
        else if(!val.equals(cpass)){
            editTextPassword.setError("Password and Confirm Password must match");
            return false;
        }
        else{
            editTextPassword.setError(null);
            return true;
        }
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


    public void registerUser(View view){
        if(!validateName() | !validateEmail()  | !validateMobile() | !validateAge() | !validateWeight() | !validatePassword() | !validateGender()) {
            return;
        }
        else{
        rootNode = FirebaseDatabase.getInstance();
        rootRefrence = rootNode.getReference("Users");
        final String name = editTextFullname.getText().toString();
        final String email = editTextEmail.getText().toString();
        final String mobile = editTextMobile.getText().toString();
        String password = editTextPassword.getText().toString();
        final String age = editTextAge.getText().toString();
        final String weight = editTextWeight.getText().toString();
        final String gender = radioButtonGender.getText().toString();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                final FirebaseUser user = mAuth.getCurrentUser();
                                user.sendEmailVerification()
                                        .addOnCompleteListener(getActivity(), new OnCompleteListener() {
                                            @Override
                                            public void onComplete(@NonNull Task task) {
                                                // Re-enable button
                                                if (task.isSuccessful()) {
                                                    UserHelper userHelper = new UserHelper(name, email, mobile, weight, age, gender);
                                                    rootRefrence.child(user.getUid()).setValue(userHelper);
                                                    Toast.makeText(getActivity(),
                                                            "Verification email sent to " + user.getEmail(),
                                                            Toast.LENGTH_SHORT).show();

                                                    FirebaseAuth.getInstance().signOut();
                                                    tabLayout.getTabAt(0).select();
                                                } else {
                                                    Log.e(TAG, "sendEmailVerification", task.getException());
                                                    Toast.makeText(getActivity(),
                                                            "Failed to send verification email.",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(getActivity(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });
        }
    }
}
