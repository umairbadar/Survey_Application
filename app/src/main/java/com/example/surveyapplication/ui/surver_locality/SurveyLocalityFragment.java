package com.example.surveyapplication.ui.surver_locality;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.surveyapplication.R;
import com.example.surveyapplication.databinding.FragmentSurveyLocalityBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SurveyLocalityFragment extends Fragment implements View.OnClickListener {

    private FragmentSurveyLocalityBinding binding;

    private List<String> provinces;
    private List<Integer> provinceID;
    private int selectedProvinceID;

    private List<String> district;
    private List<Integer> districtID;
    private int selectedDistrictID;

    private List<String> tehsils;
    private List<Integer> tehsilsID;
    private int selectedTehsilsID;

    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_survey_locality, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        provinceID = new ArrayList<>();
        provinces = new ArrayList<>();
        binding.spnProvince.setOnTouchListener((view1, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    binding.layoutProvince.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.gray_border_bg));
                    break;
                case MotionEvent.ACTION_UP:
                    view1.performClick();
                    break;
                default:
                    break;
            }
            return true;
        });
        binding.spnProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedProvinceID = provinceID.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        getProvinces();

        districtID = new ArrayList<>();
        district = new ArrayList<>();
        binding.spnDistrict.setOnTouchListener((view1, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    binding.layoutDistrict.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.gray_border_bg));
                    break;
                case MotionEvent.ACTION_UP:
                    view1.performClick();
                    break;
                default:
                    break;
            }
            return true;
        });
        binding.spnDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedDistrictID = districtID.get(i);
                if (i != 0) {
                    if (binding.layoutTehsils.getVisibility() != View.VISIBLE)
                        binding.layoutTehsils.setVisibility(View.VISIBLE);

                    getTehsils(i);

                } else {
                    if (binding.layoutTehsils.getVisibility() == View.VISIBLE)
                        binding.layoutTehsils.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        getDistrict();

        tehsilsID = new ArrayList<>();
        tehsils = new ArrayList<>();
        binding.spnTehsils.setOnTouchListener((view1, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    binding.layoutTehsils.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.gray_border_bg));
                    break;
                case MotionEvent.ACTION_UP:
                    view1.performClick();
                    break;
                default:
                    break;
            }
            return true;
        });
        binding.spnTehsils.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedTehsilsID = tehsilsID.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.btnNext.setOnClickListener(this);
    }

    private void getProvinces() {

        if (provinceID.size() > 0) {
            provinceID.clear();
        }

        if (provinces.size() > 0) {
            provinces.clear();
        }

        provinceID.add(0);
        provinces.add("Select Province");

        provinceID.add(1);
        provinces.add("Sindh");

        provinceID.add(2);
        provinces.add("Punjab");

        provinceID.add(3);
        provinces.add("Khyber Pakhtunkhwa");

        provinceID.add(4);
        provinces.add("Baluchistan");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(requireContext(),
                R.layout.item_spinner, provinces) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
                View view;
                if (position == 0) {
                    TextView textView = new TextView(getContext());
                    textView.setHeight(0);
                    textView.setVisibility(View.GONE);
                    view = textView;
                } else {
                    view = super.getDropDownView(position, null, parent);
                }
                return view;
            }
        };

        dataAdapter.setDropDownViewResource(R.layout.item_spinner);
        binding.spnProvince.setAdapter(dataAdapter);
    }

    private void getDistrict() {

        if (districtID.size() > 0) {
            districtID.clear();
        }

        if (district.size() > 0) {
            district.clear();
        }

        districtID.add(0);
        district.add("Select District");

        districtID.add(1);
        district.add("Karachi");

        districtID.add(2);
        district.add("Lahore");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(requireContext(),
                R.layout.item_spinner, district) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
                View view;
                if (position == 0) {
                    TextView textView = new TextView(getContext());
                    textView.setHeight(0);
                    textView.setVisibility(View.GONE);
                    view = textView;
                } else {
                    view = super.getDropDownView(position, null, parent);
                }
                return view;
            }
        };

        dataAdapter.setDropDownViewResource(R.layout.item_spinner);
        binding.spnDistrict.setAdapter(dataAdapter);
    }

    private void getTehsils(int district_id) {

        if (tehsilsID.size() > 0) {
            tehsilsID.clear();
        }

        if (tehsils.size() > 0) {
            tehsils.clear();
        }

        tehsilsID.add(0);
        tehsils.add("Select Tehsils");

        if (district_id == 1) {
            //Tehsils of Karachi to be populated in Spinner...

            tehsilsID.add(11);
            tehsils.add("Haidri");

            tehsilsID.add(12);
            tehsils.add("Sakhi Hassan");

            tehsilsID.add(13);
            tehsils.add("Buffer Zone");

            tehsilsID.add(14);
            tehsils.add("Ayesha Manzil");

            tehsilsID.add(15);
            tehsils.add("Azizabad");

            tehsilsID.add(16);
            tehsils.add("Karimabad");

            tehsilsID.add(17);
            tehsils.add("Liaqatabad");

            tehsilsID.add(18);
            tehsils.add("Paposh Nagar");

            tehsilsID.add(19);
            tehsils.add("Nazimabad");
        } else {
            //Tehsils of Lahore to be populated in Spinner...

            tehsilsID.add(21);
            tehsils.add("Ravi");

            tehsilsID.add(22);
            tehsils.add("Shalamar");

            tehsilsID.add(23);
            tehsils.add("Wahga");

            tehsilsID.add(24);
            tehsils.add("Aziz Bhatti");

            tehsilsID.add(25);
            tehsils.add("Data Gunj Buksh");

            tehsilsID.add(26);
            tehsils.add("Gulberg");

            tehsilsID.add(27);
            tehsils.add("Samanabad");

            tehsilsID.add(28);
            tehsils.add("Iqbal");

            tehsilsID.add(29);
            tehsils.add("Nishtar");
        }


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(requireContext(),
                R.layout.item_spinner, tehsils) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
                View view;
                if (position == 0) {
                    TextView textView = new TextView(getContext());
                    textView.setHeight(0);
                    textView.setVisibility(View.GONE);
                    view = textView;
                } else {
                    view = super.getDropDownView(position, null, parent);
                }
                return view;
            }
        };

        dataAdapter.setDropDownViewResource(R.layout.item_spinner);
        binding.spnTehsils.setAdapter(dataAdapter);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_next) {

            if (selectedProvinceID == 0) {
                binding.layoutProvince.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.red_border_bg));
            } else if (selectedDistrictID == 0) {
                binding.layoutDistrict.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.red_border_bg));
            } else if (selectedTehsilsID == 0) {
                binding.layoutTehsils.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.red_border_bg));
            } else {
                Bundle args = new Bundle();
                args.putInt("province_id", selectedProvinceID);
                args.putInt("district_id", selectedDistrictID);
                args.putInt("tehsil_id", selectedTehsilsID);
                navController.navigate(R.id.action_nav_survey_locality_to_nav_residents_info_collection, args);
            }
        }
    }
}