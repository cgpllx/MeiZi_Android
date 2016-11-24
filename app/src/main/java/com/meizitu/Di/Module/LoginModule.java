package com.meizitu.Di.Module;

import com.meizitu.Di.scope.ActivityScope;
import com.meizitu.mvp.presenter.QfangEasyWorkPresenter;
import com.meizitu.pojo.ResponseInfo;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cgpllx on 2016/11/24.
 */
@Module
public class LoginModule {
//    private LoginActivity loginActivity;

//    public LoginModule(LoginActivity loginActivity) {
//        this.loginActivity = loginActivity;
//    }
//
//
//    @Provides
//    @ActivityScope
//    LoginActivity provideMainActivity() {
//        return loginActivity;
//    }


    @Provides
    @ActivityScope
    QfangEasyWorkPresenter<ResponseInfo> provideMainActivityPresenter() {
        return new QfangEasyWorkPresenter();
    }

}
