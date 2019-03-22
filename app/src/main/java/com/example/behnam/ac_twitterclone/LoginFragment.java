package com.example.behnam.ac_twitterclone;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public LoginFragment() {
        // Required empty public constructor
    }


    private LoginCallBack loginCallBack;
    private Button btnLogin;
    private Button btnSignup;



    public interface LoginCallBack{
        void switchLoginToSignUp();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_login, container, false);

        btnLogin=view.findViewById(R.id.btnLogin);
        btnSignup=view.findViewById(R.id.btnGoToSignUp);

        btnLogin.setOnClickListener(LoginFragment.this);
        btnSignup.setOnClickListener(LoginFragment.this);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof LoginCallBack)
            loginCallBack= (LoginCallBack) context;
        else
            throw new RuntimeException();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        loginCallBack=null;
    }

    @Override
    public void onClick(View button) {
        switch (button.getId()){
            case R.id.btnLogin:


                break;
            case R.id.btnGoToSignUp:
                loginCallBack.switchLoginToSignUp();
        }

    }
}
