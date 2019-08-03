package org.codepantheon.searchflickr.presenter;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.codepantheon.searchflickr.eventutilities.Event;
import org.codepantheon.searchflickr.model.ImageInfo;
import org.codepantheon.searchflickr.model.ImageInfoCollection;
import org.codepantheon.searchflickr.utils.NetworkUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class ImagePresenter {

    private static final String TAG = ImagePresenter.class.getSimpleName();
    private Event<ImageInfo[]> mImagesFetchedEvent = new Event<>();

    public void addImagesFetchedEventHandler(Event.EventHandler<ImageInfo[]> eventHandler) {mImagesFetchedEvent.add(eventHandler);}

    public void removeImagesFetchedEventHandler(Event.EventHandler<ImageInfo[]> eventHandler) {mImagesFetchedEvent.remove(eventHandler);}

    public void fetchImagesAsync(String imageSearchKeyword){
        new FetchImageTask().execute(imageSearchKeyword);
    }

    private class FetchImageTask extends AsyncTask<String, Void, ImageInfo[]>{

        @Override
        protected ImageInfo[] doInBackground(String... params) {
            if(params == null || params.length == 0){
                return null;
            }

            String imageSearchKeyword = params[0];
            URL imageSearchUrl = NetworkUtils.buildUrl(imageSearchKeyword);

            try {
                String jsonImageDetails = NetworkUtils.getResponseFromHttpUrl(imageSearchUrl);
                Log.v(TAG, jsonImageDetails);

                return deserialize(jsonImageDetails);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ImageInfo[] imageInfos) {
            mImagesFetchedEvent.invoke(imageInfos);
        }

        private ImageInfo[] deserialize(String jsonImageDetails){
            ImageInfo[] imageInfos = null;

            try {
                JSONObject jsonObject = new JSONObject(jsonImageDetails);
                String imageInfoCollectionJson = jsonObject.getString("photos");

                ImageInfoCollection imageInfoCollection = new Gson().fromJson(imageInfoCollectionJson, ImageInfoCollection.class);
                imageInfos = imageInfoCollection.getImageInfos();
            }catch (JSONException e){
                e.printStackTrace();
            }

            return imageInfos;
        }
    }
}
