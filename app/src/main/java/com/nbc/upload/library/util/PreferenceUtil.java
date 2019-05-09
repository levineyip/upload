package com.nbc.upload.library.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.nbc.upload.library.entity.AliCloudOSS;


public class PreferenceUtil {

    public static final String KEY_ALI_CLOUD_OSS_POLICY = "ali_cloud_oss_policy";

    /**
     * 保存阿里云OSS Policy信息
     *
     * @param aliCloudOSSPolicy
     */
    public static void setAliCloudOSSPolicy(Context context, String aliCloudOSSPolicy) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(KEY_ALI_CLOUD_OSS_POLICY, aliCloudOSSPolicy).apply();
    }

    /**
     * 获取阿里云OSS Policy信息
     *
     * @return
     */
    public static AliCloudOSS.OSSPolicy getAliCloudOSSPolicy(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String policy = sp.getString(KEY_ALI_CLOUD_OSS_POLICY, "");
        if (TextUtils.isEmpty(policy)) {
            return null;
        } else {
            return JsonUtil.parseObject(policy, AliCloudOSS.OSSPolicy.class);
        }
    }


}
