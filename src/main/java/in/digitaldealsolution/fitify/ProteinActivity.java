/*
 * Creator: Harsh Ahuja and Chinmaya Padey on 18/06/21, 9:43 AM Last modified: 18/06/21, 1:16 AM Copyright: All rights reserved Ⓒ 2021 http://digitaldealsolution.in
 *
 */

package in.digitaldealsolution.fitify;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProteinActivity extends AppCompatActivity {

    private Button caluculate_btn;
    private EditText editTextWeight;
    private  double weight,answer;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protein);
        caluculate_btn= (Button)findViewById(R.id.button);
        editTextWeight =(EditText) findViewById(R.id.weight);
        result = (TextView) findViewById(R.id.result);
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.toolbar_protein);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        caluculate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProteinData();
            }

        });
        result.setText(String.valueOf(answer));

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
        return true;

    }

    private void getProteinData() {
        if(!validateWeight()){
            return;
        }
        else{
        weight = Double.parseDouble(editTextWeight.getText().toString());
        answer = weight * (1.2);
        result.setText(String.format("%.2f", answer));
        }
    }

}
