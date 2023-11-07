package com.chunbae.moweb_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

public class ListFragment extends Fragment {
    private RecyclerView recyclerView;
    private ContentListAdapter contentListAdapter;
    private String type;

    public ListFragment(String type) {
        this.type = type;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentListAdapter = new ContentListAdapter(getActivity(), new ContentListAdapter.ContentClickListener() {
            @Override
            public void onContentClicked(int id) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);

        recyclerView = view.findViewById(R.id.fg_list_rv_contents);
        recyclerView.setAdapter(contentListAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        String url = type.contains("MY") ? "https://ddol.pythonanywhere.com/posts/my-post" : "https://ddol.pythonanywhere.com/posts/";
        OkHttpClient okhttpClient = new OkHttpClient();
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MoWeb", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("Token", "");

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Token " + token)
                .get()
                .build();

        okhttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                ContentListDTO content = new Gson().fromJson(response.body().string(), ContentListDTO.class);

                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            contentListAdapter.setItemList(content.getResult().getPostList());
                            contentListAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }
}
