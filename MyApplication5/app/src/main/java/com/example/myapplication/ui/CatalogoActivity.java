package com.example.myapplication.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.adapters.CatalogAdapter;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class CatalogoActivity extends AppCompatActivity {

    private CatalogoViewModel viewModel;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Disposable mDisposable;
    private CatalogAdapter catalogAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        viewModel = new ViewModelProvider(this).get(CatalogoViewModel.class);
        recyclerView = findViewById(R.id.recycler);
        progressBar = findViewById(R.id.progress);
        catalogAdapter = new CatalogAdapter(getApplicationContext(), new ArrayList<>());


        startViews();
        listenObservers();

    }

    private void startViews() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        if (recyclerView != null) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(catalogAdapter);
        }
    }

    private void listenObservers() {
        mDisposable = viewModel.getAllSanit()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        beerResponses -> {
                            catalogAdapter.addItems(beerResponses);
                            progressBar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        },
                        throwable -> {
                            Log.println(Log.ERROR, "NETWORK", throwable.getMessage());
                            throwable.printStackTrace();
                        }
                );
    }
}
