package com.ishan1608.healthifyPlus;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


public class ArticleView extends Activity {

    private String articleLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_view);

        // Getting Info
        Intent callingIntent = getIntent();
        Bundle articleInfoBundle = callingIntent.getBundleExtra(HomeFragment.ARTICLE_BUNDLE_KEY);
        String articleTitle = articleInfoBundle.getString(HomeFragment.ARTICLE_TITLE_KEY);
        String articleFavIcon = articleInfoBundle.getString(HomeFragment.ARTICLE_FAVICON_KEY);
        articleLink = articleInfoBundle.getString(HomeFragment.ARTICLE_LINK_KEY);

        // Setting up a custom action bar
        setupCustomActionBar(articleFavIcon, articleTitle, articleLink);

        // Displaying the article inside our application
        WebView articleWebView = (WebView) findViewById(R.id.article_webView);
        final Activity activity = this;
        // Settings for webView
        articleWebView.getSettings().setJavaScriptEnabled(true);
        articleWebView.getSettings().setLoadWithOverviewMode(true);
        articleWebView.getSettings().setUseWideViewPort(true);
        // Setting a custom webView client
        articleWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                if (errorCode == -2) {
                    Toast.makeText(activity, "Oh no! Could not fetch the article for you.\nTry viewing in browser.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_LONG).show();
                }
            }
        });
        // Loading url
        articleWebView.loadUrl(articleLink);

    }

    private void setupCustomActionBar(String articleFavIcon, String articleTitle, String articleLink) {
        ActionBar articleActionBar = getActionBar();
        // Setting Custom View
        articleActionBar.setDisplayShowTitleEnabled(false);
        articleActionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater actionBarLayoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View actionBarView = actionBarLayoutInflater.inflate(R.layout.article_view_action_bar, null);

        // Image Loader library settings
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext().getApplicationContext()).defaultDisplayImageOptions(defaultOptions).build();
        ImageLoader.getInstance().init(config);
        // Setting image
        ImageView articleFavIconImageView = (ImageView) actionBarView.findViewById(R.id.action_bar_article_site_icon);
        ImageLoader.getInstance().displayImage(articleFavIcon, articleFavIconImageView);
        // Setting title and link
        TextView articleTitleTextView = (TextView) actionBarView.findViewById(R.id.action_bar_article_title);
        articleTitleTextView.setText(articleTitle);
        TextView articleLinkTextView = (TextView) actionBarView.findViewById(R.id.action_bar_article_link);
        articleLinkTextView.setText(articleLink);
        // Displaying the custom View
        articleActionBar.setCustomView(actionBarView);
        articleActionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_article_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_share:
//                Toast.makeText(this, "share", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, articleLink);
                startActivity(Intent.createChooser(intent, "Share with"));
                break;
            case R.id.action_open_in_browser:
//                Toast.makeText(this, "open", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_copy_link:
                break;
            case R.id.action_email_link:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
