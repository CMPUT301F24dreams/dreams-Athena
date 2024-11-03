package com.example.athena.EntrantAndOrganizerActivities;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.athena.GeneralActivities.signinScreenActivity;
import com.example.athena.R;

public class signUpActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registration);

        // Initialize EditTexts directly from the current view set by setContentView
        EditText editName = findViewById(R.id.editNameReg);
        EditText editEmail = findViewById(R.id.editEmailReg);
        EditText editNum = findViewById(R.id.editNumberReg);

        // Set the input type for the phone number field
        editNum.setInputType(InputType.TYPE_CLASS_PHONE);

        // Initialize Buttons
        Button submitButton = findViewById(R.id.submitButton);
        Button cancelButton = findViewById(R.id.cancelButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Retrieve values from EditText fields
                String name = editName.getText().toString().trim();
                String email = editEmail.getText().toString().trim();
                String number = editNum.getText().toString().trim();

                // Debugging Toasts to verify values
                Toast.makeText(signUpActivity.this, "Name: " + name, Toast.LENGTH_SHORT).show();
                Toast.makeText(signUpActivity.this, "Email: " + email, Toast.LENGTH_SHORT).show();
                Toast.makeText(signUpActivity.this, "Number: " + number, Toast.LENGTH_SHORT).show();

                // Check if fields are empty
                if (name.isEmpty()) {
                    editName.setError("Name cannot be empty");
                    editName.requestFocus();
                    return;
                }

                if (email.isEmpty()) {
                    editEmail.setError("Email cannot be empty");
                    editEmail.requestFocus();
                    return;
                }

                // If all fields are filled, proceed with action
                Toast.makeText(signUpActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(signUpActivity.this, signinScreenActivity.class); // Replace 'signinScreenActivity.class' with the actual home screen activity
                startActivity(intent);
                finish(); // Finish current activity
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signUpActivity.this, signinScreenActivity.class); // Replace 'signinScreenActivity.class' with the actual home screen activity
                startActivity(intent);
                finish(); // Finish current activity
            }
        });
    }
}