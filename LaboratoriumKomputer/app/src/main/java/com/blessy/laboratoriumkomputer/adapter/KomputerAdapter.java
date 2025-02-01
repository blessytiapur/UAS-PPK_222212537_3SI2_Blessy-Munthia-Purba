package com.blessy.laboratoriumkomputer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.blessy.laboratoriumkomputer.R;
import com.blessy.laboratoriumkomputer.model.Komputer;
import com.blessy.laboratoriumkomputer.activity.EditKomputerActivity;

import java.util.List;

public class KomputerAdapter extends RecyclerView.Adapter<KomputerAdapter.ViewHolder> {
    private List<Komputer> komputerList;
    private Context context;

    // Constructor untuk menerima context
    public KomputerAdapter(Context context) {
        this.context = context;
    }

    // Setter untuk list data
    public void setKomputerList(List<Komputer> komputerList) {
        this.komputerList = komputerList;
        notifyDataSetChanged(); // Notifikasi perubahan data
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_komputer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Komputer komputer = komputerList.get(position);
        holder.ruangKomputer.setText(komputer.getRuangKomputer());
        holder.ipAddress.setText(komputer.getIpAddress());

        // Listener untuk tombol edit
        holder.editKomputerButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditKomputerActivity.class);
            intent.putExtra("id", komputer.getId()); // Kirim ID komputer
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return (komputerList != null) ? komputerList.size() : 0;
    }

    // ViewHolder untuk menghubungkan layout item dengan adapter
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ruangKomputer, ipAddress;
        ImageView editKomputerButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ruangKomputer = itemView.findViewById(R.id.ruangKomputer);
            ipAddress = itemView.findViewById(R.id.ipAddress);
            editKomputerButton = itemView.findViewById(R.id.edit_komputer_button); // Pastikan ID sesuai XML
        }
    }
}
