package com.example.surveyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.surveyapplication.dao.UsersDAO;
import com.example.surveyapplication.databinding.ActivityLoginBinding;
import com.example.surveyapplication.models.Users;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityLoginBinding loginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());

        loginBinding.etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (loginBinding.layoutPhoneNumber.isErrorEnabled())
                    loginBinding.layoutPhoneNumber.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().startsWith("03")) {
                    loginBinding.layoutPhoneNumber.setErrorEnabled(true);
                    loginBinding.layoutPhoneNumber.setError("Enter Valid Phone Number (03441234567)");
                    loginBinding.layoutPhoneNumber.requestFocus();
                } else if (s.length() >= 2 && s.toString().startsWith("03") && s.length() < 11) {
                    loginBinding.layoutPhoneNumber.setErrorEnabled(true);
                    loginBinding.layoutPhoneNumber.setError("Phone Number must be 11 digits long");
                    loginBinding.layoutPhoneNumber.requestFocus();
                }
            }
        });

        loginBinding.etPin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (loginBinding.layoutPin.isErrorEnabled())
                    loginBinding.layoutPin.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 4) {
                    loginBinding.layoutPin.setErrorEnabled(true);
                    loginBinding.layoutPin.setError("Pin Must be 4 digits long");
                    loginBinding.layoutPin.requestFocus();
                }
            }
        });
    }

    private void loginUser(String phone_number, int pin) {
        AppDatabase database = AppDatabase.getAppDatabase(this);
        UsersDAO usersDAO = database.getUsersDAO();
        if (usersDAO.getAllCount() > 0) {
            if (usersDAO.checkAlreadyExists(phone_number) > 0) {
                Users user = usersDAO.getUserDetails(phone_number);
                if (user.getPhone_number().equals(phone_number) && user.getPin() == pin) {
                    if (user.getDistrict_id() == 0 && user.getProvince_id() == 0 && user.getTehsils_id() == 0) {
                        Intent intent = new Intent(this, SurveyLocalitySelectionActivity.class);
                        intent.putExtra("id", user.getId());
                        intent.putExtra("phone", user.getPhone_number());
                        startActivity(intent);
                    } else {
                        //skipping the locality selection screen...
                        Toast.makeText(this, "Skip",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "Invalid credentials.",
                            Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Invalid phone number.",
                        Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "No users found.",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.btn_login) {
            String phone_number = "";
            String pin = "";

            if (loginBinding.etPhoneNumber.getText() != null)
                phone_number = loginBinding.etPhoneNumber.getText().toString().trim();
            if (loginBinding.etPin.getText() != null)
                pin = loginBinding.etPin.getText().toString().trim();

            if (phone_number.isEmpty()) {
                loginBinding.layoutPhoneNumber.setErrorEnabled(true);
                loginBinding.layoutPhoneNumber.setError("Enter Phone Number");
                loginBinding.layoutPhoneNumber.requestFocus();
            } else if (!phone_number.startsWith("03")) {
                loginBinding.layoutPhoneNumber.setErrorEnabled(true);
                loginBinding.layoutPhoneNumber.setError("Enter Valid Phone Number (03441234567)");
                loginBinding.layoutPhoneNumber.requestFocus();
            } else if (phone_number.length() < 11) {
                loginBinding.layoutPhoneNumber.setErrorEnabled(true);
                loginBinding.layoutPhoneNumber.setError("Phone Number must be 11 digits long");
                loginBinding.layoutPhoneNumber.requestFocus();
            } else if (pin.isEmpty()) {
                loginBinding.layoutPin.setErrorEnabled(true);
                loginBinding.layoutPin.setError("Enter Pin");
                loginBinding.layoutPin.requestFocus();
            } else if (pin.length() < 4) {
                loginBinding.layoutPin.setErrorEnabled(true);
                loginBinding.layoutPin.setError("Pin Must be 4 digits long");
                loginBinding.layoutPin.requestFocus();
            } else {

                try {
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                loginUser(
                        phone_number,
                        Integer.parseInt(pin)
                );
            }
        } else if (id == R.id.btn_signup) {
            loginBinding.etPhoneNumber.setText("");
            loginBinding.etPin.setText("");
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
        }
    }
}