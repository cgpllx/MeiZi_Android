package com.meizitu.mvp.usecase;


import com.meizitu.mvp.repository.abs.DbDataSource;

import java.lang.reflect.Type;
import java.util.ArrayList;

import cc.easyandroid.easyclean.UseCase;
import cc.easyandroid.easydb.core.EasyDbObject;

/**
 * Created by cgpllx on 2016/8/16.
 */
public class GetDataFromDbUseCase<T extends EasyDbObject> extends UseCase<GetDataFromDbUseCase.RequestValues, GetDataFromDbUseCase.ResponseValue<T>> {
    public final DbDataSource mDbDataSource;

    public GetDataFromDbUseCase(DbDataSource dbDataSource) {
        this.mDbDataSource = dbDataSource;
    }

    @Override
    protected void executeUseCase(final RequestValues values) {
        this.mDbDataSource.getSingle(values.getTabeName(), values.getType(),values.getKey(),new DbDataSource.LoadDataCallback<T>() {
            @Override
            public void onDatasLoaded(T tasks) {
                ResponseValue responseValue = new ResponseValue(tasks);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onDataNotAvailable() {
                getUseCaseCallback().onError(null);
            }
        });
    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final String mTabeName;
        private final Type mType;
        private final String mKey;

        public RequestValues(String tabeName, Type type,String key) {
            mTabeName = tabeName;
            this.mType = type;
            this.mKey = key;
        }

        public Type getType() {
            return mType;
        }

        public String getTabeName() {
            return mTabeName;
        }

        public String getKey(){
            return mKey;
        }
    }

    public static final class ResponseValue<T extends EasyDbObject> implements UseCase.ResponseValue {

        private T mData;

        public ResponseValue(T data) {
            this.mData = data;
        }

        public T getDatas() {
            return mData;
        }
    }
}