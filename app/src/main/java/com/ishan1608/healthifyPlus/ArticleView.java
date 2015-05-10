package com.ishan1608.healthifyPlus;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

import java.util.List;


public class ArticleView extends Activity {

    private String articleLink;
    private String articleTitle;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_view);

        context = this;

        // Getting Info
        Intent callingIntent = getIntent();
        Bundle articleInfoBundle = callingIntent.getBundleExtra(HomeFragment.ARTICLE_BUNDLE_KEY);
        articleTitle = articleInfoBundle.getString(HomeFragment.ARTICLE_TITLE_KEY);
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
        PackageManager packageManager = getPackageManager();
        int id = item.getItemId();
        switch (id) {
            case R.id.action_share:
//                Toast.makeText(this, "share", Toast.LENGTH_SHORT).show();
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, articleLink);
                List shareActivities = packageManager.queryIntentActivities(shareIntent,
                        PackageManager.MATCH_DEFAULT_ONLY);
                boolean isShareIntentSafe = shareActivities.size() > 0;
                if (isShareIntentSafe) {
                    startActivity(Intent.createChooser(shareIntent, "Share With"));
                } else {
                    Toast.makeText(getApplicationContext(), "There are no apps to share with on your phone.", Toast.LENGTH_LONG).show();
                }


                break;
            case R.id.action_open_in_browser:
//                Toast.makeText(this, "open", Toast.LENGTH_SHORT).show();
                // Opening browser
                Intent articleBrowserIntent = new Intent(Intent.ACTION_VIEW);
                articleBrowserIntent.setData(Uri.parse(articleLink));
                List browserActivities = packageManager.queryIntentActivities(articleBrowserIntent,
                        PackageManager.MATCH_DEFAULT_ONLY);
                boolean isBrowserIntentSafe = browserActivities.size() > 0;
                if (isBrowserIntentSafe) {
                    startActivity(Intent.createChooser(articleBrowserIntent, "Open With"));
                } else {
                    Toast.makeText(getApplicationContext(), "There are no browsers to open on your phone.", Toast.LENGTH_LONG).show();
                }
                startActivity(articleBrowserIntent);
                break;
            case R.id.action_copy_link:
                // Gets a handle to the clipboard service.
                ClipboardManager clipboard = (ClipboardManager)
                        getSystemService(Context.CLIPBOARD_SERVICE);
                // Creates a new text clip to put on the clipboard
                ClipData clip = ClipData.newPlainText(articleTitle, articleLink);
                // Set the clipboard's primary clip.
                clipboard.setPrimaryClip(clip);
                // Telling the user that the contents are copied
                Toast.makeText(getApplicationContext(), "Link copied", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_email_link:
                Intent emailDummyIntent = new Intent(Intent.ACTION_SENDTO);
                emailDummyIntent.setData(Uri.parse("mailto:some@emaildomain.com"));

                List<ResolveInfo> emailActivities = packageManager.queryIntentActivities(emailDummyIntent, 0);

                if (emailActivities != null) {
                    final List<ResolveInfo> emailActivitiesForDialog = emailActivities;

                    String[] availableEmailAppsName = new String[emailActivitiesForDialog.size()];
                    for (int i = 0; i < emailActivitiesForDialog.size(); i++)
                    {
                        availableEmailAppsName[i] = emailActivitiesForDialog.get(i).activityInfo.applicationInfo.loadLabel(packageManager).toString();
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Email with : ");
                    builder.setItems(availableEmailAppsName, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            sendEmailUsingSelectedEmailApp(context, articleTitle, "Hey, I found this great article using Healthify Plus\n" + articleLink + "\n\nThought you should know", emailActivitiesForDialog.get(which));
                        }
                    });

                    builder.create().show();
                } else {
                    Toast.makeText(context, "There are no email providers on your phone.", Toast.LENGTH_LONG).show();
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected static void sendEmailUsingSelectedEmailApp(Context p_context, String p_subject, String p_body,ResolveInfo p_selectedEmailApp)
    {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        // The intent does not have a URI, so declare the "text/plain" MIME type
        emailIntent.setType("text/plain");
//                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"jon@example.com"}); // recipients
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, p_subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, p_body);

        if (null != p_selectedEmailApp)
        {
            Log.d(ArticleView.class.getSimpleName(), "Sending email using " + p_selectedEmailApp);
            emailIntent.setComponent(new ComponentName(p_selectedEmailApp.activityInfo.packageName, p_selectedEmailApp.activityInfo.name));

            p_context.startActivity(emailIntent);
        }
        else
        {
            Intent emailAppChooser = Intent.createChooser(emailIntent, "Email using ");
            p_context.startActivity(emailAppChooser);
        }
    }
}
