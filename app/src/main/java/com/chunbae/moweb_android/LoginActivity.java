package com.chunbae.moweb_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.gson.Gson;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    private ConstraintLayout loginButton;
    private EditText id;
    private EditText pw;
    private TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.login_btn_login);
        id = findViewById(R.id.login_edt_id);
        pw = findViewById(R.id.login_edt_pw);
        signup = findViewById(R.id.login_tv_sign_up);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p0) {
                String url = "https://ddol.pythonanywhere.com/sign-in/";
                OkHttpClient okhttpClient = new OkHttpClient();
                FormBody body = new FormBody.Builder()
                        .add("id", id.getText().toString())
                        .add("password", pw.getText().toString())
                        .build();

                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();

                okhttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        LoginTokenDTO token = new Gson().fromJson(response.body().string(), LoginTokenDTO.class);
                        if (token.getResult().getToken().isEmpty()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "로그인에 실패하였습니다", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            getSharedPreferences("MoWeb", MODE_PRIVATE)
                                    .edit()
                                    .putString("Token", token.getResult().getToken())
                                    .apply();

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p0) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
