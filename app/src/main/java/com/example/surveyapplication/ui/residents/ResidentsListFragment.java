package com.example.surveyapplication.ui.residents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.surveyapplication.AppDatabase;
import com.example.surveyapplication.R;
import com.example.surveyapplication.dao.ResidentialInfoDAO;
import com.example.surveyapplication.databinding.FragmentResidentsListBinding;
import com.example.surveyapplication.models.ResidentialInfo;

import java.util.List;

public class ResidentsListFragment extends Fragment {

    private FragmentResidentsListBinding binding;
    private NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                showCloseDialog();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_residents_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        AppDatabase database = AppDatabase.getAppDatabase(requireContext());

        ResidentialInfoDAO residentialInfoDAO = database.getResidentialInfoDAO();

        if (residentialInfoDAO.getResidentsCount() > 0) {
            binding.residentsList.setLayoutManager(new LinearLayoutManager(requireContext()));
            List<ResidentialInfo> list = residentialInfoDAO.getAllResidents();
            AdapterResidentsList adapter = new AdapterResidentsList(list, requireContext(), navController);
            binding.residentsList.setAdapter(adapter);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
            binding.residentsList.addItemDecoration(dividerItemDecoration);

            binding.searchView.setInputType(EditorInfo.TYPE_CLASS_TEXT);
            binding.searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
            binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    newText = newText.replaceFirst("^0+(?!$)", "");
                    adapter.getFilter().filter(newText);
                    return false;
                }
            });

            if (database.isOpen())
                database.close();
        } else {
            Toast.makeText(requireContext(), "No resident found!",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void showCloseDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(requireContext()).create();
        alertDialog.setMessage("Are you sure you want to close app?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                (dialog, which) -> {

                    requireActivity().finish();
                    dialog.dismiss();
                });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }
}