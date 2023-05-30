package app.shop.auth;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Pattern;

import app.shop.MainActivity;
import app.shop.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText displayNameET;
    private EditText emailET;
    private EditText passwordET;
    private Button signupButtonB;
    private TextView loginTV;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        displayNameET = findViewById(R.id.editTextName);
        emailET = findViewById(R.id.editTextEmail);
        passwordET = findViewById(R.id.editTextPassword);
        signupButtonB = findViewById(R.id.signupButton);
        loginTV = findViewById(R.id.loginTV);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        loginTV.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        signupButtonB.setOnClickListener(v -> {
            if(validateInputs()) {
                executeSignUp();
            }
        });
    }

    boolean validateInputs() {
        final String displayName = displayNameET.getText().toString().trim();
        final String email = emailET.getText().toString().trim();
        final String password = passwordET.getText().toString().trim();
        boolean inputsValid = true;

        if(displayName.isEmpty()) {
            displayNameET.setError(getString(com.firebase.ui.auth.R.string.fui_required_field));
            inputsValid = false;
        }
        if(email.isEmpty()) {
            emailET.setError(getString(com.firebase.ui.auth.R.string.fui_required_field));
            inputsValid = false;
        }
        if(password.isEmpty()) {
            passwordET.setError(getString(com.firebase.ui.auth.R.string.fui_required_field));
            inputsValid = false;
        }

        return inputsValid;
    }

    void executeSignUp() {
        progressBar.setVisibility(View.VISIBLE);
        final String displayName = displayNameET.getText().toString().trim();
        final String email = emailET.getText().toString().trim();
        final String password = passwordET.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(RegisterActivity.this, "Authentication successful.",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }
}