package com.example.vinaygoyal.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vinaygoyal.newsapp.model.NewsItem;
import com.example.vinaygoyal.newsapp.NetworkUtils;
import com.example.vinaygoyal.newsapp.NewsAdapter;
import com.example.vinaygoyal.newsapp.ParseJSON;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "mainactivity";

    private TextView errorMessageTextView;

    private ProgressBar progressIndicatorLoading;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressIndicatorLoading = (ProgressBar) findViewById(R.id.progressbar_loading_indicator);
        errorMessageTextView = (TextView) findViewById(R.id.error_message);
        recyclerView = (RecyclerView) findViewById(R.id.news_result);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void showErrorMessage() {

        errorMessageTextView.setVisibility(View.VISIBLE);
    }

    public class NewsTask extends AsyncTask<URL, Void,ArrayList<NewsItem>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressIndicatorLoading.setVisibility(View.VISIBLE);


        }



        @Override
        protected ArrayList<NewsItem> doInBackground(URL... params) {
            ArrayList<NewsItem> news= null;
            URL newsURL = NetworkUtils.buildUrl();
            Log.d(TAG, "url: " + newsURL.toString());

            try {
                String json = NetworkUtils.getResponseFromHttpUrl(newsURL);
                news = ParseJSON.parseJsonData(json);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return news;
        }

        @Override
        protected void onPostExecute(final ArrayList<NewsItem> data) {
            super.onPostExecute(data);
            progressIndicatorLoading.setVisibility(View.INVISIBLE);
            errorMessageTextView.setVisibility(View.INVISIBLE);
            if (data!=null) {
                NewsAdapter adapter = new NewsAdapter(data, new NewsAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(int clickedItemIndex) {
                        String url = data.get(clickedItemIndex).getUrl();
                        Log.d(TAG, String.format("Url %s", url));
                        openWebPage(url);
                    }
                });
                recyclerView.setAdapter(adapter);

            } else {
                showErrorMessage();

            }
        }

    }

    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemSelected = item.getItemId();
        if (itemSelected == R.id.action_search) {
            NewsTask task = new NewsTask();
            task.execute();

        }

        return true;
    }
}