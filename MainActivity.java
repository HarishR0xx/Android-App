package com.example.pms_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import android.content.Intent;
import android.content.IntentFilter;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    Button LoginButton, signupButton, confirm,addVehicleButton,searchVehicleButton,showSLotAvailabilityButton;
    DatePicker DOB;
    EditText editUser, editPassword, editFullName, editEmail, editContact, editConfirmPassword, editOTP;
    TextView ForgotPassword, AccountRegister, AccountLogin, resendOTP, contactNumber;
    String username, password, fullName, contact, email, confirmPassword, day, month, year,number;
    SmsManager smsManager;
    private IntentFilter receiveFilter;
    private MessageReceiver messageReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        displayLoginPage();
    }
    protected void displayLoginPage() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);
        editPassword = findViewById(R.id.editPassword);
        editUser = findViewById(R.id.editUser);
        LoginButton = findViewById(R.id.LoginButton);
        ForgotPassword = findViewById(R.id.ForgotPassword);
        AccountRegister = findViewById(R.id.AccountRegister);
        number = editUser.getText().toString();
        receiveFilter = new IntentFilter();
        receiveFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        messageReceiver = new MessageReceiver();
        registerReceiver(messageReceiver, receiveFilter);

        LoginButton.setOnClickListener(view -> {
            username = editUser.getText().toString().trim();
            password = editPassword.getText().toString().trim();
            if (TextUtils.isEmpty(username)) {
                editUser.setError("Please enter the username");
                editUser.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                editPassword.setError("Please enter the password");
                editPassword.requestFocus();
                return;
            }
        });
        ForgotPassword.setOnClickListener(view -> {
            username=editUser.getText().toString();
            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED)
            {
                MessageSent();
            } else
            {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS},100);
            }
            displayForgotPassword();
        });
        AccountRegister.setOnClickListener(view -> {
            displaySignUpPage();
        });
    }
    protected void displaySignUpPage() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.sign_up);
        signupButton = findViewById(R.id.signupButton);
        DOB = findViewById(R.id.DOB);
        AccountLogin = findViewById(R.id.AccountLogin);
        editFullName = findViewById(R.id.editFullName);
        editContact = findViewById(R.id.editContact);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editConfirmPassword = findViewById(R.id.editConfirmPassword);
        AccountLogin.setOnClickListener(view -> {
            displayLoginPage();
        });
        signupButton.setOnClickListener(view -> {
            fullName = editFullName.getText().toString().trim();
            contact = editContact.getText().toString().trim();
            email = editEmail.getText().toString().trim();
            password = editPassword.getText().toString().trim();
            confirmPassword = editConfirmPassword.getText().toString().trim();
            if (TextUtils.isEmpty(fullName)) {
                editFullName.setError("Please enter the name");
                editFullName.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(email)) {
                editEmail.setError("Please enter the email");
                editEmail.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(contact)) {
                editContact.setError("Please enter the contact number");
                editContact.requestFocus();
                return;
            }
            day = String.valueOf(DOB.getDayOfMonth());
            month = String.valueOf(DOB.getMonth());
            year = String.valueOf(DOB.getYear());
            if (TextUtils.isEmpty(password)) {
                editPassword.setError("Please enter the password");
                editPassword.requestFocus();
                return;
            }
            if (!password.equals(confirmPassword)) {
                editConfirmPassword.setError("Please enter the correct confirmation password");
                editConfirmPassword.requestFocus();
                return;
            }
            displayLoginPage();
        });
    }
    protected void displayForgotPassword() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.forgot_password);
        editOTP = findViewById(R.id.editOTP);
        confirm = findViewById(R.id.confirm);
        resendOTP = findViewById(R.id.resendOTP);
        contactNumber = findViewById(R.id.contactNumber);
        contactNumber.setText(String.valueOf(username));
        confirm.setOnClickListener(view -> {
            if (editOTP.getText().toString().equals("123456")) {
                    displayHomePage();
            } else {
                editOTP.setError("Wrong OTP");
                editOTP.requestFocus();
                return;
            }
        });
        resendOTP.setOnClickListener(view -> {
            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED)
            {
                MessageSent();
            } else
            {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS},100);
            }
        });
    }
    private void displayHomePage() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.home_page);
        addVehicleButton=findViewById(R.id.addVehicleButton);
        searchVehicleButton=findViewById(R.id.searchVehicleButton);
        showSLotAvailabilityButton=findViewById(R.id.showSlotAvailabilityButton);
        addVehicleButton.setOnClickListener(v -> {
            displayAddVehiclePage();
        });
        showSLotAvailabilityButton.setOnClickListener(v -> {
            displaySearchVehiclePage();
        });
        showSLotAvailabilityButton.setOnClickListener(v -> {
            displayShowSlotAvailability();
        });
    }
    private void displayShowSlotAvailability() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
    private void displaySearchVehiclePage() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
    private void displayAddVehiclePage() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
    private void MessageSent() {
        if(!username.equals(""))
        {
            smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(username, null,"The OTP is 123456", null, null);
            Toast.makeText(getApplicationContext(),"OTP sent",Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(getApplicationContext(),"fill the fields",Toast.LENGTH_SHORT).show();
        }
    }
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(messageReceiver);
    }
    class MessageReceiver extends BroadcastReceiver {
        Bundle bundle;
        Object[] pdus;
        SmsMessage[] messages;
        String address,fullMessage;
        @Override
        public void onReceive(Context context, Intent intent) {
            bundle = intent.getExtras();
            pdus = (Object[]) bundle.get("pdus"); // Retrieve SMS messages
            messages = new SmsMessage[pdus.length];
            for (int i = 0; i < messages.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
            }
            address = messages[0].getOriginatingAddress();
            fullMessage = "";
            for (SmsMessage message : messages) {
                fullMessage += message.getMessageBody();
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            MessageSent();
        } else {
            Toast.makeText(getApplicationContext(),"permission denied",Toast.LENGTH_LONG).show();
        }
    }
}