package com.example.pickpeach;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PeachActivity extends AppCompatActivity implements View.OnClickListener {

    private int count = 0;//桃子个数
    // 按钮控件
    private Button btn_one, btn_two, btn_three, btn_four, btn_five, btn_six, btn_exit;

    private void init() {
        btn_one = findViewById(R.id.btn_one);
        btn_two = findViewById(R.id.btn_two);
        btn_three = findViewById(R.id.btn_three);
        btn_four = findViewById(R.id.btn_four);
        btn_five = findViewById(R.id.btn_five);
        btn_six = findViewById(R.id.btn_six);
        btn_exit = findViewById(R.id.btn_exit);
        btn_one.setOnClickListener(this);
        btn_two.setOnClickListener(this);
        btn_three.setOnClickListener(this);
        btn_four.setOnClickListener(this);
        btn_five.setOnClickListener(this);
        btn_six.setOnClickListener(this);
        btn_exit.setOnClickListener(this);
    }

    private void info(Button btn) {
        count++; //桃子个数加1
        // 设为不可见，表示已经摘除
        btn.setVisibility(View.INVISIBLE);
        Log.i("pickpeach","摘到" + count + "个桃子");
        Toast.makeText(PeachActivity.this, "摘到" + count + "个桃子",
                Toast.LENGTH_SHORT).show();
    }

    private void returnData() {
        Intent intent = new Intent();
        intent.putExtra("count", count);
        setResult(1, intent);
        PeachActivity.this.finish();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_exit) {
            returnData();
        } else {
            info(findViewById(id));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            returnData(); //调用数据回传方法
        }
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peach);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.peach), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        init();
    }
}
