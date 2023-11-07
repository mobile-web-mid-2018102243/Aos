package com.chunbae.moweb_android;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

public class SignUpActivity extends AppCompatActivity {
    private ImageView backButton;
    private EditText nickname;
    private EditText id;
    private EditText pw;
    private ConstraintLayout finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        backButton = findViewById(R.id.sign_up_btn_back);
        nickname = findViewById(R.id.sign_up_edt_nickname);
        id = findViewById(R.id.sign_up_edt_id);
        pw = findViewById(R.id.sign_up_edt_pw);
        finishButton = findViewById(R.id.sign_up_btn_finish);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nickname.getText().toString().isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "닉네임을 확인해주세요", Toast.LENGTH_SHORT).show();
                } else if (id.getText().toString().isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "아이디를 확인해주세요", Toast.LENGTH_SHORT).show();
                } else if (pw.getText().toString().isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    String url = "https://ddol.pythonanywhere.com/sign-up/";
                    OkHttpClient okhttpClient = new OkHttpClient();
                    FormBody body = new FormBody.Builder()
                            .add("nickname", nickname.getText().toString())
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
                            SignUpDTO result = new Gson().fromJson(response.body().string(), SignUpDTO.class);
                            if (result.getSuccess()) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(SignUpActivity.this, "회원가입에 성공했습니다", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                finish();
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(SignUpActivity.this, "회원가입에 실패했습니다", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }
}
