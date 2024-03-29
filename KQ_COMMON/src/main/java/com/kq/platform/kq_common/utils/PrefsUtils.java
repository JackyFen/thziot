package com.kq.platform.kq_common.utils;

import android.content.Context;
import android.content.SharedPreferences;


import com.kq.platform.kq_common.utils.cipher.Base64Cipher;
import com.kq.platform.kq_common.utils.cipher.Cipher;


/**
 * @author yuyh.
 * @date 16/4/9.
 */
public class PrefsUtils {

    private SharedPreferences sp;
    private static String fileName = "KQCOMMON";
    private Context context;

    public PrefsUtils(Context context) {
        this(context,fileName);
    }



    public PrefsUtils(Context context, String fileName) {
        sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        this.context = context;
    }

    /**
     * *************** get ******************
     */

    public String get(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    public boolean get(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    public float get(String key, float defValue) {
        return sp.getFloat(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public long get(String key, long defValue) {
        return sp.getLong(key, defValue);
    }

    public Object get(String key) {
        return get(key, (Cipher) null);
    }

    public Object get(String key, Cipher cipher) {
        try {
            String hex = get(key, (String) null);
            if (hex == null)
                return null;
            byte[] bytes = HexUtils.decodeHex(hex.toCharArray());
            if (cipher != null)
                bytes = cipher.decrypt(bytes);
            Object obj = TransformUtil.byteToObject(bytes);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * *************** put ******************
     */
    public void put(String key, Object ser) {
        put(key, ser, null);
        put("", "", new Base64Cipher());
    }

    public void put(String key, Object obj, Cipher cipher) {
        try {
            if (obj == null) {
                sp.edit().remove(key).commit();
            } else {
                byte[] bytes = TransformUtil.objectToByte(obj);
                if (cipher != null)
                    bytes = cipher.encrypt(bytes);
                put(key, HexUtils.encodeHexStr(bytes));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void put(String key, String value) {
        if (value == null) {
            sp.edit().remove(key).commit();
        } else {
            sp.edit().putString(key, value).commit();
        }
    }

    public void put(String key, boolean value) {
        sp.edit().putBoolean(key, value).commit();
    }

    public void put(String key, float value) {
        sp.edit().putFloat(key, value).commit();
    }

    public void put(String key, long value) {
        sp.edit().putLong(key, value).commit();
    }

    public void putInt(String key, int value) {
        sp.edit().putInt(key, value).commit();
    }

    public void clearAll() {
        sp.edit().clear().commit();
    }
}
