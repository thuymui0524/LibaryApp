package com.example.btlltdd;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import com.example.btlltdd.database.DatabaseHelper;
import com.example.btlltdd.database.User;

public class MainActivity extends AppCompatActivity {
    EditText editTextUsername, editTextPassword;
    Button btnLogin;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextUsername = findViewById(R.id.etUsername);
        editTextPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        databaseHelper = new DatabaseHelper(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();


                if (!username.isEmpty() && !password.isEmpty()) {
                    boolean isValidUser = databaseHelper.checkUser(username, password);

                    if (isValidUser) {

                        Intent intent = new Intent(MainActivity.this, homeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        Toast.makeText(MainActivity.this, "Sai tên đăng nhập hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
