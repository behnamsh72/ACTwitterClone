package com.example.behnam.ac_twitterclone;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginCallBack, SignUpFragment.SignUpFragmnetCallback, TwitterUsers.TwitterUsersCallback {

    private final String LOG_IN_FRAGMENT_TAG = "LoginFragment";
    private final String SIGN_UP_FRAGMENT_TAG = "SignUpFragment";
    private final String TWITTER_USERS_FRAGMENT_TAG = "TwitterUsers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, LoginFragment.newInstance(), LOG_IN_FRAGMENT_TAG)
                .commit();


    }

    @Override
    public void switchLoginToSignUp() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, SignUpFragment.newInstance(), SIGN_UP_FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void changeTitleToLogin() {
        setTitle("Log In");
    }



    @Override
    public void switchSignUpToLogin() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, LoginFragment.newInstance(), LOG_IN_FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void changeTitleToSignUp() {
        setTitle("Sign Up");
    }

    @Override
    public void TransitionFromSignUp() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,TwitterUsers.newInstance(), TWITTER_USERS_FRAGMENT_TAG)
                .commit();
    }
    @Override
    public void transitionFromLogin() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,TwitterUsers.newInstance(), TWITTER_USERS_FRAGMENT_TAG)
                .commit();

    }

    public void rootTapped(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void logout() {
        ParseUser.logOutInBackground(new LogOutCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container,LoginFragment.newInstance(),LOG_IN_FRAGMENT_TAG)
                            .commit();
                }
            }
        });
    }
}
