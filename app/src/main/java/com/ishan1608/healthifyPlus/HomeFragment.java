package com.ishan1608.healthifyPlus;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

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
import java.util.Random;


public class HomeFragment extends Fragment {

    private static final String TAG = "HOME-FRAGMENT";
    private View returnView;
    private ListView articlesListView;
    private LinearLayout internetRetryScreen;
    private ImageButton internetRetryButton;

    // Color pallete
    public static String[] colorPaletteBright = {"#9C33842D", "#9CC2758B", "#9CF10008"};
    public static String[] colorPaletteLight = {"#9CFFC828", "#9CFF9809", "#9C31BCB4"};
    private ProgressDialog articleProgressDialog;

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
        // Progress Dialog
        articleProgressDialog = ProgressDialog.show(getActivity(), "Fetching Articles", "We are fetching article suggestions for you. Please Wait.", true);

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

                                // Dismissing the article progress dialog
                                articleProgressDialog.dismiss();
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
        private int colorPaletteNumber;
        private TextView articleLinkTextView;

        public articleListAdapter(Activity activity, JSONArray articleList) {
            this.activity = activity;
            this.articleList = articleList;

            // Initializing Image Downloader library
            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity().getApplicationContext()).defaultDisplayImageOptions(defaultOptions).build();
            ImageLoader.getInstance().init(config);
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
            convertView = activity.getLayoutInflater().inflate(R.layout.article_list_item, null);
            // Getting handle for article heading and URL
            articleTitleTextView = (TextView) convertView.findViewById(R.id.article_title);
            articleLinkTextView = (TextView) convertView.findViewById(R.id.article_link);
            RelativeLayout articleHolderRelativeLayout = (RelativeLayout) convertView.findViewById(R.id.article_holder);
            ImageView articleIconImageView = (ImageView) convertView.findViewById(R.id.article_site_icon);

            // Setting background color of article title
            int randomColorPoint = new Random().nextInt(3);
            if(colorPaletteNumber == 1) {
                colorPaletteNumber = 2;
                articleHolderRelativeLayout.setBackgroundColor(Color.parseColor(colorPaletteBright[randomColorPoint]));
            } else {
                colorPaletteNumber = 1;
                articleHolderRelativeLayout.setBackgroundColor(Color.parseColor(colorPaletteLight[randomColorPoint]));
            }

//            // Setting content toggler for article URL
//            articleTitleTextView.setOnClickListener(new ContentToggler());

            try {
                // Setting title of article title
                articleTitleTextView.setText(articleList.getJSONObject(position).getString("title"));
                // Setting link of article link
                final String articleLink = articleList.getJSONObject(position).getString("link");
                String articleHost = articleLink.substring(articleLink.indexOf("//") + 2, articleLink.length());
                articleHost = articleHost.substring(0, articleHost.indexOf("/"));
                articleLinkTextView.setText(articleHost);

                // Setting icon for article
                String articleProtocolLink = articleLink.substring(0, articleLink.indexOf("//") + 2);
                ImageLoader.getInstance().displayImage(articleProtocolLink + articleHost + "/favicon.ico", articleIconImageView);

                // On click opening browser
                articleHolderRelativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent articleBrowserIntent = new Intent(Intent.ACTION_VIEW);
                        articleBrowserIntent.setData(Uri.parse(articleLink));
                        startActivity(articleBrowserIntent);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
                articleTitleTextView.setText("Title");
                articleLinkTextView.setText("encrypted.google.com");

                // On click opening browser
                articleHolderRelativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent articleBrowserIntent = new Intent(Intent.ACTION_VIEW);
                        Log.d(TAG, "Intent-URL " + "https://encrypted.google.com");
                        articleBrowserIntent.setData(Uri.parse("encrypted.google.com"));
                        startActivity(articleBrowserIntent);
                    }
                });
            }
            return convertView;
        }
    }
}