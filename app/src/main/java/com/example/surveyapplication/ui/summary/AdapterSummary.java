package com.example.surveyapplication.ui.summary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.example.surveyapplication.R;
import com.example.surveyapplication.databinding.ItemSummaryBinding;
import com.example.surveyapplication.models.Summary;

import java.util.List;

public class AdapterSummary extends RecyclerView.Adapter<AdapterSummary.ViewHolder> {

    List<Summary> list;
    Context context;

    public AdapterSummary(List<Summary> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSummaryBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_summary, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Summary item = list.get(position);
        if (item.getProvince_id() == 1)
            item.setProvince_name("Sindh");
        else if (item.getProvince_id() == 2)
            item.setProvince_name("Punjab");
        else if (item.getProvince_id() == 3)
            item.setProvince_name("Khyber Pakhtunkhwa");
        else if (item.getProvince_id() == 4)
            item.setProvince_name("Baluchistan");
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ItemSummaryBinding binding;

        public ViewHolder(ItemSummaryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Object obj) {
            binding.setVariable(BR.model, obj);
            binding.executePendingBindings();
        }
    }
}
