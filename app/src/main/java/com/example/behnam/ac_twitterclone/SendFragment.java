package com.example.behnam.ac_twitterclone;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.material.button.MaterialButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SendFragment extends androidx.fragment.app.Fragment implements View.OnClickListener {
    private SendFragmentCallback sendFragmentCallback;
    private EditText edtTweet;
    private MaterialButton btnTweet;
    private MaterialButton btnViewTweets;
    private ListView viewTweetsListView;
    public SendFragment() {
        // Required empty public constructor
    }

    public static SendFragment newInstance() {

        Bundle args = new Bundle();

        SendFragment fragment = new SendFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_send, container, false);
        edtTweet = view.findViewById(R.id.edtTweet);
        btnTweet = view.findViewById(R.id.btnSend);
        btnViewTweets=view.findViewById(R.id.btnViewTweets);
        viewTweetsListView=view.findViewById(R.id.viewTweetsListView);
        btnViewTweets.setOnClickListener(this);

        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseObject parseObject = new ParseObject("MyTweet");
                parseObject.put("tweet", edtTweet.getText().toString());
                parseObject.put("user", ParseUser.getCurrentUser().getUsername());
                final ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            FancyToast.makeText(getContext(), "tweet has sent", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                        } else {
                            FancyToast.makeText(getContext(), "tweet hasn't sent", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                        }
                        progressDialog.dismiss();
                    }
                });
            }
        });


        return view;
    }

    @Override
    public void onClick(View v) {
        final ArrayList<HashMap<String,String>> tweetList=new ArrayList<>();
        final SimpleAdapter adapter=new SimpleAdapter(getContext(),tweetList,android.R.layout.simple_list_item_2,new String[]{"tweetUserName","tweetValue"},new int[]{android.R.id.text1,android.R.id.text2});
        try{
            ParseQuery<ParseObject> parseQuery=ParseQuery.getQuery("MyTweet");
            parseQuery.whereContainedIn("user",ParseUser.getCurrentUser().getList("fanOf"));
            parseQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if(objects.size()>0 && e==null){
                        for(ParseObject tweetObject:objects){
                            HashMap<String ,String> userTweet=new HashMap<>();
                            userTweet.put("tweetUserName",tweetObject.getString("user"));
                            userTweet.put("tweetValue",tweetObject.getString("tweet"));
                            tweetList.add(userTweet);
                        }
                        viewTweetsListView.setAdapter(adapter);
                    }

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public interface SendFragmentCallback {

    }
}
