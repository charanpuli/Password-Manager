package com.example.charanpuli.passwordmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MyView extends AppCompatActivity {

    ListView simpleList;
    String List [] = { "facebook" , "instagram" , "linkedin" , "twitter" , "whatsapp" , "youtube" };
    int images [] = { R.drawable .facebook , R . drawable . instagram , R . drawable . linkedin ,
            R . drawable . twitter , R . drawable . whatsapp , R . drawable . youtube };
    String[] pass={"facebook" , "instagram" , "linkedin" , "twitter" , "whatsapp" , "youtube"};
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout .activity_list_view );
        simpleList=(ListView) findViewById(R.id.listViewer);
        CustomAdapter customAdapter = new CustomAdapter ( getApplicationContext (), List,images,pass);
        simpleList.setAdapter(customAdapter);
        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                Intent intent=new Intent(MyView.this,PasswordActivity.class);
                intent.putExtra("appName",List[position]);
                startActivity(intent);
            }
        });

        }

}

