package com.shahnizarbaloch.forifixer.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shahnizarbaloch.forifixer.R;
import com.shahnizarbaloch.forifixer.model.User;

import java.util.Objects;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private EditText etName, etEmail, etNumber, etPassword;
    private ProgressBar progressBar;
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference databaseReference;
    FirebaseUser UserDetails;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);
        initialize();
    }

    private void initialize(){
        mAuth=FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance();
        databaseReference= mDatabase.getReference("Users");
        etName =findViewById(R.id.signup_et_name);
        etEmail =findViewById(R.id.signup_et_email);
        etNumber =findViewById(R.id.signup_et_number);
        etPassword =findViewById(R.id.signup_et_password);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        Button btnCreateAccount = findViewById(R.id.btn_create_account);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFields();
            }
        });
    }


    private String Name,Email,Number,Password;
    /**
     * This Method is for checking input of users
     * if User input is correct then it will create account in the firebase
     */
    private void checkFields(){
        Name = etName.getText().toString();
        Email = etEmail.getText().toString();
        Number = etNumber.getText().toString();
        Password = etPassword.getText().toString();

        if(Name.length()<4){
            etName.setBackgroundResource(R.drawable.error_text_field);
            etEmail.setBackgroundResource(R.drawable.edit_text_background);
            etPassword.setBackgroundResource(R.drawable.edit_text_background);
            etNumber.setBackgroundResource(R.drawable.edit_text_background);
        }
        else if (Email.length()<8 || !(Email.contains("@"))){
            etEmail.setBackgroundResource(R.drawable.error_text_field);
            etName.setBackgroundResource(R.drawable.edit_text_background);
            etPassword.setBackgroundResource(R.drawable.edit_text_background);
            etNumber.setBackgroundResource(R.drawable.edit_text_background);
        }
        else if(Password.length()<8){
            Toast.makeText(this, "Password Length Should Be Greater Than 8 Characters!", Toast.LENGTH_LONG).show();
            etPassword.setBackgroundResource(R.drawable.error_text_field);
            etEmail.setBackgroundResource(R.drawable.edit_text_background);
            etName.setBackgroundResource(R.drawable.edit_text_background);
            etNumber.setBackgroundResource(R.drawable.edit_text_background);
        }
        else if(Number.length()<8){
            etNumber.setBackgroundResource(R.drawable.error_text_field);
            etEmail.setBackgroundResource(R.drawable.edit_text_background);
            etName.setBackgroundResource(R.drawable.edit_text_background);
            etPassword.setBackgroundResource(R.drawable.edit_text_background);
        }
        else{
            etName.setBackgroundResource(R.drawable.edit_text_background);
            etEmail.setBackgroundResource(R.drawable.edit_text_background);
            etPassword.setBackgroundResource(R.drawable.edit_text_background);
            etNumber.setBackgroundResource(R.drawable.edit_text_background);
            progressBar.setVisibility(View.VISIBLE);
            createAccount();

        }

    }

    /**
     * This method will create account in firebase,
     * if it will be success then it will go to Login page of the App
     */
    private void createAccount() {
    mAuth.createUserWithEmailAndPassword(Email,Password)
            .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()){
                Objects.requireNonNull(mAuth.getCurrentUser()).sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(SignUpActivity.this, "Please Verify Your Email Address for Completing Registration!", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                        updateProfile(Name,Number);
                        //After Sending confirmation email, here siging out the user for going to login screen otherwise it would go direct main screen.
                        mAuth.signOut();
                        Intent intent = new Intent(SignUpActivity.this,StartLoginScreen.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);
                        showMessage("Verification Failed!");
                    }
                });

            }
            else{
                progressBar.setVisibility(View.GONE);
                showMessage(""+task.getException());
            }
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            progressBar.setVisibility(View.GONE);
        }
    });
    }

    /**
     * This Method will add Name of the User in The Firebase
     */
    private void updateProfile(String Name,String Number) {
        UserDetails=FirebaseAuth.getInstance().getCurrentUser();
        assert UserDetails != null;
        String userid=UserDetails.getUid();
        User user = new User(Name,Number);
        databaseReference.child(userid).setValue(user);


    }



    /**
     * This is Simple Method to Show Toast
     * @param Message Whatever Toast Message Can be!
     * Just Call This MEthod and Pass the Toast Message in String
     */
    private void showMessage(String Message){
        Toast.makeText(this, "Message : "+Message, Toast.LENGTH_SHORT).show();
    }

}
