package com.meizitu.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cc.easyandroid.easylog.EALog;
import cc.easyandroid.easyutils.EasyToast;

/**
 * Created by Administrator on 2016/2/25.
 */
public class QfangUtils {
    public static void callTel(Context context, String phoneNumber) {
        if (PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {
            Intent intent = new Intent();
            // 系统默认的action，用来打开默认的电话界面
            //intent.setAction(Intent.ACTION_CALL);
            intent.setAction(Intent.ACTION_DIAL); // 魅族手机不支持直接拨号
            // Intent intent = new
            // Intent(Intent.ACTION_DIAL,Uri.parse("tel:13850734494"));
            // 需要拨打的号码
            intent.setData(Uri.parse("tel:" + phoneNumber));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            EasyToast.showShort(context.getApplicationContext(), "电话格式错误");
        }
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);
    }

    /**
     * 测试发现msg不能是空啊，不然不能进入聊天界面就直接关闭了
     *
     * @param context
     * @param massage
     */
    public static void startWhatapp(Context context, String massage) {
        try {
            Intent sendIntent = new Intent();
            sendIntent.setPackage("com.whatsapp");
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, massage);//这里是要发送的文字
            sendIntent.setType("text/plain");
            context.startActivity(sendIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String floatFormat(float price) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(price);// format 返回的是字符串
        return p;
    }

    public static String floatFormat(double price) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(price);// format 返回的是字符串
        return p;
    }

    public static String floatIntegerFormat(float price) {
        DecimalFormat decimalFormat = new DecimalFormat("0");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(price);// format 返回的是字符串
        return p;
    }

    public static String floatIntegerFormatPrice(double price) {
        DecimalFormat decimalFormat = new DecimalFormat("0");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(price);// format 返回的是字符串
        return splitString(p, 3, ",").toString();
    }


    public static String longtime2StringFormat_y(long time) {
        // DateFormat dateFm = SimpleDateFormat.getInstance();
        SimpleDateFormat dateFm = new SimpleDateFormat("yyyy年", Locale.CHINA); // 格式化当前系统日期
        String dateTime = dateFm.format(time);
        return dateTime;
    }

    public static String longtime2StringFormat_mmyyyy(long time) {
        // DateFormat dateFm = SimpleDateFormat.getInstance();
        if (time <= 0) {
            return "";
        }
        SimpleDateFormat dateFm = new SimpleDateFormat("MM/yyyy", Locale.CHINA); // 格式化当前系统日期
        String dateTime = dateFm.format(time);
        return dateTime;
    }

