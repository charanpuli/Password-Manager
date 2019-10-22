package com.example.charanpuli.passwordmanager.ViewHolder;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.charanpuli.passwordmanager.Interface.ItemClickListener;
import com.example.charanpuli.passwordmanager.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class PasswordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{   public TextView appName,appPassword,changeImage;
    public ImageView appImage;
    public ItemClickListener listener;
    public Button copy;

    public PasswordViewHolder(@NonNull View itemView) {
        super(itemView);
        appName=(TextView) itemView.findViewById(R.id.elementTextView);
        appPassword=(TextView) itemView.findViewById(R.id.elementPass);
        appImage=(ImageView)itemView.findViewById(R.id.elementIcon);
        copy=(Button)itemView.findViewById(R.id.cpyBtn);


    }
    public void setItemClickListener(ItemClickListener listener)
    {
        this.listener=listener;
    }


    @Override
    public void onClick(View v) {
        listener.onClick(v,getAdapterPosition(),false);
    }
}
