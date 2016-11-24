package com.meizitu.Di.component;

import com.meizitu.Di.Module.LoginModule;
import com.meizitu.Di.scope.ActivityScope;
import com.meizitu.ui.activitys.LoginActivity;

import dagger.Component;

@ActivityScope
@Component(modules = LoginModule.class, dependencies = AppComponent.class)
public interface LoginComponent {
    void inject(LoginActivity loginActivity);
}
