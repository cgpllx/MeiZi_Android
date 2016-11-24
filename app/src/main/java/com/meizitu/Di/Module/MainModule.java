package com.meizitu.Di.Module;

import com.meizitu.Di.scope.ActivityScope;
import com.meizitu.mvp.presenter.QfangEasyWorkPresenter;
import com.meizitu.pojo.Category;
import com.meizitu.pojo.ResponseInfo;

import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cgpllx on 2016/11/24.
 */
@Module
public class MainModule {
//    private MainActivity mainActivity;
//
//    public MainModule(MainActivity mainActivity) {
//        this.mainActivity = mainActivity;
//    }
//
//
//    @Provides
//    @ActivityScope
//    MainActivity provideMainActivity() {
//        return mainActivity;
//    }


    @Provides
    @ActivityScope
    QfangEasyWorkPresenter<ResponseInfo<List<Category>>> provideMainActivityPresenter() {
//        QfangEasyWorkPresenter qfangEasyWorkPresenter = new QfangEasyWorkPresenter();
//        qfangEasyWorkPresenter.attachView(mainActivity);
        return new QfangEasyWorkPresenter();
    }

}
