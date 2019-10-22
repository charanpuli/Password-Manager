package com.example.charanpuli.passwordmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity{
    EditText encryptText,decryptText;
    Button decryptBtn,enterBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        encryptText=(EditText)findViewById(R.id.encryptText);
//        decryptText=(EditText)findViewById(R.id.decryptText);

        decryptBtn=(Button)findViewById(R.id.decryptBtn);
        enterBtn=(Button)findViewById(R.id.enterBtn);

        enterBtn.setOnClickListener((v)->{
            Intent intent=new Intent(HomeActivity.this,SaveActivity.class);
            startActivity(intent);
        });


        decryptBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,PasswordActivity.class);
                startActivity(intent);
            }
        });
    }

}
