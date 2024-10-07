package com.example.lab4profilemanager;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.net.Uri;
import android.widget.EditText;
import android.view.View;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import android.app.Activity;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> avatarLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ImageView logoImageView = findViewById(R.id.logoImage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        avatarLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            int avatarId = data.getIntExtra("avatarId", 0);
                            if (avatarId != 0) {
                                logoImageView.setImageResource(avatarId);
                            }
                        }
                    }
                });
    }

    public void OnOpenInGoogleMaps (View view) {
        EditText teamAddres = (EditText) findViewById(R.id.teamAddressField);
        // Create a Uri from an intent string. Use the result to create an Intent.
        Uri gmmIntentUri = Uri.parse("http://maps.google.co.in/maps?q="+teamAddres.getText());
        // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        // Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");
        // Attempt to start an activity that can handle the Intent
        startActivity(mapIntent);
    }

    public void onSetAvatarButton(View view) {
        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
        avatarLauncher.launch(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ImageView avatarImage = findViewById(R.id.logoImage);

        if (data != null) {
            int selectedImageId = data.getIntExtra("selectedImageId", R.id.logoImage00); // Get selected image ID


            String drawableName = "avatar1"; // Default image name
            if (selectedImageId == R.id.logoImage00) {
                drawableName = "ic_logo_00";
            } else if (selectedImageId == R.id.logoImage01) {
                drawableName = "ic_logo_01";
            } else if (selectedImageId == R.id.logoImage02) {
                drawableName = "ic_logo_02";
            } else if (selectedImageId == R.id.logoImage03) {
                drawableName = "ic_logo_03";
            } else if (selectedImageId == R.id.logoImage04) {
                drawableName = "ic_logo_04";
            } else if (selectedImageId == R.id.logoImage05) {
                drawableName = "ic_logo_05";
            } else {
                drawableName = "ic_logo_00";
            }

            int resID = getResources().getIdentifier(drawableName, "drawable", getPackageName());
            avatarImage.setImageResource(resID);
        }
    }
}