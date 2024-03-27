package com.example.saveqq;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class SharedSaveQQ {
    public static boolean saveUser(Context context, String user, String password) {
        SharedPreferences data = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = data.edit();
        edit.putString("user", user);
        edit.putString("password", password);
        return edit.commit();
    }

    public static Map<String, String> getUser(Context context) {
        SharedPreferences data = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        HashMap<String, String> map = new HashMap<>();
        map.put("user", data.getString("user", null));
        map.put("password", data.getString("password", null));
        return map;
    }
}
