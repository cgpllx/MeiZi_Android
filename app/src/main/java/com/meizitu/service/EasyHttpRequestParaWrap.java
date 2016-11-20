package com.meizitu.service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * 用来封装http参数
 */
public class EasyHttpRequestParaWrap {
    private static final String HTTPPARAKEY = "httpParaKey";
    private Bundle paraBundle;

    public EasyHttpRequestParaWrap(Activity activity) {
        if (!activity.getIntent().hasExtra(HTTPPARAKEY)) {
            paraBundle = new Bundle();
            activity.getIntent().putExtra(HTTPPARAKEY, paraBundle);
        } else {
            paraBundle = activity.getIntent().getBundleExtra(HTTPPARAKEY);
            if (paraBundle == null) {
                new IllegalArgumentException("paraBundle is null");
            }
        }
    }


    public EasyHttpRequestParaWrap(Fragment fragment) {
        obtainPara(fragment.getArguments());
    }

    private void obtainPara(Bundle bundle) {
        if (bundle != null) {
            if (bundle.containsKey(HTTPPARAKEY)) {
                paraBundle = bundle.getBundle(HTTPPARAKEY);
                if (paraBundle == null) {
                    new IllegalArgumentException("paraBundle is null");
                }
            } else {
                paraBundle = new Bundle();
                bundle.putBundle(HTTPPARAKEY, paraBundle);
            }
        } else {
            new IllegalArgumentException("bundle no can null");
        }
    }


//静态方法------------------------

    /**
     * 将http的参数绑定到intent中，可以为HTTPPARAKEY
     *
     * @param intent
     * @param paraBundle
     */
    public static void bindingHttpPara(Intent intent, Bundle paraBundle) {
        intent.putExtra(HTTPPARAKEY, paraBundle);
    }

    /**
     * 将http的参数绑定到intent中，可以为HTTPPARAKEY  自定义key
     *
     * @param intent
     * @param paraBundle
     */
    public static void bindingHttpParaCustomKey(String key, Intent intent, Bundle paraBundle) {
        intent.putExtra(key, paraBundle);
    }

    public static void putPrarToFragment(Fragment fragment, String key, String value) {
        getHttpParaFromFragment(fragment).putString(key, value);
    }

    /**
     * 将http的参数绑定到bundle中，可以为HTTPPARAKEY
     *
     * @param bundle
     * @param paraBundle
     */
    public static void bindingHttpPara(Bundle bundle, Bundle paraBundle) {
        Bundle primitiveParaBundle = bundle.getBundle(HTTPPARAKEY);
        if (primitiveParaBundle == null) {
            primitiveParaBundle = new Bundle();
        }
        primitiveParaBundle.putAll(paraBundle);
        bundle.putBundle(HTTPPARAKEY, primitiveParaBundle);//每次绑定新的，不和数据源挂钩
    }

    public static void clearHttpPara(Bundle bundle) {
        if (bundle != null) {
            bundle.remove(HTTPPARAKEY);
        }
    }

    public static void clearHttpPara(Fragment fragment) {
        Bundle bundle = fragment.getArguments();
        if (bundle != null) {
            bundle.remove(HTTPPARAKEY);
        }
    }

    /**
     * intent
     *
     * @param intent
     * @return
     */
    public static Bundle getHttpParaFromIntent(Intent intent) {
        if (intent != null) {
            return intent.getBundleExtra(HTTPPARAKEY);
        }
        return null;
    }

    /**
     * 从bundle中获取参数
     * 如果没有就创建一个并put进入
     *
     * @param bundle
     * @return
     */
    public static Bundle getHttpParaFromBundle(Bundle bundle) {
        if (bundle != null) {
            Bundle paraBundle = bundle.getBundle(HTTPPARAKEY);
            if (paraBundle == null) {
                paraBundle = new Bundle();
                bindingHttpPara(bundle, paraBundle);
            }
            return paraBundle;
        }
        return new Bundle();
    }

    /**
     * 从bundle中获取参数
     *
     * @param bundle
     * @return
     */
    public static Bundle getHttpParaFromBundleCustomKey(String key, Bundle bundle) {
        if (bundle != null) {
            return bundle.getBundle(key);
        }
        return new Bundle();
    }

    /**
     * 从activity中获取参数
     *
     * @param activity
     * @return
     */
    public static Bundle getHttpParaFromActivity(Activity activity) {
        if (activity != null) {
            return getHttpParaFromIntent(activity.getIntent());
        }
        return null;
    }

    /**
     * 从fragment中获取参数
     *
     * @param fragment
     * @return
     */
    public static Bundle getHttpParaFromFragment(Fragment fragment) {
        if (fragment != null) {
            return getHttpParaFromBundle(fragment.getArguments());
        }
        return new Bundle();
    }

    //静态方法------------------------ end
    public boolean addHttpPara(String key, String value) {
        paraBundle.putString(key, value);
        return true;
    }

    public boolean removeHttpPara(String key) {
        paraBundle.remove(key);
        return true;
    }

    public String getHttpPara(String key) {
        return paraBundle.getString(key);
    }

    public Bundle getParaBundle() {
        return paraBundle;
    }

    public void addAll(Bundle bundle) {
        paraBundle.putAll(bundle);
    }

    public void destroy() {
        paraBundle.clear();
        paraBundle = null;
    }

}
