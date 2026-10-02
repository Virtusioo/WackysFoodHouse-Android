package com.example.wackysfoodhouse.features.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.wackysfoodhouse.R;
import com.example.wackysfoodhouse.core.App;
import com.example.wackysfoodhouse.databinding.ActivitySignupBinding;
import com.example.wackysfoodhouse.features.navigation.NavigationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class SignupActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, SignupActivity.class);
        context.startActivity(intent);
    }

    public ActivitySignupBinding ui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        ui = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, 0);
            return insets;
        });

        ui.email.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ui.email.setError(null);
                ui.email.setErrorEnabled(false);
            }
        });

        ui.password.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ui.password.setError(null);
                ui.password.setErrorEnabled(false);
            }
        });

        ui.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ui.email.getEditText().getText().toString();
                String password = ui.password.getEditText().getText().toString();

                if (email.isBlank()) {
                    ui.email.setError("Email address is empty");
                    return;
                }

                if (password.isBlank()) {
                    ui.password.setError("Password is empty");
                    return;
                }

                AlertDialog loading = App.showLoadingDialog(v.getContext(), "Signing you up..");

                App.auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                loading.cancel();

                                if (task.isSuccessful()) {
                                    NavigationActivity.start(SignupActivity.this, ui.logo);
                                } else {
                                    App.showErrorDialog(
                                            v.getContext(),
                                            "Failed to sign up",
                                            task.getException().getLocalizedMessage()
                                    );
                                }
                            }
                        });
            }
        });

        ui.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.start(v.getContext());
            }
        });
    }
}