package com.example.surveyapplication.ui.summary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.surveyapplication.AppDatabase;
import com.example.surveyapplication.R;
import com.example.surveyapplication.dao.ResidentialInfoDAO;
import com.example.surveyapplication.databinding.FragmentSummaryBinding;
import com.example.surveyapplication.models.MaritalStatusCount;
import com.example.surveyapplication.models.Summary;

import java.util.List;

public class SummaryFragment extends Fragment {

    private FragmentSummaryBinding binding;

    private AppDatabase database;
    private MaritalStatusCount maritalStatusCount;
    private ResidentialInfoDAO residentialInfoDAO;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = AppDatabase.getAppDatabase(requireContext());
        residentialInfoDAO = database.getResidentialInfoDAO();
        maritalStatusCount = residentialInfoDAO.getMartialStatusCount();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_summary, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (residentialInfoDAO.getResidentsCount() == 0) {
            maritalStatusCount.setMarried_count(0);
            maritalStatusCount.setUnmarried_count(0);
            maritalStatusCount.setDivorce_count(0);
            maritalStatusCount.setWidow_count(0);
        }
        binding.setMaritalStatusCount(maritalStatusCount);

        if (residentialInfoDAO.getResidentsCount() > 0) {
            binding.summaryList.setLayoutManager(new LinearLayoutManager(requireContext()));
            List<Summary> list = residentialInfoDAO.getSummary();
            AdapterSummary adapter = new AdapterSummary(list, requireContext());
            binding.summaryList.setAdapter(adapter);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
            binding.summaryList.addItemDecoration(dividerItemDecoration);
        } else {
            Toast.makeText(requireContext(), "No summary details found!",
                    Toast.LENGTH_LONG).show();
        }

        if (database.isOpen())
            database.close();
    }
}