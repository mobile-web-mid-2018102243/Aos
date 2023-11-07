package com.chunbae.moweb_android;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.net.toUri;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;

public class EditActivity extends AppCompatActivity {
    private ImageView backButton;
    private ImageView image;
    private EditText title;
    private TextView nickname;
    private EditText content;
    private TextView save;

    private String id;
    private String token;

    private final OkHttpClient okhttpClient = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        id = getIntent().getStringExtra("id");
        token = getSharedPreferences("MoWeb", MODE_PRIVATE).getString("Token", "");

        backButton = findViewById(R.id.edit_btn_finish);
        image = findViewById(R.id.edit_iv_image);
        title = findViewById(R.id.edit_tv_title);
        nickname = findViewById(R.id.edit_tv_nickname);
        content = findViewById(R.id.edit_tv_content);
        save = findViewById(R.id.edit_tv_save);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p0) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View p0) {
                String editUrl = "https://ddol.pythonanywhere.com/posts/" + id;

                RequestBody body = new FormBody.Builder()
                        .add("title", title.getText().toString())
                        .add("text", content.getText().toString())
                        .build();

                Request editRequest = new Request.Builder()
                        .url(editUrl)
                        .header("Authorization", "Token " + token)
                        .patch(body)
                        .build();

                okhttpClient.newCall(editRequest).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        EditContentDTO content = new Gson().fromJson(response.body().string(), EditContentDTO.class);
                        if (content.isSuccess()) {
                            finish();
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(EditActivity.this, "글 수정에 실패했습니다", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        String getUrl = "https://ddol.pythonanywhere.com/posts/" + id;
        Request getRequest = new Request.Builder()
                .url(getUrl)
                .header("Authorization", "Token " + token)
                .get()
                .build();

        okhttpClient.newCall(getRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ContentDetailDTO contentDetailDTO = new Gson().fromJson(response.body().string(), ContentDetailDTO.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Assuming the ContentDetailDTO class has been converted to Java and has appropriate getters.
                        image.setImageURI(Uri.parse(contentDetailDTO.getResult().getPostList().getImage()));
                        title.setText(contentDetailDTO.getResult().getPostList().getTitle());
                        EditActivity.this.content.setText(contentDetailDTO.getResult().getPostList().getText());
                        nickname.setText(contentDetailDTO.getResult().getPostList().getOwner());
                    }
                });
            }
        });
    }
}
