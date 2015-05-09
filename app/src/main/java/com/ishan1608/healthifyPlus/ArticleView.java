package com.ishan1608.healthifyPlus;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class ArticleView extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_view);

        // Getting Info
        Intent callingIntent = getIntent();
        Bundle articleInfoBundle = callingIntent.getBundleExtra(HomeFragment.ARTICLE_BUNDLE_KEY);
        String articleTitle = articleInfoBundle.getString(HomeFragment.ARTICLE_TITLE_KEY);
        String articleFavIcon = articleInfoBundle.getString(HomeFragment.ARTICLE_FAVICON_KEY);
        String articleLink = articleInfoBundle.getString(HomeFragment.ARTICLE_LINK_KEY);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_article_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
