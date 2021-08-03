package com.example.surveyapplication.ui.residents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.surveyapplication.BR;
import com.example.surveyapplication.R;
import com.example.surveyapplication.databinding.ItemResidentsBinding;
import com.example.surveyapplication.models.ResidentialInfo;

import java.util.ArrayList;
import java.util.List;

public class AdapterResidentsList extends RecyclerView.Adapter<AdapterResidentsList.ViewHolder> implements Filterable {

    List<ResidentialInfo> list;
    List<ResidentialInfo> searchList;
    Context context;
    NavController navController;

    public AdapterResidentsList(List<ResidentialInfo> list, Context context, NavController navController) {
        this.list = list;
        this.context = context;
        this.navController = navController;
        searchList = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemResidentsBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_residents, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResidentialInfo item = list.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ItemResidentsBinding binding;

        public ViewHolder(ItemResidentsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Object obj) {
            binding.setVariable(BR.model, obj);
            binding.executePendingBindings();
        }
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    private final Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ResidentialInfo> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(searchList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ResidentialInfo item : searchList) {
                    if (item.getName_of_person().toLowerCase().trim().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
