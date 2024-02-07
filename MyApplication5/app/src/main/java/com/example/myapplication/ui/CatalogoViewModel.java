package com.example.myapplication.ui;

import androidx.lifecycle.ViewModel;

import com.example.myapplication.data.network.SanitAbastecimiento;
import com.example.myapplication.domain.CatalogoRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Single;

@HiltViewModel
public class CatalogoViewModel extends ViewModel {

    private CatalogoRepository catalogoRepository;
    public int currentPhotoId;

    @Inject
    public CatalogoViewModel(CatalogoRepository catalogoRepository) {
        this.catalogoRepository = catalogoRepository;
    }

    public Single<List<SanitAbastecimiento>> getAllSanit(){
        return catalogoRepository.getAllSanit();
    }

    public void updateOpcion(Integer id, String opcion){
        catalogoRepository.updateOpcion(id, opcion);
    }

    public void updateFoto(Integer id, byte[] foto){
        catalogoRepository.updateFoto(id, foto);
    }
}
