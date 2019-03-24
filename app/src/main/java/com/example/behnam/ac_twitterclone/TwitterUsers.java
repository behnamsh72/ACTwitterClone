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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;


/**
 * A simple {@link Fragment} subclass.
 */
public class TwitterUsers extends androidx.fragment.app.Fragment implements AdapterView.OnItemClickListener {
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
        Log.d("behnam1", "onCreate");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_twitter_users, container, false);
        twitterUsers = new ArrayList<>();
        listView = view.findViewById(R.id.listView);
        adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_checked, twitterUsers);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(TwitterUsers.this);



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
                        listView.setAdapter(adapter);
                        for(String twitterUser:twitterUsers){
                            if(ParseUser.getCurrentUser().getList("fanOf")!=null) {
                                if (ParseUser.getCurrentUser().getList("fanOf").contains(twitterUser)) {
                                    listView.setItemChecked(twitterUsers.indexOf(twitterUser), true);

                                }
                            }
                        }
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
            case R.id.sendItem:
                twitterUsersCallback.sendTweet();
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CheckedTextView checkedTextView= (CheckedTextView) view;
        if(checkedTextView.isChecked()){
            FancyToast.makeText(getActivity(),twitterUsers.get(position)+" is now followed",Toast.LENGTH_SHORT,FancyToast.INFO,false).show();
            ParseUser.getCurrentUser().add("fanOf",twitterUsers.get(position));
        }else{
            FancyToast.makeText(getActivity(),twitterUsers.get(position)+" is not followed",Toast.LENGTH_SHORT,FancyToast.INFO,false).show();
            ParseUser.getCurrentUser().getList("fanOf").remove(twitterUsers.get(position));
            List currentUserFanOfList =ParseUser.getCurrentUser().getList("fanOf");
            ParseUser.getCurrentUser().remove("fanOf");
            ParseUser.getCurrentUser().put("fanOf",currentUserFanOfList);
        }
        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    FancyToast.makeText(getContext(),"Saved",Toast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
                }
            }
        });

    }

    public interface TwitterUsersCallback {
        void logout();
        void sendTweet();
    }
}
