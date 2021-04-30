package com.heshwa.githubuserssearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.heshwa.githubuserssearch.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserActivity extends AppCompatActivity {
    TextView txtUsername,txtbio,txtlocation,txtemail,txtfollower,txtfollowing,txtrepo;
    ImageView imgProfile;
    LinearLayout llocation,llemail;
    private String baseUrl="https://api.github.com/users/",username="";
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        txtUsername = findViewById(R.id.txtUsername);
        imgProfile = findViewById(R.id.imgProfile);
        txtbio = findViewById(R.id.bio);
        txtlocation = findViewById(R.id.location);
        txtemail = findViewById(R.id.email);
        txtfollower = findViewById(R.id.followers);
        txtfollowing = findViewById(R.id.following);
        llocation = findViewById(R.id.ll_location);
        llemail = findViewById(R.id.ll_email);
        txtrepo = findViewById(R.id.txtreponumbers);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                baseUrl+username, null,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {

                        JSONObject root = (JSONObject)response;
                        try {
                            txtUsername.setText(root.get("login").toString());
                            if(!root.get("bio").toString().equals("null"))
                            {
                                txtbio.setText(root.get("bio").toString());
                                txtbio.setVisibility(View.VISIBLE);
                            }
                            if(!root.get("location").toString().equals("null"))
                            {
                                txtlocation.setText(root.get("location").toString());
                                llocation.setVisibility(View.VISIBLE);
                            }
                            if(!root.get("email").toString().equals("null"))
                            {
                                txtemail.setText(root.get("email").toString());
                                llemail.setVisibility(View.VISIBLE);
                            }
                            txtfollower.setText(root.get("followers").toString()+" followers");
                            txtfollowing.setText(root.get("following").toString()+" following");
                            txtrepo.setText(root.get("public_repos").toString());
                            Glide.with(UserActivity.this).load(root.get("avatar_url").toString()).into(imgProfile);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        try {
//                            JSONArray items=root.getJSONArray("items");
//                            for(int i =0;i<items.length();i++)
//                            {
//                                JSONObject item = items.getJSONObject(i);
//                                User user = new User(item.get("login").toString(),item.get("avatar_url").toString());
//                                userLists.add(user);
//                            }
//                            userAdapter.notifyDataSetChanged();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("ResponseError",""+error);

                    }
                }){

            @Override
            public Map getHeaders() throws AuthFailureError
            {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/vnd.github.v3+json");
                return headers;
            }

        };
        requestQueue.add(jsonObjectRequest);
    }
}