package com.example.currencyconverter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SignUpActivity extends AppCompatActivity {


    Uri imageUri;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        TextView signin = findViewById(R.id.signIn);
        EditText reg_name = findViewById(R.id.name_signup);
        EditText reg_email = findViewById(R.id.email_signup);
        EditText reg_password = findViewById(R.id.password_signup);
        EditText reg_conf_password = findViewById(R.id.confirmpassword_signup);

        Button signup_button = findViewById(R.id.signUpButton);

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = reg_name.getText().toString();
                String email = reg_email.getText().toString();
                String password = reg_password.getText().toString();
                String cpassword = reg_conf_password.getText().toString();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email)
                        || TextUtils.isEmpty(password) || TextUtils.isEmpty(cpassword)) {
                    Toast.makeText(SignUpActivity.this, "Enter valid data", Toast.LENGTH_SHORT).show();
                } else if (!email.matches(emailPattern)) {
                    reg_email.setError("Invalid email");
                    Toast.makeText(SignUpActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    reg_password.setError("Invalid password");
                    Toast.makeText(SignUpActivity.this, "Password must be atleast of 6 characters", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(cpassword)) {
                    reg_conf_password.setError("Password doesn't match");
                    Toast.makeText(SignUpActivity.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                } else {

                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                DatabaseReference database_ref = database.getReference().child("user").child(auth.getUid());
                                StorageReference storage_ref = storage.getReference().child("upload").child(auth.getUid());


                                Users users = new Users(auth.getUid(), name, email);
                                database_ref.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                                        } else {
                                            Toast.makeText(SignUpActivity.this, "Error in creating user", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                            } else {

                                Toast.makeText(SignUpActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });

    }

}