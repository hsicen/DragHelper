package com.xiaosong.draggableview.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

    }

    @OnClick({R.id.btn_start_draggable, R.id.btn_start_player})
    public void onClick(View view) {
        if (view.getId() ==R.id.btn_start_draggable){
            startActivity(new Intent(MainActivity.this, DraggableActivity.class));
        }else if (view.getId() ==R.id.btn_start_player){
            startActivity(new Intent(MainActivity.this, VideoPlayerActivity.class));
        }
    }

}
