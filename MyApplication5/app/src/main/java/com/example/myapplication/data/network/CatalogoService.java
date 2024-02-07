package com.example.myapplication.data.network;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface CatalogoService {
    @GET("firebase/api/catalogos/Sanit_abastecimiento")
    Single<CatalogoResponse> getCatalogo();
}
