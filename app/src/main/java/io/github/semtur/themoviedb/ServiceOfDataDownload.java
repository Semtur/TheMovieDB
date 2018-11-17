package io.github.semtur.themoviedb;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServiceOfDataDownload extends IntentService {
    static final String ACTION_SEND_MOVIES_INFO = "io.github.semtur.themoviedb.sendmoviesinfo";
    static final String PERM_THE_MOVIE_DB = "io.github.semtur.themoviedb.mypermission";
    static final String EXTRA_IS_DATA_DOWNLOADED = "ua.kiev.semtur.ukrainiancurrencyexchangeratesnoad.isdatadownloaded";

    private static final String TAG = "ServiceOfDataDownload";
    private static final String API_KEY = "c726553e33d0cde2fd631bd9d5c06b2d";
    private static final String URL = "https://api.themoviedb.org/3/movie/";
    private static final String LANGUAGE = "en-US";

    private Handler mHandlerPostMessage;
    private CharSequence mToastText;
    private Runnable mRunnablePostMessage = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(getApplicationContext(), mToastText, Toast.LENGTH_SHORT).show();
        }
    };

    private MovieLibrary mLibrary;

    public ServiceOfDataDownload() {
        super(TAG);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mLibrary = MovieLibrary.getInstance();
        mHandlerPostMessage = new Handler();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (!isNetworkAvailableAnConnected()) {
            mToastText = getText(R.string.network_issues);
            mHandlerPostMessage.post(mRunnablePostMessage);
            sendMessage(false);
            return;
        }
        try {
            String url = buildUrl("top_rated");
            String jsonString = new String(getData(url));
            parseJSONArray(jsonString);
            sendMessage(true);
        } catch (IOException e) {
            mToastText = e.toString();
            mHandlerPostMessage.post(mRunnablePostMessage);
            sendMessage(false);
            return;
        } catch (JSONException e) {
            mToastText = e.toString();
            mHandlerPostMessage.post(mRunnablePostMessage);
            sendMessage(false);
        }
    }

    private void parseJSON(String jsonString) throws JSONException {
        GsonBuilder gs = new GsonBuilder();
        Gson gson = gs.create();
        MovieInfo movieInfo = gson.fromJson(jsonString, MovieInfo.class);
        mLibrary.addMovie(movieInfo);
    }

    private void parseJSONArray(String jsonString) throws JSONException, IOException {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray("results");
        for (int i = 0; i < jsonArray.length(); i++) {
            String jsonText = jsonArray.getString(i);
            GsonBuilder gs = new GsonBuilder();
            Gson gson = gs.create();
            MovieLibItem item = gson.fromJson(jsonText, MovieLibItem.class);
            mLibrary.addMovie(item);
            String url = buildUrl(item.getId());
            String jsonStr = new String(getData(url)) ;
            parseJSON(jsonStr);
        }
    }

    private boolean isNetworkAvailableAnConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable && cm.getActiveNetworkInfo().isConnected();
        return isNetworkConnected;
    }

    private void sendMessage(boolean isDownloadFinishedSuccessful) {
        Intent intent = new Intent(ACTION_SEND_MOVIES_INFO);
        intent.putExtra(EXTRA_IS_DATA_DOWNLOADED, isDownloadFinishedSuccessful);
        sendBroadcast(intent, PERM_THE_MOVIE_DB);
    }

    private byte[] getData(String s) throws IOException {
        java.net.URL url = new URL(s);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + ": with " + s);
            }
            int readBytes;
            byte[] buffer = new byte[1024];
            while ((readBytes = in.read(buffer)) > 0) {
                out.write(buffer, 0, readBytes);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    private String buildUrl(String s) {
        return Uri
                .parse(URL + s)
                .buildUpon()
                .appendQueryParameter("api_key", API_KEY)
                .appendQueryParameter("language", LANGUAGE)
                .build()
                .toString();
    }
}
