package com.ccpunion.comrade.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 迈虹信息科技.All rights reserved
 *
 * @author LuoXiang
 * @ClassName ${ClassName}
 * @TitleName ${ClassName}
 * @Data 2017/7/12
 */
public class AppUtils {

    /**
     * 安装一个apk文件
     *
     * @param context
     * @param uriFile
     */
    public static void install(Context context, File uriFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(uriFile), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 卸载一个app
     *
     * @param context
     * @param packageName
     */
    public static void uninstall(Context context, String packageName) {
        //通过程序的包名创建URI
        Uri packageURI = Uri.parse("package:" + packageName);
        //创建Intent意图
        Intent intent = new Intent(Intent.ACTION_DELETE, packageURI);
        //执行卸载程序
        context.startActivity(intent);
    }

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName 应用包名
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        // 获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        // 用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        // 从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        // 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * 从apk中获取版本信息
     *
     * @param context
     * @param channelPrefix
     * @return
     */
    public static String getChannelFromApk(Context context, String channelPrefix) {
        //从apk包中获取
        ApplicationInfo appinfo = context.getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        //默认放在meta-inf/里， 所以需要再拼接一下
        String key = "META-INF/" + channelPrefix;
        String ret = "";
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (entryName.startsWith(key)) {
                    ret = entryName;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String[] split = ret.split(channelPrefix);
        String channel = "";
        if (split != null && split.length >= 2) {
            channel = ret.substring(key.length());
        }
        return channel;
    }

    /**
     * 获得apk版本号
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String versionName = "";
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(),
                    0);
            versionName = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (StringUtil.isNullOrEmpty(versionName)) {
            versionName = "";
        }

        return versionName;
    }

    /**
     * 获得apk版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(),
                    0);
            versionCode = packInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionCode;
    }

    /**
     * 获取Manifest Meta Data
     *
     * @param context
     * @param metaKey
     * @return
     */
    public static String getMetaData(Context context, String metaKey) {
        String name = context.getPackageName();
        ApplicationInfo appInfo;
        String msg = "";
        try {
            appInfo = context.getPackageManager().getApplicationInfo(name,
                    PackageManager.GET_META_DATA);
            msg = appInfo.metaData.getString(metaKey);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (StringUtil.isEmpty(msg)) {
            return "";
        }

        return msg;
    }

    /**
     * 获得渠道号
     *
     * @param context
     * @param channelKey
     * @return
     */
    public static String getChannelNo(Context context, String channelKey) {
        return getMetaData(context, channelKey);
    }


    public static String TAG = "AppUtils";

    /**
     * Returns version name
     *
     * @return
     */
    public static String getAppVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
//			throw new RuntimeException(e);
            return "";
        }

    }

    public static String getAppPackageName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).packageName;
        } catch (PackageManager.NameNotFoundException e) {
//			throw new RuntimeException(e);
            return "";
        }

    }

    /***
     * 获取应用程序的版本号
     *
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_CONFIGURATIONS).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
//			throw new RuntimeException(e);
            return 0;
        }
    }

    /***
     * 拨打电话
     *
     * @param context 上下文
     * @param phone   手机号码
     */
    public static void callPhone(Context context, String phone) {
        if (!TextUtils.isEmpty(phone)) {
            Intent intent = new Intent();// 新建一个意图
            Uri uri = Uri.parse("tel:" + phone);
            intent = new Intent(Intent.ACTION_DIAL, uri);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "号码格式错误！", Toast.LENGTH_SHORT).show();
        }
    }

    /***
     * 发送短信
     *
     * @param context 上下文
     * @param phone   手机号码
     */
    public static void sendSms(Context context, String phone) {
        if (!TextUtils.isEmpty(phone) && phone.matches(StringUtil.MOBILE)) {
            Uri uri = Uri.parse("smsto:" + phone);
            Intent it = new Intent(Intent.ACTION_SENDTO, uri);
            // it.putExtra("sms_body", "请输入内容...");
            context.startActivity(it);
        } else {
            Toast.makeText(context, "号码格式错误！", Toast.LENGTH_SHORT).show();
        }
    }

    public static void toContacts(Context context, String name, String phone) {
        try {
            Intent it = new Intent(Intent.ACTION_INSERT, Uri.withAppendedPath(
                    Uri.parse("content://com.android.contacts"), "contacts"));
            it.setType("vnd.android.cursor.dir/person");
            // it.setType("vnd.android.cursor.dir/contact");
            // it.setType("vnd.android.cursor.dir/raw_contact");
            // 联系人姓名
            it.putExtra(android.provider.ContactsContract.Intents.Insert.NAME,
                    name);
            // 公司
            // it.putExtra(android.provider.ContactsContract.Intents.Insert.COMPANY,
            // "北京XXXXXX公司");
            // email
            // it.putExtra(android.provider.ContactsContract.Intents.Insert.EMAIL,
            // email);
            // 手机号码
            it.putExtra(android.provider.ContactsContract.Intents.Insert.PHONE,
                    phone);

            // 单位电话
            // it.putExtra(
            // android.provider.ContactsContract.Intents.Insert.SECONDARY_PHONE,
            // "18600001111");
            // 住宅电话
            // it.putExtra(
            // android.provider.ContactsContract.Intents.Insert.TERTIARY_PHONE,
            // "010-7654321");
            // 备注信息
            // it.putExtra(android.provider.ContactsContract.Intents.Insert.JOB_TITLE,
            // "名片");

            context.startActivity(it);

        } catch (Exception e) {

        }
    }

    /**
     * 启动页面
     *
     * @param context
     * @param clazz
     * @param key
     * @param value
     */
    public static void startActivity(Context context,
                                     Class<? extends Activity> clazz, String key, String value) {

        if (clazz == null) {
            return;
        }

        Intent intent = new Intent(context, clazz);
        if (!TextUtils.isEmpty(key)) {
            intent.putExtra(key, value);
        }
        context.startActivity(intent);
    }

    public static void startActivity(Context context,
                                     Class<? extends Activity> clazz, String key, int value) {

        if (clazz == null) {
            return;
        }

        Intent intent = new Intent(context, clazz);
        if (!TextUtils.isEmpty(key)) {
            intent.putExtra(key, value);
        }
        context.startActivity(intent);
    }

}
