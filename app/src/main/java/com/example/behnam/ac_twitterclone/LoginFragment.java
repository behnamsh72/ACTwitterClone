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
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private LoginCallBack loginCallBack;
    private MaterialButton btnLogin;
    private MaterialButton btnSignup;
    private EditText edtUsername;
    private EditText edtPassword;
    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(ParseUser.getCurrentUser()!=null){
            loginCallBack.transitionFromLogin();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        loginCallBack.changeTitleToLogin();

        btnLogin = view.findViewById(R.id.btnLogin);
        btnSignup = view.findViewById(R.id.btnGoToSignUp);
        edtUsername = view.findViewById(R.id.edtUserNameLogin);
        edtPassword = view.findViewById(R.id.edtPasswordLogin);
        edtPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN){
                    onClick(btnLogin);
                }
                return false;
            }
        });


        btnLogin.setOnClickListener(LoginFragment.this);
        btnSignup.setOnClickListener(LoginFragment.this);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginCallBack)
            loginCallBack = (LoginCallBack) context;
        else
            throw new RuntimeException();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        loginCallBack = null;
    }

    @Override
    public void onClick(View button) {
        switch (button.getId()) {
            case R.id.btnLogin:

                if(edtUsername.getText().toString().equals("") || edtPassword.getText().toString().equals("")){
                    FancyToast.makeText(getContext(), "Username,password shouldn't be empty", FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
                }else{
                    final ProgressDialog progressDialog=new ProgressDialog(getContext());
                    progressDialog.setMessage("Logging In "+ edtUsername.getText().toString());
                    progressDialog.show();
                    ParseUser.logInInBackground(edtUsername.getText().toString(), edtPassword.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if(user!=null && e==null){
                                FancyToast.makeText(getActivity(), user.getUsername() + " is Logged In", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                                loginCallBack.transitionFromLogin();
                            }else{
                                FancyToast.makeText(getContext(), e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
                }
                break;
            case R.id.btnGoToSignUp:
                loginCallBack.switchLoginToSignUp();

                break;
        }

    }

    public interface LoginCallBack {
        void switchLoginToSignUp();

        void changeTitleToLogin();
        void transitionFromLogin();
    }

}
