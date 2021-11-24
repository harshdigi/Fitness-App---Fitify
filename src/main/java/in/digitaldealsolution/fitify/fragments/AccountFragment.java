/*
 * Creator: Harsh Ahuja on 18/06/21, 9:43 AM Last modified: 18/06/21, 9:33 AM Copyright: All rights reserved Ⓒ 2021 http://digitaldealsolution.in
 *
 */

package in.digitaldealsolution.fitify.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;
import in.digitaldealsolution.fitify.InternetConnection;
import in.digitaldealsolution.fitify.LoginActivity;
import in.digitaldealsolution.fitify.NoInternetAcitivity;
import in.digitaldealsolution.fitify.R;
import in.digitaldealsolution.fitify.helper.UserHelper;

public class AccountFragment extends Fragment {
    private String name;
    private String age;
    private String email;
    private String gender;
    private String phoneno;
    private String weight;
    private Uri image;
    private TextView username, usermobile, useremail;
    private EditText editTextFullName, editTextAge, editTextWeight;
    private RadioGroup radioGroupGender;
    private Button submit_btn;
    private FirebaseDatabase rootNode;
    private DatabaseReference rootRefrence;
    private GoogleSignInClient mGoogleSignInClient;
    private MaterialRadioButton radioButtonGender;
    private Button logout_btn;
    private CircleImageView userImage;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_account, container, false);
        editTextAge = (EditText) root.findViewById(R.id.age_account);
        editTextWeight = (EditText) root.findViewById(R.id.weight_account);
        editTextFullName = (EditText) root.findViewById(R.id.full_name_account);
        submit_btn = (Button) root.findViewById(R.id.submit_account);
        radioGroupGender = (RadioGroup) root.findViewById(R.id.radioGender_account);
        username = (TextView) root.findViewById(R.id.username_account);
        usermobile = (TextView) root.findViewById(R.id.usermobile_account);
        useremail = (TextView) root.findViewById(R.id.useremail_account);
        userImage = (CircleImageView) root.findViewById(R.id.user_image_account);
        logout_btn = (Button) root.findViewById(R.id.logout_btn);

        getData();
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                int selectedId = (int) radioGroupGender.getCheckedRadioButtonId();
                radioButtonGender = (MaterialRadioButton) radioGroupGender.findViewById(selectedId);
                if (InternetConnection.checkConnection(getActivity())) {
                    submitdetails(v);
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
        return root;
    }

    private void logout() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
        FirebaseAuth.getInstance().signOut();
        mGoogleSignInClient.signOut();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();

    }

    private void getData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String user = FirebaseAuth.getInstance().getUid();
                name = snapshot.child(user).child("name").getValue(String.class);
                phoneno = snapshot.child(user).child("phoneno").getValue(String.class);
                age = snapshot.child(user).child("age").getValue(String.class);
                email = snapshot.child(user).child("email").getValue(String.class);
                gender = snapshot.child(user).child("gender").getValue(String.class);
                weight = snapshot.child(user).child("weight").getValue(String.class);
                username.setText(name);
                usermobile.setText(phoneno);
                useremail.setText(email);
                editTextFullName.setText(name);
                editTextWeight.setText(weight);
                editTextAge.setText(age);
                image = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();
                Glide.with(getContext())
                        .load(image)
                        .into(userImage);

                if (gender.equals("Male")) {
                    radioButtonGender = (MaterialRadioButton) radioGroupGender.findViewById(R.id.radioButtonMale_account);
                    radioButtonGender.setChecked(true);
                } else {
                    radioButtonGender = (MaterialRadioButton) radioGroupGender.findViewById(R.id.radioButtonFemale_account);
                    radioButtonGender.setChecked(true);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private Boolean validateName() {
        String val = editTextFullName.getText().toString();
        if (val.isEmpty()) {
            editTextFullName.setError("Field cannot be empty");
            return false;
        } else {
            editTextFullName.setError(null);
            return true;
        }
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
        if (!validateName() | !validateAge() | !validateWeight() | !validateGender()) {
            return;
        } else {
            rootNode = FirebaseDatabase.getInstance();
            rootRefrence = rootNode.getReference("Users");
            String name = editTextFullName.getText().toString();
            String age = editTextAge.getText().toString();
            String weight = editTextWeight.getText().toString();
            String gender = radioButtonGender.getText().toString();

            UserHelper userHelper = new UserHelper(name, email, phoneno, weight, age, gender);
            rootRefrence.child(FirebaseAuth.getInstance().getUid()).setValue(userHelper);
            Toast.makeText(getActivity(),"Submitted Successfully",Toast.LENGTH_SHORT).show();
        }


    }
}