package com.ishan1608.googlefittest;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class HomeFragment extends Fragment {

    private static final String TAG = "HOME-FRAGMENT";
    private View returnView;
    private ListView articlesListView;
    private LinearLayout internetRetryScreen;
    private ImageButton internetRetryButton;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        returnView = inflater.inflate(R.layout.fragment_home, container, false);
        articlesListView = (ListView) returnView.findViewById(R.id.articles_list);
        internetRetryScreen = (LinearLayout) returnView.findViewById(R.id.internet_retry_screen);

        // Retry Button
        internetRetryButton = (ImageButton) returnView.findViewById(R.id.internet_retry_image_button);
        internetRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "internetRetryButton clicked");
                checkInternetAndDisplay();
            }
        });

        // Execution flow for first entry
        checkInternetAndDisplay();

        return returnView;
    }

    // Fetching articles list from the internet
    private void fetchArticles() {
        // Connect to internet and grab
        Thread articleListThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // This is sending GET request for checking articles
                    Uri articleListUri = Uri.parse("http://health-server.herokuapp.com/articleList").buildUpon()
                            .build();
                    URL articleListURL = null;
                    articleListURL = new URL(articleListUri.toString());

                    // Create the request to health-server, and open the connection
                    HttpURLConnection articleListURLConnection = (HttpURLConnection) articleListURL.openConnection();
                    articleListURLConnection.setRequestMethod("GET");
                    articleListURLConnection.connect();

                    // Read the input stream into a String
                    InputStream articleListInputStream = articleListURLConnection.getInputStream();
                    StringBuffer articleListStringBuffer = new StringBuffer();

                    if (articleListInputStream == null) {
                        // Display the error screen once again
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Hiding List View
                                articlesListView.setVisibility(View.GONE);
                                // Displaying the error panel
                                internetRetryScreen.setVisibility(View.VISIBLE);
                            }
                        });
                        return;
                    }

                    BufferedReader articleListBufferedReader = new BufferedReader(new InputStreamReader(articleListInputStream));

                    String line;
                    while ((line = articleListBufferedReader.readLine()) != null) {
                        // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                        // But it does make debugging a *lot* easier if you print out the completed
                        // articleListStringBuffer for debugging.
                        articleListStringBuffer.append(line + "\n");
                    }

                    if (articleListStringBuffer.length() == 0) {
                        // Display the error screen once again
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d(TAG, "articleListStringBuffer.length is 0");
                                // Hiding List View
                                articlesListView.setVisibility(View.GONE);
                                // Displaying the error panel
                                internetRetryScreen.setVisibility(View.VISIBLE);
                            }
                        });
                        return;
                    } else {
                        String articleListJsonString = articleListStringBuffer.toString();

                        // Have to extract JSON from string
                        final JSONObject articleListJSONObject = new JSONObject(articleListJsonString);
                        final JSONArray articleListJSONArray = articleListJSONObject.getJSONArray("articles");

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Setting the list of adapter
                                articlesListView.setAdapter(new articleListAdapter(getActivity(), articleListJSONArray));
                            }
                        });
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        articleListThread.start();
    }

    private void checkInternetAndDisplay() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(getActivity().getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.d(TAG, "Internet connected");
            // Hiding error panel
            internetRetryScreen.setVisibility(View.GONE);
            // Fetch articles from internet
            fetchArticles();
            Log.d(TAG, "Article fetch done");
            // Displaying article list
            articlesListView.setVisibility(View.VISIBLE);
            Log.d(TAG, "Displayed the article list");
        } else {
            Log.d(TAG, "Internet not connected");
            // Hiding List View
            articlesListView.setVisibility(View.GONE);
            // Displaying the error panel
            internetRetryScreen.setVisibility(View.VISIBLE);
        }
    }

    private class articleListAdapter extends BaseAdapter {
        private static final String TAG = "ARTICLE-ADAPTER";
        Activity activity;
        JSONArray articleList;
        private TextView articleTitleTextView;
        private LinearLayout articleHolder;

        public articleListAdapter(Activity activity, JSONArray articleList) {
            this.activity = activity;
            this.articleList = articleList;
        }

        @Override
        public int getCount() {
            return articleList.length();
        }

        @Override
        public Object getItem(int position) {
            try {
                return articleList.getJSONObject(position);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = activity.getLayoutInflater().inflate(R.layout.article_list_item, null);
            }
            articleTitleTextView = (TextView) convertView.findViewById(R.id.article_title);
            try {
                articleTitleTextView.setText(articleList.getJSONObject(position).getString("title"));
//                Log.d(TAG, articleList.getJSONObject(position).getString("title"));
//                Log.d(TAG, articleList.getJSONObject(position).getString("link"));
                articleHolder = (LinearLayout) convertView.findViewById(R.id.article_holder);
                articleHolder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Intent articleBrowserIntent = new Intent(Intent.ACTION_VIEW);
//                            Log.d(TAG, "Intent-URL " + articleList.getJSONObject(position).getString("link"));
                            articleBrowserIntent.setData(Uri.parse(articleList.getJSONObject(position).getString("link")));
                            startActivity(articleBrowserIntent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Intent articleBrowserIntent = new Intent(Intent.ACTION_VIEW);
//                            Log.d(TAG, "Intent-URL " + "https://encrypted.google.com");
                            articleBrowserIntent.setData(Uri.parse("https://encrypted.google.com"));
                            startActivity(articleBrowserIntent);
                        };
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
                articleTitleTextView.setText("Title");
                articleHolder = (LinearLayout) convertView.findViewById(R.id.article_holder);
                articleHolder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent articleBrowserIntent = new Intent(Intent.ACTION_VIEW);
//                        Log.d(TAG, "Intent-URL " + "https://encrypted.google.com");
                        articleBrowserIntent.setData(Uri.parse("https://encrypted.google.com"));
                        startActivity(articleBrowserIntent);
                    }
                });
            }
            return convertView;
        }
    }
}