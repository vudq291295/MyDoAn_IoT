package com.dqv.smarthome.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dqv.smarthome.Application.MyApplication;
import com.dqv.smarthome.ConnSqlite.SQLiteHandler;
import com.dqv.smarthome.Model.UrlModel;
import com.dqv.smarthome.Model.UserModel;
import com.dqv.smarthome.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    public String url = UrlModel.url_login;
    public String url_getCurrentUser = UrlModel.url_getCurrentUser;

    //    private String url_changeStatus = UrlModel.url_isonl;
    boolean result;
    EditText mUsername, mPassword;
    private String username,password;
    Button mSubmit;
    public static final String TAG = LoginActivity.class.getSimpleName();
    RequestQueue mRequestQueue;
    TextView mRegister, twQuenMK,twChangeLanguage;
    ImageView imgLogo;

    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUsername = (EditText) findViewById(R.id.usernameET);
        mPassword = (EditText) findViewById(R.id.password_login_ET);
        mSubmit = (Button) findViewById(R.id.Btn_login);

//        switchLogo = findViewById(R.id.switch1);
        imgLogo = (ImageView) findViewById(R.id.logo);
        mSubmit.setText(getString(R.string.login));
        mUsername.setHint(getString(R.string.username));
        mPassword.setHint(getString(R.string.password));

        if (MyApplication.getInstance().getPrefManager().getUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        db = new SQLiteHandler(getApplicationContext());

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsername.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                if (!username.isEmpty() && !password.isEmpty()) {
                    CheckLogin(username, password);
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.empty_username)
                            , Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    public void CheckLogin(final String username, final String pwd) {
        Log.d(TAG, "CheckLogin: "+username+" - "+pwd);
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage(getString(R.string.loading_data));
//        pDialog.show();
        pDialog.setCanceledOnTouchOutside(false);
        boolean result = false;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse() returned: " + response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    String accessToken = jObj.getString("access_token");
                    SetCurrentUser(accessToken);
                    pDialog.dismiss();
                }
                catch (Exception ex)
                {
                    Toast.makeText(getApplicationContext(),
                            ex+"", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onResponse() returned: " + ex);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error+"", Toast.LENGTH_LONG).show();
                pDialog.hide();
                Log.d(TAG, "onErrorResponse() returned: " + error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("grant_type", "password");
                params.put("client_id", "test");
                params.put("client_secret", "test");
                params.put("username",username);
                params.put("password", pwd);
                return params;
            }
            @Override
            protected Map<String, String> getPostParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("grant_type", "password");
                params.put("client_id", "test");
                params.put("client_secret", "test");
                params.put("username",username);
                params.put("password", pwd);
                return params;
            }

        };
        MyApplication.getInstance().addToRequestQueue(request,TAG);
//        return result;
    }

    public void SetCurrentUser(final String token){
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage(getString(R.string.loading_data));
//        pDialog.show();
        pDialog.setCanceledOnTouchOutside(false);
        boolean result = false;
        StringRequest request = new StringRequest(Request.Method.GET, url_getCurrentUser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse() returned: " + response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    String userName = jObj.getString("username");
                    String passWord = jObj.getString("password");

                    UserModel userTeamp = new UserModel("1",userName,passWord);
                    userTeamp.setToken(token);
                    db.addUser(userTeamp);
                    MyApplication.getInstance().getPrefManager().storeUser(userTeamp);
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    pDialog.dismiss();
                }
                catch (Exception ex)
                {
                    Toast.makeText(getApplicationContext(),
                            ex+"", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onResponse() returned: " + ex);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error+"", Toast.LENGTH_LONG).show();
                pDialog.hide();
                Log.d(TAG, "onErrorResponse() returned: " + error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                super.getHeaders();
                Map<String,String> params = new HashMap<String,String>();
                params.put("Authorization", "Bearer "+token);
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(request,TAG);
    }
}
