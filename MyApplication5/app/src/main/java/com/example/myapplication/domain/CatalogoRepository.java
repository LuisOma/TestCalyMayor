package com.example.myapplication.domain;

import android.content.Context;
import android.util.Log;

import com.example.myapplication.data.DataBaseHelper;
import com.example.myapplication.data.network.CatalogoApi;
import com.example.myapplication.data.network.SanitAbastecimiento;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class CatalogoRepository {
    private CatalogoApi catalogoApi;
    private DataBaseHelper dbHelper;

    @Inject
    public CatalogoRepository(CatalogoApi catalogoApi, Context context) {
        this.catalogoApi = catalogoApi;
        this.dbHelper = new DataBaseHelper(context);
    }

    public Single<List<SanitAbastecimiento>> getAllSanit() {
        List<SanitAbastecimiento> cachedBeers = dbHelper.getAllSanitAbastecimientos();

        if (cachedBeers.isEmpty()) {
            Log.println(Log.ERROR, "NETWORK", "No cached information");
            return catalogoApi.getAllSanit()
                    .doOnSuccess(this::cacheBeers);
        } else {
            return Single.just(cachedBeers);
        }
    }

    private void cacheBeers(List<SanitAbastecimiento> beers) {
        dbHelper.insertSanitAbastecimiento(beers);
    }

    public void updateOpcion(Integer id, String opcion){
        dbHelper.updateOpcion(id, opcion);
    }

    public void updateFoto(Integer id, byte[] foto){
        dbHelper.updateFoto(id, foto);
    }
}

