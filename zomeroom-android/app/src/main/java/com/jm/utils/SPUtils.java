package com.jm.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.jm.base.BaseApplication;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by pc on 2018/5/29.
 */

public class SPUtils {
    private static final String FILE_NAME = "";

    public SPUtils() {
    }

    public SPUtils getSharedPreferences() {
        SPUtils spUtils = new SPUtils();
        return spUtils;
    }

    public static void put(String key, Object object) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(BaseApplication.application);
        SharedPreferences.Editor editor = sp.edit();
        if(object instanceof String) {
            editor.putString(key, (String)object);
        } else if(object instanceof Integer) {
            editor.putInt(key, ((Integer)object).intValue());
        } else if(object instanceof Boolean) {
            editor.putBoolean(key, ((Boolean)object).booleanValue());
        } else if(object instanceof Float) {
            editor.putFloat(key, ((Float)object).floatValue());
        } else if(object instanceof Long) {
            editor.putLong(key, ((Long)object).longValue());
        } else {
            editor.putString(key, null == object?"":object.toString());
        }

        SPUtils.SharedPreferencesCompat.apply(editor);
    }

    public static Object get(String key, Object defaultObject) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(BaseApplication.application);
        return defaultObject instanceof String?sp.getString(key, (String)defaultObject):(defaultObject instanceof Integer?Integer.valueOf(sp.getInt(key, ((Integer)defaultObject).intValue())):(defaultObject instanceof Boolean?Boolean.valueOf(sp.getBoolean(key, ((Boolean)defaultObject).booleanValue())):(defaultObject instanceof Float?Float.valueOf(sp.getFloat(key, ((Float)defaultObject).floatValue())):(defaultObject instanceof Long?Long.valueOf(sp.getLong(key, ((Long)defaultObject).longValue())):null))));
    }

    public static void remove(String key) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(BaseApplication.application);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SPUtils.SharedPreferencesCompat.apply(editor);
    }

    public static void clear() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(BaseApplication.application);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SPUtils.SharedPreferencesCompat.apply(editor);
    }

    public static boolean contains(String key) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(BaseApplication.application);
        return sp.contains(key);
    }

    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(BaseApplication.application);
        return sp.getAll();
    }

    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        private SharedPreferencesCompat() {
        }

        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply", new Class[0]);
            } catch (NoSuchMethodException var1) {
                return null;
            }
        }

        public static void apply(SharedPreferences.Editor editor) {
            try {
                if(sApplyMethod != null) {
                    sApplyMethod.invoke(editor, new Object[0]);
                    return;
                }
            } catch (IllegalAccessException | IllegalArgumentException var2) {
                ;
            } catch (InvocationTargetException var3) {
                ;
            }

            editor.commit();
        }
    }
}
