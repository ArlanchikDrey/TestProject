package com.mobium.testproject.data.modules;

import android.content.Context;


import com.mobium.testproject.data.scopes.JobsApplicationScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityContextModule {
    private final Context context;

    public ActivityContextModule(Context context) {
        this.context = context;
    }
    @Named("activity_context")
    @JobsApplicationScope
    @Provides
    public Context context(){return context;}
}
