package com.example.behnam.ac_twitterclone;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;


/**
 * A simple {@link Fragment} subclass.
 */
public class TwitterUsers extends androidx.fragment.app.Fragment {
    private TwitterUsersCallback twitterUsersCallback;
    private ListView listView;
    private ArrayList<String> twitterUsers;
    private ArrayAdapter adapter;


    public TwitterUsers() {
        // Required empty public constructor
    }

    public static TwitterUsers newInstance() {

        Bundle args = new Bundle();

        TwitterUsers fragment = new TwitterUsers();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        FancyToast.makeText(getContext(), "Welcome " + ParseUser.getCurrentUser().getUsername(), Toast.LENGTH_LONG, FancyToast.INFO, false).show();
        twitterUsers = new ArrayList<>();
        Log.d("behnam1", "onCreate");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_twitter_users, container, false);
        listView = view.findViewById(R.id.listView);

        try {
            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
            query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if (objects.size() > 0 && e == null) {
                        for (ParseUser twitterUser : objects) {
                            twitterUsers.add(twitterUser.getUsername());
                        }
                        adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, twitterUsers);
                        listView.setAdapter(adapter);
                    }
                }
            });

        } catch (Exception e) {

        }

        Log.d("behnam1", "onCreateView");
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("behnam1", "onResume");
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.twitter_users_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
        Log.d("behnam1", "onAttach");

        if (context instanceof TwitterUsersCallback)
            twitterUsersCallback = (TwitterUsersCallback) getContext();
        else
            throw new RuntimeException();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        twitterUsersCallback = null;
    }

    public interface TwitterUsersCallback {
        void logout();
    }
}
