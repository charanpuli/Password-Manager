package com.example.charanpuli.passwordmanager;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.charanpuli.passwordmanager.ViewHolder.PasswordViewHolder;
import com.example.charanpuli.passwordmanager.model.Passwords;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;

public class PasswordActivity extends AppCompatActivity {

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_leader);
//    }
//private EditText inputText;
//    private Button searchBtn;
    private RecyclerView searchList;
    String decPass="";
    //    private String SearchInput="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

//        inputText=findViewById(R.id.search_product_name);
//        searchBtn=findViewById(R.id.search_btn);
        searchList=findViewById(R.id.search_list);
        searchList.addItemDecoration(new DividerItemDecoration(searchList.getContext(),DividerItemDecoration.VERTICAL));
        searchList.setLayoutManager(new LinearLayoutManager(PasswordActivity.this));
//        searchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SearchInput=inputText.getText().toString();
//                onStart();
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Passwords");

        FirebaseRecyclerOptions<Passwords> options=new FirebaseRecyclerOptions.Builder<Passwords>()
                .setQuery(reference,Passwords.class)
                .build();


        FirebaseRecyclerAdapter<Passwords,PasswordViewHolder> adapter=new FirebaseRecyclerAdapter<Passwords, PasswordViewHolder>(options) {
            @Override
            public Passwords getItem(int position) {
                return super.getItem(position);
            }

            @Override
            protected void onBindViewHolder(@NonNull PasswordViewHolder holder, int position, @NonNull final Passwords model)
            {
                holder.appName.setText(model.getPname());
                String temp=model.getPassword();
                AES aes=new AES();
                try {
                    decPass = aes.decrypt(Paper.book().read("master").toString(), Base64.decode(temp.getBytes("UTF-16LE"), Base64.DEFAULT));
                }
                catch (Exception e){
                    e.printStackTrace();
                }

                holder.appPassword.setText("From : "+decPass);

                holder.copy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClipboardManager clipboard = (ClipboardManager)(PasswordActivity.this).getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("label", decPass);
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(PasswordActivity.this, "copied...", Toast.LENGTH_SHORT).show();
                    }
                });
                Picasso.get().load(model.getImage()).into(holder.appImage);



            }

            @NonNull
            @Override
            public PasswordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.elementview,parent,false);
                PasswordViewHolder holder=new PasswordViewHolder(view);
                return holder;
            }
        };
        searchList.setAdapter(adapter);
        adapter.startListening();
    }
}