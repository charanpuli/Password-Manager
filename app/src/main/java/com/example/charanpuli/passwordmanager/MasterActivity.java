package com.example.charanpuli.passwordmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import io.paperdb.Paper;

public class MasterActivity extends AppCompatActivity {
    private TextView create;
    private Button loginBtn;
    private EditText loginPass;
    private CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Paper.init(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        create=(TextView) findViewById(R.id.createAccount);
        loginBtn=(Button)findViewById(R.id.loginBtn);
        loginPass=(EditText)findViewById(R.id.login_password) ;
        checkBox=(CheckBox)findViewById(R.id.show_hide_password);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    loginPass.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
                else{
                    loginPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MasterActivity.this,MasterSignup.class);
                startActivity(intent);
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password=Paper.book().read("master");
                if(!password.equals(loginPass.getText().toString())){
                    Toast.makeText(MasterActivity.this, "Password Incorrect", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(MasterActivity.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }
}
