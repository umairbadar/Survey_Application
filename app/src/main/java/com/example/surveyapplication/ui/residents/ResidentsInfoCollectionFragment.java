package com.example.surveyapplication.ui.residents;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.surveyapplication.AppDatabase;
import com.example.surveyapplication.R;
import com.example.surveyapplication.dao.ResidentialInfoDAO;
import com.example.surveyapplication.databinding.FragmentResidentsInfoCollectionBinding;
import com.example.surveyapplication.models.ResidentialInfo;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.content.Context.MODE_PRIVATE;

public class ResidentsInfoCollectionFragment extends Fragment implements View.OnClickListener {

    private FragmentResidentsInfoCollectionBinding binding;

    private List<String> gender;
    private List<Integer> genderID;
    private int selectedGenderID;

    private List<String> maritalStatus;
    private List<Integer> maritalStatusID;
    private int selectedMaritalStatusID;

    private List<String> education;
    private List<Integer> educationID;
    private int selectedEducationID;

    private List<String> occupation;
    private List<Integer> occupationID;
    private int selectedOccupationID;

    private List<String> pregnant;
    private List<Integer> pregnantID;
    private int selectedPregnantID = 0;

    private Calendar calendar;
    private DatePickerDialog.OnDateSetListener dateOfBirth;

    private String phone;

    private NavController navController;

    private int province_id;
    private int district_id;
    private int tehsil_id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("data", MODE_PRIVATE);
        phone = sharedPreferences.getString("phone", "");

