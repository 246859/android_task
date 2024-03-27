package com.example.shoppingcart;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    //商品名称与价格数据集合
    private String[] titles = {"桌子", "苹果", "蛋糕", "线衣", "猕猴桃", "围巾", "桌子", "苹果", "蛋糕", "线衣", "猕猴桃", "围巾"};
    private String[] prices = {"1800元", "10元/kg", "300元", "350元", "10元/kg", "280元", "1800元", "10元/kg", "300元", "350元", "10元/kg", "280元"};
    //图片数据集合
    private int[] icons = {R.drawable.table, R.drawable.apple, R.drawable.cake, R.drawable.wireclothes, R.drawable.kiwifruit, R.drawable.scarf, R.drawable.table, R.drawable.apple, R.drawable.cake, R.drawable.wireclothes, R.drawable.kiwifruit, R.drawable.scarf};

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

        // 设置listview控件的adapter
        mListView = findViewById(R.id.lv);
        MyBaseAdapter myBaseAdapter = new MyBaseAdapter();
        mListView.setAdapter(myBaseAdapter);
    }

    class MyBaseAdapter extends BaseAdapter {

        // ViewHolder 缓存
        class ViewHolder {
            TextView title, price;
            ImageView imageView;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Object getItem(int position) {
            return titles[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            if (convertView == null) {
                // 将list_item.xml的结构转换为对象
                convertView = View.inflate(MainActivity.this, R.layout.list_item, null);
                holder = new ViewHolder();
                holder.title = convertView.findViewById(R.id.title);
                holder.price = convertView.findViewById(R.id.price);
                holder.imageView = convertView.findViewById(R.id.iv);
                // 将view控件对象存放到tag中
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            // 设置属性值
            holder.title.setText(titles[position]);
            holder.price.setText(prices[position]);
            holder.imageView.setBackgroundResource(icons[position]);

            return convertView;
        }
    }
}