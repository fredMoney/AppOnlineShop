package app.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        UserButtonListener();
    }

    public void UserButtonListener() {
        ImageButton userProfileButton = findViewById(R.id.userProfileButton);
        userProfileButton.setOnClickListener(
                v -> {
                    Intent intent = new Intent("android.intent.action.LoginActivity");
                    startActivity(intent);
                }
        );
    }
}