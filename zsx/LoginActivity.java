
package net.micode.notes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    // 用户名
    private EditText etUsername;
    //密码
    private EditText etPassword;
    // 定义登录按钮
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 加载登录界面布局
        setContentView(R.layout.activity_login);

        // 初始化输入框和按钮
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // 设置按钮的点击事件监听器
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 执行登录操作
                performLogin();
            }
        });
    }

    // 执行登录操作的方法
    private void performLogin() {
        //获取登录名和密码
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        //登录验证
        if (validateCredentials(username, password)) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            // 登录失败
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }
    
    private boolean validateCredentials(String username, String password) {
        // 验证用户名和密码
        
        return  ；
    }
}