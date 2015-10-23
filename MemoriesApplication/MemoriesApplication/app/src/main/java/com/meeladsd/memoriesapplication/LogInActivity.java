package com.meeladsd.memoriesapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LogInActivity extends ActionBarActivity {

    private Button btnLogin;
    private EditText txtUsername, txtPassword;
    private String myURL;
    private TextView txtRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        SharedPreferences s = this.getSharedPreferences(getString(R.string.str_token), MODE_PRIVATE);

        String t = s.getString(getString(R.string.str_access_token), "");
        if(t.length() == 0)
        {
            txtUsername = (EditText)findViewById(R.id.txtUsername);
            txtPassword = (EditText)findViewById(R.id.txtPass);

            btnLogin = (Button)findViewById(R.id.btnLogin);

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Login(v);
                }
            });

            txtRegister = (TextView)findViewById(R.id.txtRegister);

            txtRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewRegisterActivity(v);
                }
            });
        }
        else
        {
            Intent main = new Intent(this, MainActivity.class);

            startActivity(main);
        }
    }

    private void viewRegisterActivity(View v) {
        Intent reg = new Intent(v.getContext(), RegisterActivity.class);

        startActivity(reg);
    }

    public void Login(View view) {
        if (txtUsername.getText().toString() == "")
            txtUsername.setError(getString(R.string.msg_empty));
        else if (txtPassword.getText().toString() == "")
                txtPassword.setError(getString(R.string.msg_empty));
            else {
                User u = new User();
                myURL = getString(R.string.url_token);
                new login(txtUsername.getText().toString(), txtPassword.getText().toString(), myURL, getApplicationContext()).execute();
            }

    }
}
