package com.example.behnam.ac_twitterclone;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;


/**
 * A simple {@link Fragment} subclass.
 */
public class TwitterUsers extends androidx.fragment.app.Fragment {
    public static TwitterUsers newInstance() {

        Bundle args = new Bundle();
        
        TwitterUsers fragment = new TwitterUsers();
        fragment.setArguments(args);
        return fragment;
    }

    public interface TwitterUsersCallback{
        void logout();
    }
    private TwitterUsersCallback twitterUsersCallback;
    public TwitterUsers() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_twitter_users, container, false);
        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.twitter_users_menu,menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutUserItem:
                twitterUsersCallback.logout();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof TwitterUsersCallback)
            twitterUsersCallback= (TwitterUsersCallback) getContext();
        else
            throw new RuntimeException();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        twitterUsersCallback=null;
    }
}
