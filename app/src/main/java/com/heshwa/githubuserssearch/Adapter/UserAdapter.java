package com.heshwa.githubuserssearch.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.heshwa.githubuserssearch.Model.User;
import com.heshwa.githubuserssearch.R;
import com.heshwa.githubuserssearch.UserActivity;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<User> userlists;

    public UserAdapter() {
    }

    public UserAdapter(Context mContext, ArrayList<User> userlists) {
        this.mContext = mContext;
        this.userlists = userlists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = userlists.get(position);
        holder.txtUsername.setText(user.getUsername());
        Glide.with(mContext).load(user.getUrl()).into(holder.imgProfile);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UserActivity.class);
                intent.putExtra("username",user.getUsername());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userlists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtUsername;
        ImageView imgProfile;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            imgProfile = itemView.findViewById(R.id.imgProfile);

        }
    }
}
