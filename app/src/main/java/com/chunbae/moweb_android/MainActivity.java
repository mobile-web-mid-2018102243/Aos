package com.chunbae.moweb_android;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigation;
    private TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleText = findViewById(R.id.main_tv_title);
        bottomNavigation = findViewById(R.id.main_layout_bottom_navigation);

        bottomNavigation.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.main_bottom_list:
                        titleText.setText("목록 조회");
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_layout_container, ListFragment.newInstance("LIST"))
                                .commit();
                        break;
                    case R.id.main_bottom_my_list: // 메뉴 아이템의 ID를 확인하세요.
                        titleText.setText("내 글 조회");
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_layout_container, ListFragment.newInstance("MY_LIST"))
                                .commit();
                        break;
                }
                return true;
            }
        });

        // 초기 선택 항목 설정
        bottomNavigation.setSelectedItemId(R.id.main_bottom_list);
    }
}
