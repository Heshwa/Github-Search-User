package com.heshwa.githubuserssearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.heshwa.githubuserssearch.Adapter.UserAdapter;
import com.heshwa.githubuserssearch.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText edtSearch;
    private Button btnSearch;
    private RecyclerView recyclerView;
    private ArrayList<User> userLists;
    private UserAdapter userAdapter;
    private String baseUrl="https://api.github.com/search/users?q=";
    private RequestQueue requestQueue;
    private CheckBox cbSort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtSearch = findViewById(R.id.edtSearch);
        btnSearch = findViewById(R.id.btnsearch);
        recyclerView = findViewById(R.id.recyclerView);
        userLists = new ArrayList<User>();
        userAdapter = new UserAdapter(getApplicationContext(),userLists);
        cbSort = findViewById(R.id.sort);
        recyclerView.setAdapter(userAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchUsers(edtSearch.getText().toString());
            }
        });
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                searchUsers(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, baseUrl+"language:java", null,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {

                        JSONObject root = (JSONObject)response;
                        try {
                            JSONArray items=root.getJSONArray("items");
                            for(int i =0;i<items.length();i++)
                            {
                                JSONObject item = items.getJSONObject(i);
                                User user = new User(item.get("login").toString(),item.get("avatar_url").toString());
                                userLists.add(user);
                            }
                            userAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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
    public void searchUsers(String s)
    {
        userLists.clear();
        userAdapter.notifyDataSetChanged();
        if(cbSort.isChecked())
        {
            s = s+"&sort=repositories&order=asc";
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, baseUrl+s, null,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {


                        JSONObject root = (JSONObject)response;
                        try {
                            JSONArray items=root.getJSONArray("items");
                            for(int i =0;i<items.length();i++)
                            {
                                JSONObject item = items.getJSONObject(i);
                                User user = new User(item.get("login").toString(),item.get("avatar_url").toString());
                                userLists.add(user);
                                Log.i("userObj",""+user);
                            }
                            userAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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