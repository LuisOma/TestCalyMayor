package com.example.myapplication.common;

import android.content.Context;

import com.example.myapplication.data.network.CatalogoApi;
import com.example.myapplication.data.network.CatalogoService;
import com.example.myapplication.domain.CatalogoRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class ApplicationModule {

    @Singleton
    @Provides
    public static Retrofit provideRetrofit(Gson gson) {
        return new Retrofit.Builder()
                .baseUrl("https://www.calymayor.com.mx/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    @Provides
    public static Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    public static CatalogoService provideCatalogoService(Retrofit retrofit) {
        return retrofit.create(CatalogoService.class);
    }

    @Singleton
    @Provides
    public static CatalogoApi provideCatalogoApi(CatalogoService catalogoService) {
        return new CatalogoApi(catalogoService);
    }

    @Singleton
    @Provides
    public static CatalogoRepository provideCatalogoRepository(CatalogoApi catalogoApi, Context context) {
        return new CatalogoRepository(catalogoApi, context);
    }

    @Provides
    @Singleton
    public static Context provideContext(@ApplicationContext Context context) {
        return context;
    }
}
