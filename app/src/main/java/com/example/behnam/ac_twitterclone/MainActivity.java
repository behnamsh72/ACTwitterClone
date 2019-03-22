package com.example.behnam.ac_twitterclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.parse.ParseInstallation;

public  class MainActivity extends AppCompatActivity implements LoginFragment.LoginCallBack, SignUpFragment.SignUpFragmnetCallback {

    private final String loginFragmentTag = "LoginFragment";
    private final String signUpFragmentTag = "SignUpFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,LoginFragment.newInstance(), loginFragmentTag)
                .commit();


    }

    @Override
    public void switchLoginToSignUp() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, SignUpFragment.newInstance(), signUpFragmentTag)
                .commit();
    }

    @Override
    public void switchSignUpToLogin() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,LoginFragment.newInstance(), loginFragmentTag)
                .commit();
    }
}
