package com.kayako.twitterapp;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import com.kayako.twitterapp.R;

public class MainActivity extends Activity {

	private String mCustomerKey = "";
	private String mCustomerSecret = "";
	private ListView mTweetsLv;
	private ProgressDialog mProgressDialog;
	private List<String> mTweets;
	private NetworkManager mNetworkManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen);
		mTweetsLv = (ListView) findViewById(R.id.lv_tweets);
		mProgressDialog = new ProgressDialog(MainActivity.this,
				"Fetching Data...", false);
		mCustomerKey = getStringFromManifest("CONSUMER_KEY");
		mCustomerSecret = getStringFromManifest("CONSUMER_SECRET");
		mNetworkManager = new NetworkManager(this, mCustomerKey,
				mCustomerSecret, mProgressDialog);
		mNetworkManager.fetchTweetsFromNetwork();
	}

	private String getStringFromManifest(String key) {
		String results = null;

		try {
			Context context = this.getBaseContext();
			ApplicationInfo info = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			results = (String) info.metaData.get(key);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}

	public void populateTweets(List<String> tweets) {
		mTweets = tweets;
		TweetsAdapter adapter = new TweetsAdapter(MainActivity.this, mTweets);
		mTweetsLv.setAdapter(adapter);
	}
}
