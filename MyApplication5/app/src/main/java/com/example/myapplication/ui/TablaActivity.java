package com.example.myapplication.ui;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.adapters.TablaAdapter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class TablaActivity extends AppCompatActivity implements TablaAdapter.OnItemSelectedListener {

    private CatalogoViewModel viewModel;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Disposable mDisposable;
    private TablaAdapter tablaAdapter;

    private static final int REQUEST_IMAGE_CAPTURE = 2;

    private final ActivityResultLauncher<String> requestCameraPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    openCamera();
                } else {
                    Toast.makeText(this, "Camera permission is required to use the camera", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabla);

        viewModel = new ViewModelProvider(this).get(CatalogoViewModel.class);
        recyclerView = findViewById(R.id.recycler2);
        progressBar = findViewById(R.id.progress2);
        tablaAdapter = new TablaAdapter(getApplicationContext(), new ArrayList<>(), this);

        startViews();
        listenObservers();
    }

    private void startViews() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(tablaAdapter);
    }

    private void listenObservers() {
        mDisposable = viewModel.getAllSanit()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        beerResponses -> {
                            tablaAdapter.addItems(beerResponses);
                            progressBar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        },
                        throwable -> {
                            Log.println(Log.ERROR, "NETWORK", throwable.getMessage());
                            throwable.printStackTrace();
                        }
                );
    }

    @Override
    public void onIconClick(int id) {
        viewModel.currentPhotoId = id;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA);
        } else {
            openCamera();
        }
    }

    @Override
    public void onRadioSelectionChanged(int id, String selection) {
        viewModel.updateOpcion(id, selection);
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Error opening camera", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.WEBP, 100, bos);
            byte[] imgBytes = bos.toByteArray();
            if(viewModel.currentPhotoId != 0)
            viewModel.updateFoto(viewModel.currentPhotoId, imgBytes);
            tablaAdapter.updateImage(viewModel.currentPhotoId, imgBytes);
            viewModel.currentPhotoId = 0;
        }
    }
}
