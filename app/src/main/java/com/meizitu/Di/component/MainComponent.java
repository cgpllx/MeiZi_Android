package com.meizitu.Di.component;

import com.meizitu.Di.Module.MainModule;
import com.meizitu.Di.scope.ActivityScope;
import com.meizitu.ui.activitys.MainActivity;

import dagger.Component;

@ActivityScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
