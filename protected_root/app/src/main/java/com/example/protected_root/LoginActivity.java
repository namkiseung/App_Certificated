package com.example.protected_root;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import static java.lang.System.getProperties;

public class LoginActivity extends AppCompatActivity {
    Boolean result;
    //public static String secretKey = "a";
    AES256Chiper AES256Chiper_obj = new AES256Chiper();
    Handler h_obj = new Handler();
    RootCheck a = new RootCheck();
    //Declare an instance of FirebaseAuth
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    CheckBox save_Data;
    ProgressBar pbLogin;
    Button btnLogin;
    Button btnRegister;
    EditText etEmail;
    EditText etPasswd;
    TextView title;
    Boolean chk1;
    String stEmali;
    String stPasswd;
    @Override
    public void onResume(){
        super.onResume();
        //Log.d("System props info : ",""+getProperties());
        Log.d("Protected [Login]",""+a.checkSuperUser());
        if(a.checkSuperUser()) {
            Toast.makeText(LoginActivity.this, "루팅 및 무결성 탐지로 인해 앱을 종료합니다.", Toast.LENGTH_LONG).show();
            h_obj.postDelayed(new Runnable() {
                @Override
                public void run() {
                   Log.d("namkiLog","This Device is Rooting");
                   //System.exit(0);
                }
            }, 2000);
        }else{
            Log.d("namkiLog","This Device is Not Rooting");
            Toast.makeText(LoginActivity.this,"Not Rooted",Toast.LENGTH_SHORT).show();
        }
    }
    //so파일 로드
    static {
        //System.loadLibrary();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pbLogin = (ProgressBar)findViewById(R.id.pbLogin);
        //Initialize the EditText Obj
        etEmail = (EditText) findViewById(R.id.etLogin);
        etPasswd = (EditText) findViewById(R.id.etPassword);
        save_Data = (CheckBox)findViewById(R.id.chk_usrData);
        //checkBox 이벤트 처리
        SharedPreferences pref=getSharedPreferences("pref", Activity.MODE_PRIVATE);
        String id=pref.getString("id_save", "");
        String pwd=pref.getString("pwd_save", "");
        chk1=pref.getBoolean("chk1", false);

        if(chk1==true){
            String decryp_id ="";
            String decryp_pwd ="";
            try {
                decryp_id = AES256Chiper_obj.AES_Decode(id);
                decryp_pwd = AES256Chiper_obj.AES_Decode(pwd);
            } catch (Exception e) {
                e.printStackTrace();
            }
            etEmail.setText(decryp_id);
            etPasswd.setText(decryp_pwd);
            save_Data.setChecked(chk1);
        }

        //Initialize the FirebaseAuth instance.
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d("NamkiLog", "onAuthStateChanged sign in"+user.getUid());
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
                stEmali = etEmail.getText().toString();
                stPasswd = etPasswd.getText().toString();
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
                pbLogin.setVisibility(View.VISIBLE);
                stEmali = etEmail.getText().toString();
                stPasswd = etPasswd.getText().toString();
                try {
                    if (!stEmali.equals("")) {

                    } else if (stEmali.equals("admin") && stPasswd.equals("admin")) {
                        Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intentMain);
                     //   Toast.makeText(LoginActivity.this, "Null Value",Toast.LENGTH_SHORT).show();
                    }
                    Log.d("NamkiLog", "Check:" + stEmali + "--" + stPasswd);
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, "error",
                            Toast.LENGTH_SHORT).show();
                    Log.d("NamkiLog", "Check:" + stEmali + "--" + stPasswd);
                }
                userLogin(stEmali, stPasswd);
            }
        });
    }
    public void registerUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("NamkiLog","createUserWithEmail:onComplete:"+task.isSuccessful());

                        if(!task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Authentication failed",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LoginActivity.this, "Authentication success",
                                    Toast.LENGTH_SHORT).show();
                            Intent intentChat = new Intent(LoginActivity.this, ChatActivity.class);
                            startActivity(intentChat);
                        }
                        pbLogin.setVisibility(View.GONE);
                    }
                });
    }
    private void userLogin(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        result = task.isSuccessful();
                        Log.d("NamkiLog","CheckLoginUser:onComplete:"+result);
                        if(result) {
                            Toast.makeText(LoginActivity.this, "Authentication success",
                                    Toast.LENGTH_SHORT).show();

                            Intent intentChat = new Intent(LoginActivity.this, ChatActivity.class);
                            startActivity(intentChat);
                        }else{
                            Toast.makeText(LoginActivity.this, "Authentication failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                        pbLogin.setVisibility(View.GONE);

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
        if(mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        EditText etId=(EditText)findViewById(R.id.etLogin);
        EditText etPwd=(EditText)findViewById(R.id.etPassword);
        CheckBox etIdSave=(CheckBox)findViewById(R.id.chk_usrData);
        //SharedPreferences에 각 아이디를 지정하고 EditText 내용을 저장한다.
        String encryp_id="";
        String encryp_pwd="";
        try {
            encryp_id = AES256Chiper_obj.AES_Encode(etId.getText().toString());
            encryp_pwd = AES256Chiper_obj.AES_Encode(etPwd.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        editor.putString("id_save", encryp_id);
        editor.putString("pwd_save", encryp_pwd);
        editor.putBoolean("chk1", etIdSave.isChecked());
        editor.commit();
    }
}
/*
   @Override
    public void onStop(){
        super.onStop();
		//파이어베이스 인증리스너 구현
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
		//SP 객체 초기화
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        EditText etId=(EditText)findViewById(R.id.etLogin);
        EditText etPwd=(EditText)findViewById(R.id.etPassword);
        CheckBox etIdSave=(CheckBox)findViewById(R.id.chk_usrData);
        //SP에 각 아이디를 지정하고 EditText 내용을 저장한다.
        editor.putString("id_save", etId.getText().toString());
        editor.putString("pwd_save", etPwd.getText().toString());
        editor.putBoolean("chk1", etIdSave.isChecked());
        editor.commit();
    }
}
    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        EditText etId=(EditText)findViewById(R.id.etLogin);
        EditText etPwd=(EditText)findViewById(R.id.etPassword);
        CheckBox etIdSave=(CheckBox)findViewById(R.id.chk_usrData);
        if(chk1!=true){
            editor.remove("id_save");
            editor.remove("pwd_save");
            editor.commit();
        }
        //SharedPreferences에 각 아이디를 지정하고 EditText 내용을 저장한다.
        editor.putString("id_save", etId.getText().toString());
        editor.putString("pwd_save", etPwd.getText().toString());
        editor.putBoolean("chk1", etIdSave.isChecked());
        editor.commit();
    }
}
 */
