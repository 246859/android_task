package com.example.register;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private EditText et_name, et_email, et_pwd;
    private Button btn_submit;
    private String name, email, pwd, sex, hobbys;
    private RadioGroup rg_sex;
    private CheckBox cb_sing, cb_dance, cb_read;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }

    private void init() {
        // 界面元素
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_pwd = findViewById(R.id.et_pwd);
        rg_sex = findViewById(R.id.rg_sex);
        cb_sing = findViewById(R.id.cb_sing);
        cb_dance = findViewById(R.id.cb_dance);
        cb_read = findViewById(R.id.cb_read);
        btn_submit = findViewById(R.id.btn_submit);

        // 设置监听器
        btn_submit.setOnClickListener(this);

        //设置复选框控件的点击事件的监听器
        cb_sing.setOnCheckedChangeListener(this);
        cb_dance.setOnCheckedChangeListener(this);
        cb_read.setOnCheckedChangeListener(this);

        // 单选框
        rg_sex.setOnCheckedChangeListener((RadioGroup group, int checkedId) -> {
            if (checkedId == R.id.rb_boy) {
                sex = "男";
            } else if (checkedId == R.id.rb_girl) {
                sex = "女";
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit) {
            // get data from view
            name = et_name.getText().toString().trim();
            email = et_email.getText().toString().trim();
            pwd = et_pwd.getText().toString().trim();

            if (TextUtils.isEmpty(name)) {
                Toast.makeText(MainActivity.this, "请输入名字",
                        Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(email)) {
                Toast.makeText(MainActivity.this, "请输入邮箱",
                        Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(pwd)) {
                Toast.makeText(MainActivity.this, "请输入密码",
                        Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(sex)) {
                Toast.makeText(MainActivity.this, "请选择性别",
                        Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(hobbys)) {
                Toast.makeText(MainActivity.this, "请选择兴趣爱好",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "注册成功",
                        Toast.LENGTH_SHORT).show();
                Log.i("MainActivity", "注册的用户信息：" + "名字：" + name + ", 邮箱："
                        + email + ", 性别：" + sex + ", 兴趣爱好：" + hobbys);
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String motion = buttonView.getText().toString();
        if (isChecked) {
            if (hobbys != null) {
                hobbys = String.join(",", hobbys, motion);
            } else {
                hobbys = String.join(",", motion);
            }
        } else {
            hobbys = hobbys.replace(motion, "").trim();
        }
    }
}