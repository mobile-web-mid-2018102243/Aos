package com.chunbae.moweb_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URL;

public class DetailActivity extends AppCompatActivity {
    private ImageView backButton;
    private ImageView image;
    private TextView title;
    private TextView nickname;
    private TextView content;
    private LinearLayout ownerMenu;
    private TextView edit;
    private TextView delete;

    private String id;
    private String token;

    private final OkHttpClient okhttpClient = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        id = String.valueOf(getIntent().getIntExtra("id", 0));
        SharedPreferences prefs = getSharedPreferences("MoWeb", MODE_PRIVATE);
        token = prefs.getString("Token", "");

        backButton = findViewById(R.id.detail_btn_finish);
        image = findViewById(R.id.detail_iv_image);
        title = findViewById(R.id.detail_tv_title);
        nickname = findViewById(R.id.detail_tv_nickname);
        content = findViewById(R.id.detail_tv_content);
        ownerMenu = findViewById(R.id.detail_layout_only_owner);
        edit = findViewById(R.id.detail_only_owner_tv_edit);
        delete = findViewById(R.id.detail_only_owner_tv_delete);

        backButton.setOnClickListener(v -> finish());

        edit.setOnClickListener(v -> {
            Intent intent = new Intent(DetailActivity.this, EditActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        });

        delete.setOnClickListener(v -> {
            String deleteUrl = "https://ddol.pythonanywhere.com/posts/" + id;

            Request deleteRequest = new Request.Builder()
                    .url(deleteUrl)
                    .header("Authorization", "Token " + token)
                    .delete()
                    .build();

            okhttpClient.newCall(deleteRequest).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    EditContentDTO content = new Gson().fromJson(response.body().string(), EditContentDTO.class);
                    if (content.isSuccess()) {
                        runOnUiThread(() -> {
                            Toast.makeText(DetailActivity.this, "글이 삭제되었습니다", Toast.LENGTH_SHORT).show();
                            finish();
                        });
                    } else {
                        runOnUiThread(() -> Toast.makeText(DetailActivity.this, "글 삭제에 실패했습니다", Toast.LENGTH_SHORT).show());
                    }
                }
            });
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
                ContentDetailDTO content = new Gson().fromJson(response.body().string(), ContentDetailDTO.class);
                new Thread(() -> {
                    try {
                        URL url = new URL(content.getResult().getPostList().getImage());
                        URLConnection conn = url.openConnection();
                        conn.connect();
                        BitmapFactory.decodeStream(conn.getInputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();

                runOnUiThread(() -> {
                    title.setText(content.getResult().getPostList().getTitle());
                    DetailActivity.this.content.setText(content.getResult().getPostList().getText());
                    nickname.setText(content.getResult().getPostList().getOwner());

                    if (content.getResult().getPostList().isMine()) {
                        ownerMenu.setVisibility(View.VISIBLE);
                    } else {
                        ownerMenu.setVisibility(View.GONE);
                    }
                });
            }
        });
    }
}
