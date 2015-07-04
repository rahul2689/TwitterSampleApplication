package com.kayako.twitterapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkManager {

	private MainActivity mActivity;
	private String mCustomerSecret;
	private String mCustomerKey;
	private ProgressDialog mProgressDialog;
	private final static String SEARCH_KEY = "custserv";
	private final static String LOG_TAG = "MainActivity";

	public NetworkManager(MainActivity mainActivity, String customerKey,
			String customerSecret, ProgressDialog progressDialog) {
		mActivity = mainActivity;
		mCustomerSecret = customerSecret;
		mCustomerKey = customerKey;
		mProgressDialog = progressDialog;
	}

	public void fetchTweetsFromNetwork() {
		ConnectivityManager connMgr = (ConnectivityManager) mActivity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		if (networkInfo != null && networkInfo.isConnected()) {
			DownloadTwitterTask downloadTwitterTask = new DownloadTwitterTask(
					mActivity, mProgressDialog, mCustomerKey, mCustomerSecret);
			downloadTwitterTask.execute(SEARCH_KEY);
		} else {
			Log.v(LOG_TAG, "No network connection available.");
		}
	}
}
