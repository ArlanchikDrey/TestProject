package com.mobium.testproject.data.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobium.testproject.data.JobsApi;
import com.mobium.testproject.data.scopes.JobsApplicationScope;


import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = OkHttpClientModule.class)
public class JobsModule {
    @Provides
    public JobsApi getRandomUserService(Retrofit retrofit) {
        return retrofit.create(JobsApi.class);
    }
    @JobsApplicationScope
    @Provides
    public Retrofit retrofit(OkHttpClient okHttpClient, GsonConverterFactory gsonConverterFactory, Gson gson) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://jobs.github.com/")
                .addConverterFactory(gsonConverterFactory)
                .build();
    }
    @Provides
    public Gson gson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    public GsonConverterFactory gsonConverterFactory(Gson gson){
        return GsonConverterFactory.create(gson);
    }
}
