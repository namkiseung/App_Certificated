package com.example.protected_root;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class LoginActivity extends AppCompatActivity {
    //Declare an instance of FirebaseAuth
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    ProgressBar pbLogin;
    Button btnLogin;
    Button btnRegister;
    EditText etEmail;
    EditText etPasswd;
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pbLogin = (ProgressBar)findViewById(R.id.pbLogin);
        //Initialize the EditText Obj
        etEmail = (EditText) findViewById(R.id.etLogin);
        etPasswd = (EditText) findViewById(R.id.etPassword);
        //Initialize the FirebaseAuth instance.
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d("NamkiLog", "onAuthStateChanged sign in");
                } else {
                    Log.d("NamkiLog", "onAuthStateChanged sign out");
                }
            }
        };
        //goChat
        title = (TextView) findViewById(R.id.title);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etEmail.setText("admin@naver.com");
                Log.d("NamkiLog", "e: admin / p: admin / a: MainActivity");
            }
        });

        //Set btnSignup Event
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stEmali = etEmail.getText().toString();
                String stPasswd = etPasswd.getText().toString();
                registerUser(stEmali, stPasswd);
//                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
//                startActivity(intent);
            }
        });


        //Set btnLogin Event
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stEmali = etEmail.getText().toString();
                String stPasswd = etPasswd.getText().toString();
                try {
                    if (stEmali.equals("admin") && stPasswd.equals("admin")) {
                        Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
                     startActivity(intentMain);
                    } else if (stEmali != "" || stPasswd != "") {
                        Toast.makeText(LoginActivity.this, "Login",
                                Toast.LENGTH_SHORT).show();
                        userLogin(stEmali, stPasswd);
                    }
                    Log.d("NamkiLog", "Check:" + stEmali + "--" + stPasswd);
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, "error",
                            Toast.LENGTH_SHORT).show();
                    Log.d("NamkiLog", "Check:" + stEmali + "--" + stPasswd);
                }
            }
        });
    }
    public void registerUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("NamkiLog","createUserWithEmail:onComplete:"+task.isSuccessful());
                        Toast.makeText(LoginActivity.this, "Authentication success",
                                Toast.LENGTH_SHORT).show();
                        if(!task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Authentication failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void userLogin(String email, String password){
        pbLogin.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("NamkiLog","createUserWithEmail:onComplete:"+task.isSuccessful());
                        Toast.makeText(LoginActivity.this, "Hello "+etEmail,
                                Toast.LENGTH_SHORT).show();
                        pbLogin.setVisibility(View.GONE);
                        if(!task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Authentication failed",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LoginActivity.this, "Authentication success",
                                    Toast.LENGTH_SHORT).show();
                        }
                        Intent intentChat = new Intent(LoginActivity.this, ChatActivity.class);
                        startActivity(intentChat);
                    }
                });
    }
    //hint
    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
