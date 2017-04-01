package com.meizitu.core;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.meizitu.ui.items.Item_GroupImageInfoListItem;

import cc.easyandroid.easydb.EasySqliteHelper;
import cc.easyandroid.easydb.core.TableManager;

/**
 * 简易数据库模块
 */
public class ImageDB extends EasySqliteHelper {
    static final int VERSION = 1;//Version must be >= 1,
    static final String DBNAME = "EasyAndroidDB";

    public static final String TABNAME_GROUPIMAGEINFO = "GROUPIMAGEINFO";//


    public ImageDB(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, TableManager tableManager) {
        System.out.println("easyandroid ImageDB onCreate=" + "ImageDB onCreate");
        try {
            tableManager.createTable(TABNAME_GROUPIMAGEINFO, Item_GroupImageInfoListItem.class);//
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion, TableManager tableManager) {
        try {
            tableManager.dropTable(db, TABNAME_GROUPIMAGEINFO, Item_GroupImageInfoListItem.class);//
            onCreate(db);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static ImageDB imageDB;

    public static ImageDB getInstance(Context context) {
        if (imageDB == null) {
            synchronized (ImageDB.class) {
                if (imageDB == null) {
                    imageDB = new ImageDB(context.getApplicationContext());
                }
            }
        }
        return imageDB;
    }
}
