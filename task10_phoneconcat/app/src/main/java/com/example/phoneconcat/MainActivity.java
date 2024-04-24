package com.example.phoneconcat;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ContactAdapter adapter;
    private RecyclerView rv_contact;

    String[] permissionList;

    private void setData() {
        List<ContactInfo> contactInfos = getContacts();
        contactInfos.forEach((contactInfo) -> {
            Log.i("", "通讯录: " + contactInfo.getContactName() + "," + contactInfo.getPhoneNumber());
        });
        adapter = new ContactAdapter(this, contactInfos);
        rv_contact.setAdapter(adapter);
    }

    public List<ContactInfo> getContacts() {
        List<ContactInfo> contactInfos = new ArrayList<>();
        Cursor cursor = getContentResolver().query(ContactsContract.
                Contacts.CONTENT_URI, null, null, null, null);
        if (contactInfos != null) contactInfos.clear();//清除集合中的数据
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            @SuppressLint("Range") int isHas = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
            if (isHas > 0) {
                Cursor c = getContentResolver().query(ContactsContract.
                                CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID +
                                " = " + id, null, null);
                while (c.moveToNext()) {
                    ContactInfo info = new ContactInfo();
                    info.setContactName(name);
                    @SuppressLint("Range") String number = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).trim();
                    number = number.replace(" ", "");
                    number = number.replace("-", "");
                    info.setPhoneNumber(number);
                    contactInfos.add(info);
                }
                c.close();
            }
        }
        cursor.close();
        contactInfos.forEach((contactInfo) -> {
            Log.i("联系人", contactInfo.getContactName() + ":" + contactInfo.getPhoneNumber());
        });
        return contactInfos;
    }

    private void init() {
        rv_contact = findViewById(R.id.rv_contact);
        rv_contact.setLayoutManager(new LinearLayoutManager(this));
        getPermissions();
    }

    public void getPermissions() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissionList = new String[]{"android.permission.READ_CONTACTS"};
            ArrayList<String> list = new ArrayList<String>();
            // 循环判断所需权限中有哪个尚未被授权
            for (int i = 0; i < permissionList.length; i++) {
                if (ActivityCompat.checkSelfPermission(this, permissionList[i]) != PackageManager.PERMISSION_GRANTED)
                    list.add(permissionList[i]);
            }
            if (list.size() > 0) {
                ActivityCompat.requestPermissions(this,
                        list.toArray(new String[list.size()]), 1);
            } else {
                setData();//后续创建该方法
            }
        } else {
            setData();  //后续创建该方法
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals("android.permission.READ_CONTACTS")
                        && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "读取通讯录权限申请成功",
                            Toast.LENGTH_SHORT).show();
                    Log.i("MainActivity", "读取通讯录权限申请成功");
                    setData();//后续创建该方法
                } else {
                    Log.i("MainActivity", "读取通讯录权限申请失败");
                    Toast.makeText(this, "读取通讯录权限申请失败",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
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
        init();
    }
}