package com.example.connected_plant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class login extends AppCompatActivity {

    public EditText usernamet,passwordt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernamet = (EditText)findViewById(R.id.username);
        passwordt = (EditText)findViewById(R.id.passwd);
    }


    public void OnLogin(View view)
    {
        String username = usernamet.getText().toString();
        String psswd = passwordt.getText().toString();
        String type = "login";
        BackgroundWorker bk = new BackgroundWorker(this);
        bk.execute(type,username,psswd);
    }


    public void gotoregisterpg(View view)
    {
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
    }

}
