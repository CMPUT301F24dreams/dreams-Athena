package com.example.athena.Activities;

import static android.widget.Toast.makeText;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.athena.R;
import androidx.appcompat.app.AppCompatActivity;

/**
 * This class represent the class that the user will user to register their details or sign into the application
 */
public class signinScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_screen);

        // Get Device ID
        //Line derived from copilot: https://copilot.microsoft.com/?msockid=1ce35a3ee9c767ff0e974f22e87366e5, 2024-10-25
        String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        //This is just a place holder for now represent a sign in since the db isn't working
        // Put Device ID in Bundle
        Bundle bundle = new Bundle();
        bundle.putString("device_id", deviceId);



        ImageButton signupbutton = findViewById(R.id.register_button);
        
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToCorrespondingActivity(bundle, LoadingScreenActivity.class);
            }
        });



    }

    public void switchToCorrespondingActivity(Bundle deviceID, Class<?> Activity) {

        boolean is_admin = false;
        //Place holder until the database is working, to represent the idea that having admin privileges will allow you to access the correct screen
        if (is_admin) {
            Toast admin = Toast.makeText(this, "This user is an Admin, he will get special privileges", Toast.LENGTH_SHORT);
            admin.show();
        }

        Intent entrantsAndOrganizersHome = new Intent(this, Activity);
        entrantsAndOrganizersHome.putExtras(deviceID);
        startActivity(entrantsAndOrganizersHome);
    }


}


