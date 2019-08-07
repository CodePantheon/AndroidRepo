/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codepantheon.searchflickr.utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the flickr server.
 */
public final class NetworkUtils {

    private static final String FLICKR_BASE_URL = "https://www.flickr.com/services/rest";

    private final static String METHOD_PARAM = "method";
    private final static String API_KEY_PARAM = "api_key";
    private final static String TEXT_PARAM = "text";
    private final static String FORMAT_PARAM = "format";
    private final static String NO_JSON_CALLBACK_PARAM = "nojsoncallback";

    private static final String apiMethod = "flickr.photos.search";
    private static final String apiKey = "c0e0e8f299d73bfef0c1de90136f9048";
    private static final String responceFormat = "json";
    private static final int noJsonCallback = 1;

    public static URL buildUrl(String searchKeyword) {
        Uri builtUri = Uri.parse(FLICKR_BASE_URL).buildUpon()
                .appendQueryParameter(METHOD_PARAM, apiMethod)
                .appendQueryParameter(API_KEY_PARAM, apiKey)
                .appendQueryParameter(TEXT_PARAM, searchKeyword)
                .appendQueryParameter(FORMAT_PARAM, responceFormat)
                .appendQueryParameter(NO_JSON_CALLBACK_PARAM, Integer.toString(noJsonCallback))
                .build();

        URL searchUrl = null;
        try {
            searchUrl = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v("SearchFlickr", "Built URI " + searchUrl);

        return searchUrl;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
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