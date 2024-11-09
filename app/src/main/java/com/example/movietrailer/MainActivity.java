package com.example.movietrailer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movietrailer.ui.pager.PagerFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // todo: 가로모드 대응 필요
        // 가로모드일 변경 시 현재 선택된 페이지 유지하기 위해 조건문 추가
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new PagerFragment())
                    .commitNow(); // commitNow() 사용 이유, https://github.com/lminsu/MovieTrailer/issues/3#issue-2600864620
        }
    }
}