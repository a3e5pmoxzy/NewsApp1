package com.example.android.newsapp1;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Displays a {@link ViewPager} where each page shows a different section of the news
 */
public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<com.example.android.newsapp1.Feed>> {
    private TextView mEmptyStateTextView;
    private NewsAdapter mAdapter;
    private boolean isIntentSafe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);
        ListView feedListView = (ListView) findViewById(R.id.sec_cpromos_news_list);
        mAdapter = new com.example.android.newsapp1.NewsAdapter(this, new ArrayList<com.example.android.newsapp1.Feed>());
        feedListView.setAdapter(mAdapter);

        feedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                com.example.android.newsapp1.Feed currentFeed = mAdapter.getItem(position);
                Uri feedUri = Uri.parse(currentFeed.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, feedUri);
                PackageManager packageMgr = getBaseContext().getPackageManager();
                List<ResolveInfo> activities = packageMgr.queryIntentActivities(
                        websiteIntent, 0);
                isIntentSafe = (activities.size() > 0);

                if (isIntentSafe) {
                    getBaseContext().startActivity(websiteIntent);
                }
            }
        });
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        feedListView.setEmptyView(mEmptyStateTextView);
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(VarUtils.FEED_LOADER_ID1, null, this);
        } else {
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<com.example.android.newsapp1.Feed>> onCreateLoader(int i, Bundle bundle) {
        return new com.example.android.newsapp1.FeedLoader(this, VarUtils.JSON_REQUEST_URL1);
    }

    @Override
    public void onLoadFinished(Loader<List<com.example.android.newsapp1.Feed>> loader, List<com.example.android.newsapp1.Feed> feeds) {
        mEmptyStateTextView.setText(R.string.no_feeds);
        if (mAdapter != null) {
            mAdapter.clear();
        }

        if (feeds != null && !feeds.isEmpty()) {
            mAdapter.addAll(feeds);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<com.example.android.newsapp1.Feed>> loader) {
        mAdapter.clear();
    }
}
