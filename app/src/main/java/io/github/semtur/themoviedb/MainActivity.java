package io.github.semtur.themoviedb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private static  final String SAVED_IS_DOWNLOAD_STARTED = "is_data_download_started";
    private FragmentManager mFragmentManager = getSupportFragmentManager();
    private boolean mIsDownloadStarted;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mIsDownloadStarted = false;
            boolean isDataDownloaded = intent.getBooleanExtra(ServiceOfDataDownload.EXTRA_IS_DATA_DOWNLOADED, false);
            Fragment fragment;
            if (isDataDownloaded) {
                fragment = new MoviesFragment();
            } else {
                fragment = StartScreenFragment.newInstance(mIsDownloadStarted);
            }
            mFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            mIsDownloadStarted = savedInstanceState.getBoolean(SAVED_IS_DOWNLOAD_STARTED);
        }
        updateData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ServiceOfDataDownload.ACTION_SEND_MOVIES_INFO);
        registerReceiver(mBroadcastReceiver, intentFilter, ServiceOfDataDownload.PERM_THE_MOVIE_DB, null);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putBoolean(SAVED_IS_DOWNLOAD_STARTED, mIsDownloadStarted);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mBroadcastReceiver);
    }

    protected void updateData() {
        if (mIsDownloadStarted) {
            return;
        }
        Intent intent = new Intent(this, ServiceOfDataDownload.class);
        startService(intent);
        mIsDownloadStarted = true;
        showFragment(StartScreenFragment.class);
    }

    private void showFragment(Class fClass) {
        Fragment fragment = mFragmentManager.findFragmentById(R.id.fragment_container);
        if (fragment != null && fClass.equals(fragment.getClass())) {
            return;
        }
        if (fClass.equals(StartScreenFragment.class)) {
            fragment = StartScreenFragment.newInstance(mIsDownloadStarted);
        }
        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
