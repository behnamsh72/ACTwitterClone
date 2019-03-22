package com.example.behnam.ac_twitterclone;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.SignUpCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment implements View.OnClickListener {

    public static SignUpFragment newInstance() {

        Bundle args = new Bundle();

        SignUpFragment fragment = new SignUpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private SignUpFragmnetCallback signUpFragmnetCallback;
    private EditText edtEmail;
    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnSignUp;
    private Button btnLogin;


    public interface SignUpFragmnetCallback {
         void switchSignUpToLogin();
    }
    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_signup, container, false);
        edtEmail=view.findViewById(R.id.edtEmailSignUp);
        edtUsername=view.findViewById(R.id.edtUsernameSignUp);
        edtPassword=view.findViewById(R.id.edtPasswordSignUp);
        btnLogin=view.findViewById(R.id.btnGoToLogin);
        btnSignUp=view.findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(SignUpFragment.this);
        btnLogin.setOnClickListener(SignUpFragment.this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof SignUpFragmnetCallback)
            signUpFragmnetCallback = (SignUpFragmnetCallback) context;
        else
            throw new RuntimeException();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        signUpFragmnetCallback =null;
    }

    @Override
    public void onClick(View button) {
        switch (button.getId()){
            case R.id.btnSignUp:
                break;
            case R.id.btnGoToLogin:
                signUpFragmnetCallback.switchSignUpToLogin();
        }

    }
}
