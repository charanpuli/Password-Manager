package com.example.charanpuli.passwordmanager;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class CustomAdapter extends BaseAdapter {
    Context context;
    String List [];
    int images [];
    String[] pass;
    private TextView text,password;
    private ImageView image;
    LayoutInflater inflter;
    Button copy;
    public CustomAdapter (Context applicationContext , String [] List , int [] images,String[] pass) {
        this . context = applicationContext;
        this . List = List;
        this . images = images;
        this.pass=pass;
        inflter = ( LayoutInflater. from ( applicationContext ));
    }
    @Override
    public int getCount () {
        return List . length;
    }
    @Override
    public Object getItem ( int i ) {
        return null;
    }
    @Override
    public long getItemId ( int i ) {
        return 0;

        }
    @SuppressLint("ViewHolder")
    @Override
    public View getView (int i , View view , ViewGroup viewGroup ) {
        view = inflter.inflate( R.layout .elementview , null );
        text = (TextView) view.findViewById(R.id.elementTextView);
        image=(ImageView) view.findViewById(R.id.elementIcon);
        password=(TextView)view.findViewById(R.id.elementPass);
        copy=(Button)view.findViewById(R.id.cpyBtn);
        text.setText(List[i]);
        image.setImageResource (images[i]);
        password.setText(pass[i]);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", pass[i]);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "copied...", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


}
