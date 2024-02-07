package com.example.myapplication.data.network;

import java.util.List;
import javax.inject.Inject;
import io.reactivex.rxjava3.core.Single;

public class CatalogoApi {
    private CatalogoService catalogoService;

    @Inject
    public CatalogoApi(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    public Single<List<SanitAbastecimiento>> getAllSanit() {
        return catalogoService.getCatalogo()
                .map(catalogoResponse -> catalogoResponse.getSanitAbastecimiento());
    }
}

