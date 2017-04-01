package com.meizitu.mvp.repository;

import android.content.Context;

import com.meizitu.core.ImageDB;
import com.meizitu.mvp.repository.abs.DbDataSource;

import java.lang.reflect.Type;
import java.util.ArrayList;

import cc.easyandroid.easydb.abs.DataAccesObject;
import cc.easyandroid.easydb.core.EasyDbObject;

/**
 * 数据库的仓库
 */
public class DbRepository implements DbDataSource {
    public final ImageDB imageDB;

    public DbRepository(Context context) {
        imageDB = ImageDB.getInstance(context);
    }


    @Override
    public <T extends EasyDbObject> void getAll(String tabName, Type type, LoadDatasCallback<T> callback) {
        DataAccesObject<T> dataAccesObject = imageDB.getDao(tabName);
        try {
            if(type==null){
                callback.onDatasLoaded(dataAccesObject.findAllFromTabName("DESC"));
            }else{
                callback.onDatasLoaded(dataAccesObject.findAllFromTabName("DESC",type));
            }
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        callback.onDataNotAvailable();
    }

    @Override
    public <T extends EasyDbObject> void getSingle(String tabName, Type type, String key, LoadDataCallback<T> callback) {
        DataAccesObject<T> dataAccesObject = imageDB.getDao(tabName);
        try {
            if(type==null){
                callback.onDatasLoaded(dataAccesObject.findById(key));
            }else{
                callback.onDatasLoaded(dataAccesObject.findById(key,type));
            }
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        callback.onDataNotAvailable();
    }

    @Override
    public <T extends EasyDbObject> boolean deleteAll(String tabName) {
        DataAccesObject<T> dataAccesObject = imageDB.getDao(tabName);
        try {
            return dataAccesObject.deleteAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public <T extends EasyDbObject> boolean deleteById(String tabName, String id) {
        DataAccesObject<T> dataAccesObject = imageDB.getDao(tabName);
        try {
            return dataAccesObject.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public <T extends EasyDbObject> boolean insert(String tabName, T dto) {
        DataAccesObject<T> dataAccesObject = imageDB.getDao(tabName);
        try {
            dataAccesObject.insert(dto);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public <T extends EasyDbObject> boolean insertAll(String tabName, ArrayList<T> arrayList) {
        DataAccesObject<T> dataAccesObject = imageDB.getDao(tabName);
        try {
            dataAccesObject.insertAll(arrayList);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
