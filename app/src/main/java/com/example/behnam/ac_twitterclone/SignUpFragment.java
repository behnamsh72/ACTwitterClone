package com.example.behnam.ac_twitterclone;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment implements View.OnClickListener {

    private SignUpFragmnetCallback signUpFragmnetCallback;
    private EditText edtEmail;
    private EditText edtUsername;
    private EditText edtPassword;
    private MaterialButton btnSignUp;
    private MaterialButton btnLogin;
    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance() {

        Bundle args = new Bundle();

        SignUpFragment fragment = new SignUpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(ParseUser.getCurrentUser()!=null){
            signUpFragmnetCallback.TransitionFromSignUp();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        signUpFragmnetCallback.changeTitleToSignUp();
        edtEmail = view.findViewById(R.id.edtEmailSignUp);
        edtUsername = view.findViewById(R.id.edtUsernameSignUp);
        edtPassword = view.findViewById(R.id.edtPasswordSignUp);
        btnLogin = view.findViewById(R.id.btnGoToLogin);
        btnSignUp = view.findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(SignUpFragment.this);
        btnLogin.setOnClickListener(SignUpFragment.this);

        edtPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    onClick(btnSignUp);
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SignUpFragmnetCallback)
            signUpFragmnetCallback = (SignUpFragmnetCallback) context;
        else
            throw new RuntimeException();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        signUpFragmnetCallback = null;
    }

    @Override
    public void onClick(View button) {
        switch (button.getId()) {
            case R.id.btnSignUp:
                if (edtEmail.getText().toString().equals("") || edtUsername.getText().toString().equals("") || edtPassword.getText().toString().equals("")) {
                    FancyToast.makeText(getContext(), "Email,Username,Password Shoudn't be empty", FancyToast.LENGTH_LONG, FancyToast.INFO, false).show();
                } else {
                    final ParseUser parseUser = new ParseUser();
                    parseUser.setEmail(edtEmail.getText().toString());
                    parseUser.setPassword(edtPassword.getText().toString());
                    parseUser.setUsername(edtUsername.getText().toString());
                    final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Signing Up " + edtUsername.getText().toString());
                    progressDialog.show();
                    parseUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(getActivity(), parseUser.get("username") + "is Signed Up", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                                signUpFragmnetCallback.TransitionFromSignUp();
                            } else {
                                FancyToast.makeText(getActivity(), e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
                }
                break;
            case R.id.btnGoToLogin:
                signUpFragmnetCallback.switchSignUpToLogin();
        }

    }

    public interface SignUpFragmnetCallback {
        void switchSignUpToLogin();

        void changeTitleToSignUp();
        void TransitionFromSignUp();
    }
}
