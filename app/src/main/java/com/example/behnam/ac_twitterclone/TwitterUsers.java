package com.example.behnam.ac_twitterclone;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TwitterUsers extends androidx.fragment.app.Fragment {
    private TwitterUsersCallback twitterUsersCallback;
    private ArrayList<String> twitterUsers = new ArrayList<>();
    private RecyclerView recyclerView;
    private UsersAdapter usersAdapter;
/*
    private ListView listView;
    private ArrayAdapter adapter;
*/


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
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_twitter_users, container, false);
/*
        listView = view.findViewById(R.id.listView);
*/

        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Getting data");
        progressDialog.show();
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (ParseUser twitterUser : objects) {
                            twitterUsers.add(twitterUser.get("username").toString());
                        }
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "finished" + twitterUsers.size(), Toast.LENGTH_SHORT).show();
                        usersAdapter = new UsersAdapter(twitterUsers);
                        recyclerView.setAdapter(usersAdapter);
                    }

                } else {
                    FancyToast.makeText(getActivity(), e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                }
            }
        });
/*        adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, twitterUsers);
        listView.setAdapter(adapter);*/
        return view;

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

    private class UsersHolder extends RecyclerView.ViewHolder {
        private String user;
        private TextView userTextView;

        public UsersHolder(@NonNull View itemView) {
            super(itemView);
            userTextView = itemView.findViewById(R.id.user_text_view);
        }

        public void bind(String twitterUser) {
            user = twitterUser;
            userTextView.setText(user);
        }
    }

    private class UsersAdapter extends RecyclerView.Adapter<UsersHolder> {
        private ArrayList<String> twitterUsers;

        public UsersAdapter(ArrayList<String> twitterUsers) {
            this.twitterUsers = twitterUsers;
        }

        public void setTwitterUsers(ArrayList<String> twitterUsers) {
            this.twitterUsers = twitterUsers;
        }

        @NonNull
        @Override
        public UsersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.recycler_view_layout, parent, false);
            return new UsersHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull UsersHolder holder, int position) {
            holder.bind(twitterUsers.get(position));
        }

        @Override
        public int getItemCount() {
            return twitterUsers.size();
        }
    }
}
