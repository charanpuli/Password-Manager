package com.example.charanpuli.passwordmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.paperdb.Paper;

public class MasterSignup extends AppCompatActivity {
    private TextView already_user,signUp;
    private EditText pass,confirmpass;
    private CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_signup);
        already_user=(TextView)findViewById(R.id.already_user);
        signUp=(Button)findViewById(R.id.signUpBtn);
        pass=(EditText)findViewById(R.id.signuppassword);
        confirmpass=(EditText)findViewById(R.id.confirmPassword);
        checkBox=(CheckBox)findViewById(R.id.terms_conditions);
        Paper.init(this);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password=pass.getText().toString();
                String conpass=confirmpass.getText().toString();

                if(password.equals("") || conpass.equals("")){
                    Toast.makeText(MasterSignup.this, "EnterPass", Toast.LENGTH_SHORT).show();
                }
                else if(!password.equals(conpass)){
                    Toast.makeText(MasterSignup.this, "non matching passwords", Toast.LENGTH_SHORT).show();
                }
                else if(!checkBox.isChecked()){
                    Toast.makeText(MasterSignup.this, "Agree terms and conditions", Toast.LENGTH_SHORT).show();
                }
                else {
                    Paper.book().write("master",password);
                    Toast.makeText(MasterSignup.this, "Signed Up successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MasterSignup.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
        already_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MasterSignup.this,MasterActivity.class);
                startActivity(intent);
            }
        });
    }
}