    public static String longtime2StringFormat_ymdhms(long time) {
        // DateFormat dateFm = SimpleDateFormat.getInstance();
        SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA); // 格式化当前系统日期
        String dateTime = dateFm.format(time);
        return dateTime;
    }

    public static String longtime2StringFormat_ym(long time) {
        // DateFormat dateFm = SimpleDateFormat.getInstance();
        if (time <= 0l) {
            return "";
        }
        SimpleDateFormat dateFm = new SimpleDateFormat("yyyy年MM月", Locale.CHINA); // 格式化当前系统日期
        String dateTime = dateFm.format(time);
        return dateTime;
    }

    public static String longtime2StringFormat_m(long time) {
        if (time <= 0l) {
            return "";
        }
        SimpleDateFormat dateFm = new SimpleDateFormat("MM月", Locale.CHINA); // 格式化当前系统日期
        String dateTime = dateFm.format(time);
        if (dateTime.startsWith("0")) {
            System.out.println("dateTime.length()=" + dateTime.length());
            return dateTime.substring(1);
        }
        return dateTime;
    }

    public static String longtime2StringFormat_m_y(long time) {
        if (time <= 0l) {
            return "";
        }
        SimpleDateFormat dateFm = new SimpleDateFormat("MM/yy", Locale.CHINA); // 格式化当前系统日期
        String dateTime = dateFm.format(time);
        return dateTime;
    }

    public static String longtime2StringFormat_ymd(long time) {
        // DateFormat dateFm = SimpleDateFormat.getInstance();
        if (time <= 0l) {
            return "";
        }
        SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA); // 格式化当前系统日期
        String dateTime = dateFm.format(time);
        return dateTime;
    }

    public static String longtime2StringFormat_d_m_y(long time) {
        // DateFormat dateFm = SimpleDateFormat.getInstance();
        if (time <= 0l) {
            return "";
        }
        SimpleDateFormat dateFm = new SimpleDateFormat("dd/MM/yyyy", Locale.CHINA); // 格式化当前系统日期
        String dateTime = dateFm.format(time);
        return dateTime;
    }

    /**
     * 字符串分割
     *
     * @param characterToBeSplit 被分割的字符串
     * @param splitLength        多少为分割
     * @param SplitCharacter     用什么符号分割
     * @return
     */
    public static CharSequence splitString(CharSequence characterToBeSplit,
                                           int splitLength, CharSequence SplitCharacter) {
        int characterToBeSplitlength = characterToBeSplit.length();
        if (characterToBeSplitlength < splitLength) {
            return characterToBeSplit;
        }
        StringBuilder sb = new StringBuilder(characterToBeSplit).reverse();
        StringBuilder strTemp = new StringBuilder();
        for (int i = 0; i < characterToBeSplitlength; i = i + splitLength) {
            if (i + splitLength >= characterToBeSplitlength) {
                strTemp.append(sb.subSequence(i, characterToBeSplitlength));
            } else {
                strTemp.append(sb.subSequence(i, i + splitLength));
                strTemp.append(SplitCharacter);
            }
        }
        return strTemp.reverse().toString();
    }

    /**
     * 添加千位符，小数点最后是0的要去掉0
     *
     * @param price
     * @return
     */
    public static String formatPrice(double price) {
        DecimalFormat df = new DecimalFormat("#,###.00");
        String price_string = df.format(price);
        if (!TextUtils.isEmpty(price_string)) {
            StringBuilder stringBuilder = new StringBuilder(price_string);
            if ('0' == stringBuilder.charAt(stringBuilder.length() - 1)) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);//删除0
                if ('0' == stringBuilder.charAt(stringBuilder.length() - 1)) {
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);//删除0
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);//删除点
                }
            }
            return stringBuilder.toString();
        }
        return price_string;
    }

    public static String formatPriceToInteger(double price) {
        DecimalFormat df = new DecimalFormat("#,###");
        String price_string = df.format(price);
        return price_string;

    }

    public static String formatPriceToWan(double price) {
        double wanPrice = price / 10000;
        DecimalFormat df = new DecimalFormat("#,###.00");
        String price_string = df.format(wanPrice);
        if (!TextUtils.isEmpty(price_string)) {
            StringBuilder stringBuilder = new StringBuilder(price_string);
            if ('0' == stringBuilder.charAt(stringBuilder.length() - 1)) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);//删除0
                if ('0' == stringBuilder.charAt(stringBuilder.length() - 1)) {
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);//删除0
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);//删除点
                }
            }
            return stringBuilder.toString();
        }
        return price_string;
    }

    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            MessageDigest e = MessageDigest.getInstance("MD5");
            e.update(key.getBytes());
            cacheKey = bytesToHexString(e.digest());
        } catch (NoSuchAlgorithmException var3) {
            cacheKey = String.valueOf(key.hashCode());
        }

        return cacheKey;
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < bytes.length; ++i) {
            String hex = Integer.toHexString(255 & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }

            sb.append(hex);
        }

        return sb.toString();
    }

    public static void share(Activity launchingActivity, CharSequence text) {
        ShareCompat.IntentBuilder.from(launchingActivity)
                .setType("text/plain")//
                .setText(text)//
                .setChooserTitle("Q房網香港")
                .startChooser();
    }

    public static boolean checkFirstItemCompletelyVisible(RecyclerView.LayoutManager mLayoutManager) {
        int firstVisiblePosition = ((RecyclerView.LayoutParams) mLayoutManager.getChildAt(0).getLayoutParams()).getViewLayoutPosition();
        if (firstVisiblePosition == 0) {
            return true;
        }
        return false;
    }

    public static Map<String, String> BundleToMap(Bundle bundle) {
        HashMap params = new HashMap();
        Iterator i$ = bundle.keySet().iterator();

        while (i$.hasNext()) {
            String key = (String) i$.next();
            String value = bundle.getString(key);
            if (!TextUtils.isEmpty(value)) {
                params.put(key, value);
            }
        }

        return params;
    }

    public static void mapToBundle(ArrayMap<String, String> paras, Bundle bundlepara) {
        for (int i = 0; i < paras.size(); i++) {
            String key = paras.keyAt(i);
            EALog.e("key =" + key);
            bundlepara.putString(key, paras.get(key));
        }
    }
}
