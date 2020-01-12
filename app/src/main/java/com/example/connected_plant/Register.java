package com.example.connected_plant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class Register extends AppCompatActivity {


     EditText Username,Password;
    String name,username,password;
Button btrgst;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Username = (EditText) findViewById(R.id.usernm);
        Password = (EditText) findViewById(R.id.pswrd);
        btrgst = (Button)findViewById(R.id.btn_rgstr);
        btrgst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void registerUser() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://192.168.43.77/html/employee/register.php",

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),login.class);
                        startActivity(intent);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();

                     }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", Username.getText().toString());
                params.put("password", Password.getText().toString());
                return params;
            }
        };
Handler.getInstance(getApplicationContext()).addToRequestQue(stringRequest);

    }

}
