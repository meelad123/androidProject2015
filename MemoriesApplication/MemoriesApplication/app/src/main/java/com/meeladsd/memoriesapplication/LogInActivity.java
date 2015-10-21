package com.meeladsd.memoriesapplication;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogInActivity extends ActionBarActivity {

    private Button btnLogin;
    private EditText txtUsername, txtPassword;
    private String myURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        txtUsername = (EditText)findViewById(R.id.txtUsername);
        txtPassword = (EditText)findViewById(R.id.txtPass);

        btnLogin = (Button)findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtUsername.getText().toString() == "")
                    txtUsername.setError(getString(R.string.msg_empty));
                if (txtPassword.getText().toString() == "")
                    txtPassword.setError(getString(R.string.msg_empty));
                boolean res = Login();
                if(!res) {
                    Toast.makeText(getApplicationContext(), "Not logged in",
                            Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getApplicationContext(), "Logged in!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public Boolean Login()
    {
        User u = new User();
        myURL = getString(R.string.url_token);
        new login(txtUsername.getText().toString(), txtPassword.getText().toString(), myURL).execute();
        if(u.token != null)
            return true;
        return false;
    }
}
