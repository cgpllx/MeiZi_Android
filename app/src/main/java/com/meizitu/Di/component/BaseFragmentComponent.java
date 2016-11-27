package com.meizitu.Di.component;

import com.meizitu.Di.Module.BaseFragmentModule;
import com.meizitu.Di.scope.FragmentScope;
import com.meizitu.ui.fragments.ImageDetailsFragment;
import com.meizitu.ui.fragments.ImageListFragment;

import dagger.Component;

@FragmentScope
@Component(modules = BaseFragmentModule.class, dependencies = AppComponent.class)
public interface BaseFragmentComponent {
    void inject(ImageListFragment mainActivity);
    void inject(ImageDetailsFragment mainActivity);
}
