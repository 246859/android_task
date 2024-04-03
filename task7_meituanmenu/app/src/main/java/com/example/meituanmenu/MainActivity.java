package com.example.meituanmenu;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment leftFragment;
    private TextView tv_recommend, tv_must_buy;
    private Fragment rightFragment;
    //推荐菜单列表数据
    private String[] names1 = {"爆款*肥牛鱼豆腐骨肉相连三荤五素一份米饭", "豪华双人套餐",
            "【热销】双人套餐（含两份米饭）"};
    private String[] sales1 = {"月售520 好评度80%", "月售184 好评度68%",
            "月售114 好评度60%"};
    private String[] prices1 = {"￥23", "￥41", "￥32"};
    private int[] imgs1 = {R.drawable.recom_one, R.drawable.recom_two,
            R.drawable.recom_three};
    //进店必买菜单列表数据
    private String[] names2 = {"'蔬菜主义'1人套餐", "2人经典套餐", "3人经典套餐"};
    private String[] sales2 = {"月售26 好评度70%", "月售12 好评度50%",
            "月售4 好评度40%"};
    private String[] prices2 = {"￥44", "￥132", "￥180"};
    private int[] imgs2 = {R.drawable.must_buy_one, R.drawable.must_buy_two,
            R.drawable.must_buy_three};
    private Map<String, List<FoodBean>> map;

    private void init() {
        fragmentManager = getSupportFragmentManager();//获取fragmentManager
        //通过findFragmentById()方法获取leftFragment
        leftFragment = fragmentManager.findFragmentById(R.id.left);
        //获取左侧菜单栏中的控件
        tv_recommend = leftFragment.getView().findViewById(R.id.tv_recommend);
        tv_must_buy = leftFragment.getView().findViewById(R.id.tv_must_buy);
    }

    private void setData() {
        map = new HashMap<>();
        List<FoodBean> list1 = new ArrayList<>();
        List<FoodBean> list2 = new ArrayList<>();
        for (int i = 0; i < names1.length; i++) {
            FoodBean bean = new FoodBean();
            bean.setName(names1[i]);
            bean.setSales(sales1[i]);
            bean.setPrice(prices1[i]);
            bean.setImg(imgs1[i]);
            list1.add(bean);
        }
        map.put("1", list1);//将推荐菜单列表的数据添加到map集合中
        for (int i = 0; i < names2.length; i++) {
            FoodBean bean = new FoodBean();
            bean.setName(names2[i]);
            bean.setSales(sales2[i]);
            bean.setPrice(prices2[i]);
            bean.setImg(imgs2[i]);
            list2.add(bean);
        }
        map.put("2", list2); //将进店必买菜单列表的数据添加到map集合中
    }

    public void switchData(List<FoodBean> list) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();//开启一个事务
        //通过调用getInstance()方法实例化RightFragment
        rightFragment = (Fragment) new RightFragment().getInstance(list);
        //调用replace()方法
        fragmentTransaction.replace(R.id.right, rightFragment);
        fragmentTransaction.commit();
    }

    private void clickEvent() {
        tv_recommend.setOnClickListener(v -> {
            //调用switchData()方法填充Rightfragment中的数据
            switchData(map.get("1"));
            Log.i("recommend", "美团推荐");
            tv_recommend.setBackgroundColor(Color.WHITE);
            tv_must_buy.setBackgroundResource(R.color.gray);
        });
        tv_must_buy.setOnClickListener(v -> {
            Log.i("must buy", "美团进店必买");
            switchData(map.get("2"));
            tv_must_buy.setBackgroundColor(Color.WHITE);
            tv_recommend.setBackgroundResource(R.color.gray);
        });
        //设置首次进入界面后，默认需要显示的数据
        switchData(map.get("1"));
    }

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
        setData();
        init();
        clickEvent();
    }
}