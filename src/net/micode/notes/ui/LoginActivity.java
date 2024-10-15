
package net.micode.notes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        if(login(username,password)){
            ProgressDialog progressDialog = new      ProgressDialog(LoginActivity.this);
            progressDialog.setTitle(R.string.Loading);
                      progressDialog.setMessage("Loading...");
                      progressDialog.setCancelable(true);
                      progressDialog.show();;
                      Intent intent = new Intent(LoginActivity.this,NotesListActivity.class);
                      startActivity(intent);
                      finish();
                  }else {
                      Toast.makeText(LoginActivity.this, R.string.invalid,Toast.LENGTH_SHORT).show();}


 }

 public static boolean login(String username, String password) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    boolean isValidUser = false;

    try {
        // 加载数据库驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 建立数据库连接
        conn = DriverManager.getConnection("jdbc:mysql://10.4.92.58:3306/user table", mysql, PASSWORD);

        // 创建SQL查询语句
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        pstmt = conn.prepareStatement(sql);

        // 设置查询参数
        pstmt.setString(1, username);
        pstmt.setString(2, password);

        // 执行查询
        rs = pstmt.executeQuery();

        isValidUser = rs.next();
    } catch (ClassNotFoundException e) {
        System.out.println("MySQL JDBC driver not found.");
        e.printStackTrace();
    } catch (SQLException e) {
        System.out.println("Database connection failed.");
        e.printStackTrace();
    } 
    return isValidUser;
}
}