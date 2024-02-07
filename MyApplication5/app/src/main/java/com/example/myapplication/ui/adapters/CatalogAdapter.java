package com.example.myapplication.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.network.SanitAbastecimiento;

import java.util.ArrayList;
import java.util.List;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder> {
    private Context context;
    private ArrayList<SanitAbastecimiento> catalogList;

    public CatalogAdapter(Context context, ArrayList<SanitAbastecimiento> catalogList) {
        this.context = context;
        this.catalogList = catalogList;
    }

    @Override
    public CatalogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.catalog_item, parent, false);
        return new CatalogViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CatalogViewHolder holder, int position) {
        SanitAbastecimiento item = catalogList.get(position);
        holder.idNumber.setText(String.valueOf(item.getIdAbastecimiento()));
        holder.tipo.setText(item.getTipoAbastecimiento());
        holder.fecha.setText(item.getFechaCreacion());
    }

    @Override
    public int getItemCount() {
        return catalogList.size();
    }

    public void addItems(List<SanitAbastecimiento> list) {
        catalogList.clear();
        catalogList.addAll(list);
        notifyDataSetChanged();
    }

    public static class CatalogViewHolder extends RecyclerView.ViewHolder {
        public TextView idNumber, tipo, fecha;

        public CatalogViewHolder(View view) {
            super(view);
            idNumber = view.findViewById(R.id.idNumber);
            tipo = view.findViewById(R.id.tipo);
            fecha = view.findViewById(R.id.fecha);
        }
    }
}
