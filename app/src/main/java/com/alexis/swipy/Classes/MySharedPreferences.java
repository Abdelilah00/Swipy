package com.alexis.swipy.Classes;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MySharedPreferences {
    private SharedPreferences prefs;

    public MySharedPreferences(Activity activity, String name) {
        this.prefs = activity.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public void saveArrayList(String key, ArrayList<String> list){
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
    }

    public ArrayList<String> getArrayList(String key) throws Exception{
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        return gson.fromJson(json, type)==null ? new ArrayList<String>() : gson.<ArrayList<String>>fromJson(json, type);
    }
}
