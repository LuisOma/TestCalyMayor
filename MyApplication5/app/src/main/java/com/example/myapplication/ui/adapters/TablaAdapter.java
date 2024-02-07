package com.example.myapplication.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.network.SanitAbastecimiento;

import java.util.List;

public class TablaAdapter extends RecyclerView.Adapter<TablaAdapter.TablaViewHolder> {
    private Context context;
    private List<SanitAbastecimiento> dataList;
    private OnItemSelectedListener listener;

    public interface OnItemSelectedListener {
        void onIconClick(int id);
        void onRadioSelectionChanged(int id, String selection);
    }

    public TablaAdapter(Context context, List<SanitAbastecimiento> dataList, OnItemSelectedListener listener) {
        this.context = context;
        this.dataList = dataList;
        this.listener = listener;
    }

    @Override
    public TablaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tabla_item, parent, false);
        return new TablaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TablaViewHolder holder, int position) {
        SanitAbastecimiento item = dataList.get(holder.getAdapterPosition());
        holder.tipoTextView.setText(item.getTipoAbastecimiento());
        String selected = item.getOpcion();

        if (item.getFoto() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(item.getFoto(), 0, item.getFoto().length);
            holder.iconoImageView.setImageBitmap(bitmap);
        } else {
            holder.iconoImageView.setImageResource(R.drawable.baseline_camera_enhance_24); // Assuming you have a default_icon in your drawable resources
        }

        holder.opcionesRadioGroup.setOnCheckedChangeListener(null);
        holder.opcionesRadioGroup.clearCheck();

        switch (selected) {
            case "Si":
                holder.opcionesRadioGroup.check(R.id.siRadioButton);
                break;
            case "No":
                holder.opcionesRadioGroup.check(R.id.noRadioButton);
                break;
            case "N/A":
                holder.opcionesRadioGroup.check(R.id.naRadioButton);
                break;
            default:
                break;
        }

        holder.opcionesRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String selection = "";
                if (checkedId == R.id.siRadioButton) {
                    selection = "Si";
                } else if (checkedId == R.id.noRadioButton) {
                    selection = "No";
                } else if (checkedId == R.id.naRadioButton) {
                    selection = "N/A";
                }
                if (listener != null) {
                    dataList.get(holder.getAdapterPosition()).setOpcion(selection);
                    listener.onRadioSelectionChanged(dataList.get(holder.getAdapterPosition()).getIdAbastecimiento(), selection);
                }
            }
        });

        holder.iconoImageView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onIconClick(dataList.get(holder.getAdapterPosition()).getIdAbastecimiento());
            }
        });
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void addItems(List<SanitAbastecimiento> list) {
        dataList.clear();
        dataList.addAll(list);
        notifyDataSetChanged();
    }

    public void updateImage(int id, byte[] image) {
        for (int i = 0; i < dataList.size(); i++) {
            SanitAbastecimiento item = dataList.get(i);
            if (item.getIdAbastecimiento() == id) {
                item.setFoto(image);
                notifyItemChanged(i);
                break;
            }
        }
    }


    public static class TablaViewHolder extends RecyclerView.ViewHolder {
        public TextView tipoTextView;
        public RadioGroup opcionesRadioGroup;
        public ImageView iconoImageView;

        public TablaViewHolder(View view) {
            super(view);
            tipoTextView = view.findViewById(R.id.tipoTextView);
            opcionesRadioGroup = view.findViewById(R.id.opcionesRadioGroup);
            iconoImageView = view.findViewById(R.id.iconoImageView);
        }
    }
}

