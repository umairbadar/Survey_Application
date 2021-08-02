package com.example.surveyapplication;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.surveyapplication.dao.UsersDAO;
import com.example.surveyapplication.databinding.ActivitySurveyLocalitySelectionBinding;
import com.example.surveyapplication.models.Users;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SurveyLocalitySelectionActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySurveyLocalitySelectionBinding localitySelectionBinding;

    private List<String> provinces;
    private List<Integer> provinceID;
    private int selectedProvinceID;

    private List<String> district;
    private List<Integer> districtID;
    private int selectedDistrictID;

    private List<String> tehsils;
    private List<Integer> tehsilsID;
    private int selectedTehsilsID;

    private int user_id;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user_id = getIntent().getIntExtra("id", 0);
        phone = getIntent().getStringExtra("phone");

        localitySelectionBinding = ActivitySurveyLocalitySelectionBinding.inflate(getLayoutInflater());
        setContentView(localitySelectionBinding.getRoot());

        provinceID = new ArrayList<>();
        provinces = new ArrayList<>();
        localitySelectionBinding.spnProvince.setOnTouchListener((view1, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    localitySelectionBinding.layoutProvince.setBackground(ContextCompat.getDrawable(this, R.drawable.gray_border_bg));
                    break;
                case MotionEvent.ACTION_UP:
                    view1.performClick();
                    break;
                default:
                    break;
            }
            return true;
        });
        localitySelectionBinding.spnProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        localitySelectionBinding.spnDistrict.setOnTouchListener((view1, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    localitySelectionBinding.layoutDistrict.setBackground(ContextCompat.getDrawable(this, R.drawable.gray_border_bg));
                    break;
                case MotionEvent.ACTION_UP:
                    view1.performClick();
                    break;
                default:
                    break;
            }
            return true;
        });
        localitySelectionBinding.spnDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedDistrictID = districtID.get(i);
                if (i != 0) {
                    if (localitySelectionBinding.layoutTehsils.getVisibility() != View.VISIBLE)
                        localitySelectionBinding.layoutTehsils.setVisibility(View.VISIBLE);

                    getTehsils(i);

                } else {
                    if (localitySelectionBinding.layoutTehsils.getVisibility() == View.VISIBLE)
                        localitySelectionBinding.layoutTehsils.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        getDistrict();

        tehsilsID = new ArrayList<>();
        tehsils = new ArrayList<>();
        localitySelectionBinding.spnTehsils.setOnTouchListener((view1, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    localitySelectionBinding.layoutTehsils.setBackground(ContextCompat.getDrawable(this, R.drawable.gray_border_bg));
                    break;
                case MotionEvent.ACTION_UP:
                    view1.performClick();
                    break;
                default:
                    break;
            }
            return true;
        });
        localitySelectionBinding.spnTehsils.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedTehsilsID = tehsilsID.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
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
        localitySelectionBinding.spnProvince.setAdapter(dataAdapter);
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


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
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
        localitySelectionBinding.spnDistrict.setAdapter(dataAdapter);
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


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
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
        localitySelectionBinding.spnTehsils.setAdapter(dataAdapter);
    }

    private void updateUserDetails(int selectedProvinceID, int selectedDistrictID, int selectedTehsilsID) {
        AppDatabase database = AppDatabase.getAppDatabase(this);
        UsersDAO usersDAO = database.getUsersDAO();
        if (user_id != 0) {
            Users user = usersDAO.getUserDetails(phone);
            user.setId(user.getId());
            user.setName(user.getName());
            user.setPhone_number(user.getPhone_number());
            user.setPin(user.getPin());
            user.setProvince_id(selectedProvinceID);
            user.setDistrict_id(selectedDistrictID);
            user.setTehsils_id(selectedTehsilsID);
            usersDAO.update(user);
        } else {
            Toast.makeText(this, "user id is null",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_next) {

            if (selectedProvinceID == 0) {
                localitySelectionBinding.layoutProvince.setBackground(ContextCompat.getDrawable(this, R.drawable.red_border_bg));
            } else if (selectedDistrictID == 0) {
                localitySelectionBinding.layoutDistrict.setBackground(ContextCompat.getDrawable(this, R.drawable.red_border_bg));
            } else if (selectedTehsilsID == 0) {
                localitySelectionBinding.layoutTehsils.setBackground(ContextCompat.getDrawable(this, R.drawable.red_border_bg));
            } else {
                updateUserDetails(
                        selectedProvinceID,
                        selectedDistrictID,
                        selectedTehsilsID
                );
            }
        }
    }
}