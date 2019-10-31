package com.mobium.testproject.data.modules;

import android.content.Context;


import androidx.annotation.NonNull;

import com.mobium.testproject.data.scopes.JobsApplicationScope;

import java.io.File;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

@Module(includes = ApplicationContextModule.class)
public class OkHttpClientModule {
    @Provides
    public OkHttpClient okHttpClient(Cache cache, HttpLoggingInterceptor httpLoggingInterceptor){
        return new OkHttpClient()
                .newBuilder()
                .cache(cache)
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }
    @Provides
    public Cache cache(File cacheFile){
        return new Cache(cacheFile, 10*1000*1000);//10 MB
    }
    @Provides
    @JobsApplicationScope
    public File cacheFile(@Named("application_context")Context context){
        File file = new File(context.getCacheDir(),"HttpCache");
        file.mkdirs();
        return file;
    }
    @Provides
    public HttpLoggingInterceptor httpLoggingInterceptor(){
        HttpLoggingInterceptor httpLoggingInterceptor =  new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String message) {
                Timber.i(message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return httpLoggingInterceptor;
    }
}
