package com.meizitu.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;

import com.meizitu.pojo.AppInfo;

public class AppUpGradeManager {

	public static long downLoadApk(Context context, AppInfo appInfo) {
		DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
		//在执行下载前注册内容监听者
		//  registerContentObserver();
		DownloadManager.Request request = new DownloadManager.Request(Uri.parse(appInfo.getApkUrl()));
		/**设置用于下载时的网络状态*/
		request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
		/**设置通知栏是否可见*/
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {// sdk17
			request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		}
		/**设置漫游状态下是否可以下载*/
		request.setAllowedOverRoaming(true);
		/**如果我们希望下载的文件可以被系统的Downloads应用扫描到并管理，
		 我们需要调用Request对象的setVisibleInDownloadsUi方法，传递参数true.*/
		request.setVisibleInDownloadsUi(true);
		/**设置文件保存路径*/
		request.setDestinationInExternalFilesDir(context.getApplicationContext(), appInfo.getAppName(), appInfo.getAppName() + appInfo.getVersionCode() + ".apk");
		/**将下载请求放入队列， return下载任务的ID*/
		return downloadManager.enqueue(request);
	}
}
