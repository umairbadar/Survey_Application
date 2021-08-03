package com.example.surveyapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.surveyapplication.dao.UsersDAO;
import com.example.surveyapplication.databinding.ActivitySignupBinding;
import com.example.surveyapplication.models.Users;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySignupBinding signupBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        signupBinding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(signupBinding.getRoot());

        signupBinding.etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (signupBinding.layoutName.isErrorEnabled())
                    signupBinding.layoutName.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        signupBinding.etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (signupBinding.layoutPhoneNumber.isErrorEnabled())
                    signupBinding.layoutPhoneNumber.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        signupBinding.etPin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (signupBinding.layoutPin.isErrorEnabled())
                    signupBinding.layoutPin.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        signupBinding.etConfirmPin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (signupBinding.layoutConfirmPin.isErrorEnabled())
                    signupBinding.layoutConfirmPin.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void registerUser(String name, String phone_number, int pin) {
        AppDatabase database = AppDatabase.getAppDatabase(this);
        UsersDAO usersDAO = database.getUsersDAO();
        if (usersDAO.checkAlreadyExists(phone_number) > 0){
            Toast.makeText(this, "Phone number already exists.",
                    Toast.LENGTH_LONG).show();
        } else {
            Users users = new Users();
            users.setName(name);
            users.setPhone_number(phone_number);
            users.setPin(pin);
            usersDAO.insert(users);

            Toast.makeText(this, "Account created. Login to continue",
                    Toast.LENGTH_LONG).show();

            if (database.isOpen())
                database.close();

            finish();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btn_signup) {
            String name = "";
            String phone_number = "";
            String pin = "";
            String confirm_pin = "";

            if (signupBinding.etName.getText() != null)
                name = signupBinding.etName.getText().toString().trim();

            if (signupBinding.etPhoneNumber.getText() != null)
                phone_number = signupBinding.etPhoneNumber.getText().toString().trim();

            if (signupBinding.etPin.getText() != null)
                pin = signupBinding.etPin.getText().toString().trim();

            if (signupBinding.etConfirmPin.getText() != null)
                confirm_pin = signupBinding.etConfirmPin.getText().toString().trim();

            if (name.isEmpty()) {
                signupBinding.layoutName.setErrorEnabled(true);
                signupBinding.layoutName.setError("Enter Name");
                signupBinding.layoutName.requestFocus();
            } else if (phone_number.isEmpty()) {
                signupBinding.layoutPhoneNumber.setErrorEnabled(true);
                signupBinding.layoutPhoneNumber.setError("Enter Phone Number");
                signupBinding.layoutPhoneNumber.requestFocus();
            } else if (!phone_number.startsWith("03")) {
                signupBinding.layoutPhoneNumber.setErrorEnabled(true);
                signupBinding.layoutPhoneNumber.setError("Enter Valid Phone Number (03441234567)");
                signupBinding.layoutPhoneNumber.requestFocus();
            } else if (phone_number.length() < 11) {
                signupBinding.layoutPhoneNumber.setErrorEnabled(true);
                signupBinding.layoutPhoneNumber.setError("Phone Number must be 11 digits long");
                signupBinding.layoutPhoneNumber.requestFocus();
            } else if (pin.isEmpty()) {
                signupBinding.layoutPin.setErrorEnabled(true);
                signupBinding.layoutPin.setError("Enter Pin");
                signupBinding.layoutPin.requestFocus();
            } else if (pin.length() < 4) {
                signupBinding.layoutPin.setErrorEnabled(true);
                signupBinding.layoutPin.setError("Pin Must be 4 digits long");
                signupBinding.layoutPin.requestFocus();
            } else if (confirm_pin.isEmpty()) {
                signupBinding.layoutConfirmPin.setErrorEnabled(true);
                signupBinding.layoutConfirmPin.setError("Enter Confirm Pin");
                signupBinding.layoutConfirmPin.requestFocus();
            } else if (confirm_pin.length() < 4) {
                signupBinding.layoutConfirmPin.setErrorEnabled(true);
                signupBinding.layoutConfirmPin.setError("Confirm Pin Must be 4 digits long");
                signupBinding.layoutConfirmPin.requestFocus();
            } else if (!pin.equals(confirm_pin)) {
                signupBinding.layoutConfirmPin.setErrorEnabled(true);
                signupBinding.layoutConfirmPin.setError("Pin & Confirm Pin must be same");
                signupBinding.layoutConfirmPin.requestFocus();
            } else {

                try {
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                registerUser(
                        name,
                        phone_number,
                        Integer.parseInt(pin)
                );
            }
        } else if (id == R.id.btn_login){
            finish();
        }
    }
}