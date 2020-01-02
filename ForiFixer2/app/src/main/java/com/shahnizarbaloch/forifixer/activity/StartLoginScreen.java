package com.shahnizarbaloch.forifixer.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.shahnizarbaloch.forifixer.R;
import java.util.Objects;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


@SuppressLint("Registered")
public class StartLoginScreen extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Exception : ";
    private EditText etEmailAddress,etPassword;
    private String Email;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    /**
     * This Method is Nothing but for simplifying the code
     */
    private void initialize(){

        mAuth = FirebaseAuth.getInstance();
        TextView tvForgotPassword = findViewById(R.id.tv_forgot_password);
        tvForgotPassword.setOnClickListener(this);
        Button tvSignUp = findViewById(R.id.tv_signup);
        tvSignUp.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);
        etEmailAddress=findViewById(R.id.et_email_address);
        etPassword=findViewById(R.id.et_password);
        Button btnSignin = findViewById(R.id.btn_signin);
        btnSignin.setOnClickListener(this);
    }



    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = mAuth.getCurrentUser();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user!=null) {
            Intent intent = new Intent(this,SplashScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_layout);
        initialize();
    }

    /**
     * This Method is for checking User Email is verified or not
     */
    private void checkIfEmailVerified()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        assert user != null;
        if (user.isEmailVerified())
        {
            // user is verified, so you can finish this activity or send user to activity which you want.
            Toast.makeText(StartLoginScreen.this, "Successfully logged in!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(StartLoginScreen.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        else
        {
            // email is not verified, so just prompt the message to the user and restart this activity.
            // NOTE: don't forget to log out the user.
            Toast.makeText(this, "First Verify Your Email Address!", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();

            //restart this activity

        }
    }

    /**
     *
     * This MEthod will login user in the firebase
     * @param emailAddress Email Address of the user
     * @param password Password of the user
     */

    private void login(String emailAddress, String password){
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(emailAddress, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            progressBar.setVisibility(View.GONE);
                            checkIfEmailVerified();

                        } else {
                            // If sign in fails, display a message to the user.
                            try {
                                throw Objects.requireNonNull(task.getException());
                            } catch (FirebaseAuthInvalidUserException e) {
                                Toast.makeText(StartLoginScreen.this, "Invalid Email id", Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                Log.d(TAG , "email :" + Email);
                                Toast.makeText(StartLoginScreen.this, "invalid Password", Toast.LENGTH_SHORT).show();
                            } catch (FirebaseNetworkException e) {
                                Toast.makeText(StartLoginScreen.this, "Netword Problem", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                //Log.e(TAG, e.getMessage());
                            }
                            progressBar.setVisibility(View.GONE);
                        }

                        // ...
                    }
                });
    }



    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btn_signin:
                Email= etEmailAddress.getText().toString();
                String password = etPassword.getText().toString();
                if(Email.isEmpty() || !(Email.contains("@"))|| Email.length()<8){
                    Toast.makeText(this, "Incorrect Email !", Toast.LENGTH_SHORT).show();
                    etEmailAddress.setBackgroundResource(R.drawable.error_text_field);
                    etPassword.setBackgroundResource(R.drawable.edit_text_background);
                    progressBar.setVisibility(View.GONE);
                }
                else if (password.length()<8){
                    Toast.makeText(this, "Incorrect Password!", Toast.LENGTH_SHORT).show();
                    etPassword.setBackgroundResource(R.drawable.error_text_field);
                    etEmailAddress.setBackgroundResource(R.drawable.edit_text_background);
                    progressBar.setVisibility(View.GONE);
                }
                else {
                    etEmailAddress.setBackgroundResource(R.drawable.edit_text_background);
                    etPassword.setBackgroundResource(R.drawable.edit_text_background);
                    login(Email,password);
                }

            break;
            case R.id.tv_forgot_password:
                intent = new Intent(this,ForgotPasswordActivity.class);
                startActivity(intent);
                break;

            case R.id.tv_signup:
                intent = new Intent(this,SignUpActivity.class);
                startActivity(intent);
                break;


        }
    }
}
