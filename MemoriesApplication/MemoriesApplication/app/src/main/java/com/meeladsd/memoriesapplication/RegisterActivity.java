package com.meeladsd.memoriesapplication;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends ActionBarActivity {

    private String regM = "\"^(([\\\\w-]+\\\\.)+[\\\\w-]+|([a-zA-Z]{1}|[\\\\w-]{2,}))@\"\n" +
            "+\"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\\\.([0-1]?\"\n" +
            " +\"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\\\.\"\n" +
            " +\"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\\\.([0-1]?\"\n" +
            "+\"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|\"\n" +
            "+\"([a-zA-Z]+[\\\\w-]+\\\\.)+[a-zA-Z]{2,4})$\"";

    private Button btnReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        btnReg = (Button)findViewById(R.id.btn_register);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerFun(v);

            }
        });
    }

    private void registerFun(View v) {
        EditText user_name = (EditText) findViewById(R.id.editText2);
        EditText password = (EditText) findViewById(R.id.Pass_text_Field);
        EditText Email = (EditText) findViewById(R.id.Email_text);

        String user_name_str = user_name.getText().toString();
        String Pass_str = password.getText().toString();
        String Email_str = Email.getText().toString();

        if (user_name_str.isEmpty())
            user_name.setError("cannot be Empty");
        else if (Pass_str.isEmpty())
            password.setError("cannot be Empty");
        else if (Email_str.isEmpty() || Email_str.matches(regM))
            Email.setError("Cannot be Empty ");
        else
            new Register(user_name_str, Pass_str, Email_str, getApplicationContext()).execute();
    }
}