        if (getArguments() != null) {
            province_id = getArguments().getInt("province_id");
            district_id = getArguments().getInt("district_id");
            tehsil_id = getArguments().getInt("tehsil_id");
        }

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.nav_residents_list, true).build();
                navController.navigate(R.id.action_nav_residents_info_collection_to_nav_residents_list, null, navOptions);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_residents_info_collection, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        calendar = Calendar.getInstance();

        maritalStatusID = new ArrayList<>();
        maritalStatus = new ArrayList<>();
        binding.spnMaritalStatus.setOnTouchListener((view1, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    binding.layoutMaritalStatus.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.gray_border_bg));
                    try {
                        InputMethodManager inputManager = (InputMethodManager) requireActivity().getSystemService(INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(requireActivity().getWindow().getDecorView().getRootView().getWindowToken(), 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    view1.performClick();
                    break;
                default:
                    break;
            }
            return true;
        });
        binding.spnMaritalStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedMaritalStatusID = maritalStatusID.get(i);
                binding.layoutPregnant.setVisibility((selectedGenderID == 2 && (selectedMaritalStatusID == 2 || selectedMaritalStatusID == 3 || selectedMaritalStatusID == 4)) ? View.VISIBLE : View.GONE);
                binding.layoutChildren.setVisibility((selectedMaritalStatusID != 1 && selectedMaritalStatusID != 0) ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        getMaritalStatus(0);

        genderID = new ArrayList<>();
        gender = new ArrayList<>();
        binding.spnGender.setOnTouchListener((view1, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    binding.layoutGender.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.gray_border_bg));
                    try {
                        InputMethodManager inputManager = (InputMethodManager) requireActivity().getSystemService(INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(requireActivity().getWindow().getDecorView().getRootView().getWindowToken(), 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    view1.performClick();
                    break;
                default:
                    break;
            }
            return true;
        });
        binding.spnGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedGenderID = genderID.get(i);
                getMaritalStatus(selectedGenderID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        getGenders();

        educationID = new ArrayList<>();
        education = new ArrayList<>();
        binding.spnEducation.setOnTouchListener((view1, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    binding.layoutEducation.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.gray_border_bg));
                    try {
                        InputMethodManager inputManager = (InputMethodManager) requireActivity().getSystemService(INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(requireActivity().getWindow().getDecorView().getRootView().getWindowToken(), 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    view1.performClick();
                    break;
                default:
                    break;
            }
            return true;
        });
        binding.spnEducation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedEducationID = educationID.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        getEducations();

        occupationID = new ArrayList<>();
        occupation = new ArrayList<>();
        binding.spnOccupation.setOnTouchListener((view1, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    binding.layoutOccupation.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.gray_border_bg));
                    try {
                        InputMethodManager inputManager = (InputMethodManager) requireActivity().getSystemService(INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(requireActivity().getWindow().getDecorView().getRootView().getWindowToken(), 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    view1.performClick();
                    break;
                default:
                    break;
            }
            return true;
        });
        binding.spnOccupation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedOccupationID = occupationID.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        getOccupations();

        pregnantID = new ArrayList<>();
        pregnant = new ArrayList<>();
        binding.spnPregnant.setOnTouchListener((view1, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    binding.layoutPregnant.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.gray_border_bg));
                    try {
                        InputMethodManager inputManager = (InputMethodManager) requireActivity().getSystemService(INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(requireActivity().getWindow().getDecorView().getRootView().getWindowToken(), 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    view1.performClick();
                    break;
                default:
                    break;
            }
            return true;
        });
        binding.spnPregnant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPregnantID = pregnantID.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        getPregnantStatus();

        binding.btnSelectDate.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);

        binding.etSerialNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.layoutSerialNo.isErrorEnabled())
                    binding.layoutSerialNo.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.etNameOfPerson.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.layoutNameOfPerson.isErrorEnabled())
                    binding.layoutNameOfPerson.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.etFatherHusbandName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.layoutFatherHusbandName.isErrorEnabled())
                    binding.layoutFatherHusbandName.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.etMotherName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.layoutMotherName.isErrorEnabled())
                    binding.layoutMotherName.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.etResidentialAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.layoutResidentialAddress.isErrorEnabled())
                    binding.layoutResidentialAddress.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.etMobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.layoutMobileNumber.isErrorEnabled())
                    binding.layoutMobileNumber.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.etAgeInYears.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.layoutAgeInYears.isErrorEnabled())
                    binding.layoutAgeInYears.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.etChildren.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.layoutChildren.isErrorEnabled())
                    binding.layoutChildren.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void getGenders() {

        if (genderID.size() > 0) {
            genderID.clear();
        }

        if (gender.size() > 0) {
            gender.clear();
        }

        genderID.add(0);
        gender.add("What is your gender?");

        genderID.add(1);
        gender.add("Male");

        genderID.add(2);
        gender.add("Female");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(requireContext(),
                R.layout.item_spinner, gender) {
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
        binding.spnGender.setAdapter(dataAdapter);
    }

    private void getMaritalStatus(int selectedGenderID) {

        if (maritalStatusID.size() > 0) {
            maritalStatusID.clear();
        }

        if (maritalStatus.size() > 0) {
            maritalStatus.clear();
        }

        maritalStatusID.add(0);
        maritalStatus.add("What is your marital status?");

        maritalStatusID.add(1);
        maritalStatus.add("Unmarried");

        maritalStatusID.add(2);
        maritalStatus.add("Married");

        maritalStatusID.add(3);
        maritalStatus.add("Divorce");

        if (selectedGenderID == 0 || selectedGenderID == 2) {
            maritalStatusID.add(4);
            maritalStatus.add("Widow");
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(requireContext(),
                R.layout.item_spinner, maritalStatus) {
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
        binding.spnMaritalStatus.setAdapter(dataAdapter);
    }

    private void getEducations() {

        if (educationID.size() > 0) {
            educationID.clear();
        }

        if (education.size() > 0) {
            education.clear();
        }

        educationID.add(0);
        education.add("What is your education?");

        educationID.add(1);
        education.add("No Education");

        educationID.add(2);
        education.add("Primary School");

        educationID.add(3);
        education.add("Secondary School");

        educationID.add(4);
        education.add("Matriculation/O Levels");

        educationID.add(5);
        education.add("Intermediate/A-Levels");

        educationID.add(6);
        education.add("Bachelor's Degree");

        educationID.add(7);
        education.add("Master's Degree");

        educationID.add(8);
        education.add("Ph.D. or higher");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(requireContext(),
                R.layout.item_spinner, education) {
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
        binding.spnEducation.setAdapter(dataAdapter);
    }

    private void getOccupations() {

        if (occupationID.size() > 0) {
            occupationID.clear();
        }

        if (occupation.size() > 0) {
            occupation.clear();
        }

        occupationID.add(0);
        occupation.add("What is your occupation?");

        occupationID.add(1);
        occupation.add("Student");

        occupationID.add(2);
        occupation.add("Housewife");

        occupationID.add(3);
        occupation.add("Trader");

        occupationID.add(4);
        occupation.add("Business");

        occupationID.add(5);
        occupation.add("Self-employed");

        occupationID.add(6);
        occupation.add("Formal employee");

        occupationID.add(7);
        occupation.add("NGO employee");

        occupationID.add(8);
        occupation.add("Seasonal worker");

        occupationID.add(9);
        occupation.add("Unemployed");

        occupationID.add(10);
        occupation.add("Retired");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(requireContext(),
                R.layout.item_spinner, occupation) {
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
        binding.spnOccupation.setAdapter(dataAdapter);
    }

    private void getPregnantStatus() {

        if (pregnantID.size() > 0) {
            pregnantID.clear();
        }

        if (pregnant.size() > 0) {
            pregnant.clear();
        }

        pregnantID.add(0);
        pregnant.add("Are you currently Pregnant?");

        pregnantID.add(1);
        pregnant.add("Pregnant");

        pregnantID.add(2);
        pregnant.add("Not Pregnant");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(requireContext(),
                R.layout.item_spinner, pregnant) {
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
        binding.spnPregnant.setAdapter(dataAdapter);
    }

    private void showDateOfBirthPicker() {
        dateOfBirth = (view, year, monthOfYear, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd/MM/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
            binding.etDateOfBirth.setText(sdf.format(calendar.getTime()));
            binding.etDateOfBirth.setTextColor(Color.BLACK);
        };
    }

    public void showDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(requireContext()).create();
        alertDialog.setMessage("Residential Information Inserted.\nDo you want to add more?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                (dialog, which) -> {
                    if (binding.etSerialNo.getText().length() > 0)
                        binding.etSerialNo.setText("");

                    if (binding.etNameOfPerson.getText().length() > 0)
                        binding.etNameOfPerson.setText("");

                    if (binding.etFatherHusbandName.getText().length() > 0)
                        binding.etFatherHusbandName.setText("");

                    if (binding.etMotherName.getText().length() > 0)
                        binding.etMotherName.setText("");

                    if (binding.etResidentialAddress.getText().length() > 0)
                        binding.etResidentialAddress.setText("");

                    if (binding.etMobileNumber.getText().length() > 0)
                        binding.etMobileNumber.setText("");

                    if (binding.etDateOfBirth.getText().length() > 0)
                        binding.etDateOfBirth.setText("");

                    if (binding.etAgeInYears.getText().length() > 0)
                        binding.etAgeInYears.setText("");

                    if (binding.etChildren.getText().length() > 0)
                        binding.etChildren.setText("");

                    binding.spnGender.setSelection(0);
                    binding.spnMaritalStatus.setSelection(0);
                    binding.spnEducation.setSelection(0);
                    binding.spnOccupation.setSelection(0);
                    binding.spnPregnant.setSelection(0);
                    binding.layoutPregnant.setVisibility(View.GONE);

                    selectedGenderID = 0;
                    selectedMaritalStatusID = 0;
                    selectedEducationID = 0;
                    selectedOccupationID = 0;
                    selectedPregnantID = 0;

                    binding.etSerialNo.requestFocus();

                    dialog.dismiss();
                });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                (dialog, which) -> {
                    NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.nav_residents_list, true).build();
                    navController.navigate(R.id.action_nav_residents_info_collection_to_nav_residents_list, null, navOptions);
                    dialog.dismiss();
                });
        alertDialog.show();
    }

    private void storeResidentialData(String serial_no, String name_of_person, String father_husband_name, String mother_name, String residential_address, String mobile_number, String date_of_birth, int age, int gender_id, int marital_status_id, int education_id, int occupation_id, int children, int pregnant_status_id) {

        AppDatabase database = AppDatabase.getAppDatabase(requireContext());
        ResidentialInfoDAO residentialInfoDAO = database.getResidentialInfoDAO();

        ResidentialInfo residentialInfo = new ResidentialInfo();
        residentialInfo.setSerial_no(serial_no);
        residentialInfo.setName_of_person(name_of_person);
        residentialInfo.setFather_husband_name(father_husband_name);
        residentialInfo.setMother_name(mother_name);
        residentialInfo.setResidential_address(residential_address);
        residentialInfo.setMobile_number(mobile_number);
        residentialInfo.setDate_of_birth(date_of_birth);
        residentialInfo.setAge(age);
        residentialInfo.setGender_id(gender_id);
        residentialInfo.setMarital_status_id(marital_status_id);
        residentialInfo.setEducation_id(education_id);
        residentialInfo.setOccupation_id(occupation_id);
        residentialInfo.setChildren(children);
        residentialInfo.setPregnant_status_id(pregnant_status_id);
        residentialInfo.setProvince_id(province_id);
        residentialInfo.setDistrict_id(district_id);
        residentialInfo.setTehsil_id(tehsil_id);
        residentialInfoDAO.insert(residentialInfo);

        showDialog();

        if (database.isOpen())
            database.close();
    }

    private void validations() {

        String serial_no = binding.etSerialNo.getText().toString().trim();
        String name_of_person = binding.etNameOfPerson.getText().toString().trim();
        String father_husband_name = binding.etFatherHusbandName.getText().toString().trim();
        String mother_name = binding.etMotherName.getText().toString().trim();
        String residential_address = binding.etResidentialAddress.getText().toString().trim();
        String mobile_number = binding.etMobileNumber.getText().toString().trim();
        String date_of_birth = binding.etDateOfBirth.getText().toString().trim();
        String age_in_year = binding.etAgeInYears.getText().toString().trim();
        String children = binding.etChildren.getText().toString().trim();

        if (serial_no.isEmpty()) {
            binding.layoutSerialNo.setErrorEnabled(true);
            binding.layoutSerialNo.setError("Enter Serial No");
            binding.layoutSerialNo.requestFocus();
        } else if (name_of_person.isEmpty()) {
            binding.layoutNameOfPerson.setErrorEnabled(true);
            binding.layoutNameOfPerson.setError("Enter Name of Person");
            binding.layoutNameOfPerson.requestFocus();
        } else if (father_husband_name.isEmpty()) {
            binding.layoutFatherHusbandName.setErrorEnabled(true);
            binding.layoutFatherHusbandName.setError("Enter Father/Husband Name");
            binding.layoutFatherHusbandName.requestFocus();
        } else if (mother_name.isEmpty()) {
            binding.layoutMotherName.setErrorEnabled(true);
            binding.layoutMotherName.setError("Enter Mother Name");
            binding.layoutMotherName.requestFocus();
        } else if (residential_address.isEmpty()) {
            binding.layoutResidentialAddress.setErrorEnabled(true);
            binding.layoutResidentialAddress.setError("Enter Residential Address");
            binding.layoutResidentialAddress.requestFocus();
        } else if (mobile_number.isEmpty()) {
            binding.layoutMobileNumber.setErrorEnabled(true);
            binding.layoutMobileNumber.setError("Enter Mobile Number");
            binding.layoutMobileNumber.requestFocus();
        } else if (mobile_number.length() < 11 || !mobile_number.startsWith("03")) {
            binding.layoutMobileNumber.setErrorEnabled(true);
            binding.layoutMobileNumber.setError("Enter Valid Mobile Number");
            binding.layoutMobileNumber.requestFocus();
        } else if (date_of_birth.isEmpty()) {
            binding.layoutDateOfBirth.setErrorEnabled(true);
            binding.layoutDateOfBirth.setError("Select Date of Birth");
            binding.layoutDateOfBirth.requestFocus();
        } else if (age_in_year.isEmpty()) {
            binding.layoutAgeInYears.setErrorEnabled(true);
            binding.layoutAgeInYears.setError("Enter Age in Years");
            binding.layoutAgeInYears.requestFocus();
        } else if (binding.spnGender.getSelectedItemPosition() == 0) {
            binding.layoutGender.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.red_border_bg));
        } else if (binding.spnMaritalStatus.getSelectedItemPosition() == 0) {
            binding.layoutMaritalStatus.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.red_border_bg));
        } else if (binding.spnEducation.getSelectedItemPosition() == 0) {
            binding.layoutEducation.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.red_border_bg));
        } else if (binding.spnOccupation.getSelectedItemPosition() == 0) {
            binding.layoutOccupation.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.red_border_bg));
        } else if (binding.layoutChildren.getVisibility() == View.VISIBLE && children.isEmpty()) {
            binding.layoutChildren.setErrorEnabled(true);
            binding.layoutChildren.setError("Enter no. of Children");
            binding.layoutChildren.requestFocus();
        } else if (binding.layoutPregnant.getVisibility() == View.VISIBLE && binding.spnPregnant.getSelectedItemPosition() == 0) {
            binding.layoutPregnant.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.red_border_bg));
        } else {
            storeResidentialData(
                    serial_no,
                    name_of_person,
                    father_husband_name,
                    mother_name,
                    residential_address,
                    mobile_number,
                    date_of_birth,
                    Integer.parseInt(age_in_year),
                    selectedGenderID,
                    selectedMaritalStatusID,
                    selectedEducationID,
                    selectedOccupationID,
                    children.isEmpty() ? 0 : Integer.parseInt(children),
                    selectedPregnantID
            );
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btn_select_date) {

            try {
                InputMethodManager inputManager = (InputMethodManager) requireActivity().getSystemService(INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(requireActivity().getWindow().getDecorView().getRootView().getWindowToken(), 0);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (binding.etDateOfBirth.getText().length() > 0) {
                binding.etDateOfBirth.setText("");
            }

            if (binding.layoutDateOfBirth.isErrorEnabled())
                binding.layoutDateOfBirth.setErrorEnabled(false);

            showDateOfBirthPicker();
            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), dateOfBirth, calendar
                    .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        } else if (id == R.id.btn_submit) {
            validations();
        }
    }
}