package com.example.vinaygoyal.newsapp;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    final static String BASE_URL =
            "https://newsapi.org/v1/articles";

    final static String QUERY = "source";
    final static String SORT = "sortBy";
    final static String sortBy = "apiKey";
    final static String QUERY_VALUE="the-next-web";
    final static String SORT_VALUE="latest";
    final static String  SORTBY_VALUE="";


    public static URL buildUrl(String githubSearchQuery) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(QUERY, QUERY_VALUE)
                .appendQueryParameter(SORT, SORT_VALUE)
                .appendQueryParameter(sortBy,SORTBY_VALUE)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}