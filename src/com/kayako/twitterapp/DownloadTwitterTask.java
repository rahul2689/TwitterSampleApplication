package com.kayako.twitterapp;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

public class DownloadTwitterTask extends AsyncTask<String, Void, String> {
	private ArrayList<String> tweets = new ArrayList<String>();
	private MainActivity mActivity;
	private ProgressDialog mProgressDialog;
	private final static String LOG_TAG = "MainActivity";
	private static final String HASHTAG = "custserv";
	private String mCustomerKey = "";
	private String mCustomerSecret = "";
	private HttpConnectionHandling mConnectionHandling;

	public DownloadTwitterTask(MainActivity mainActivity,
			ProgressDialog progressDialog, String customerKey,
			String customerSecret) {
		mActivity = mainActivity;
		mProgressDialog = progressDialog;
		mCustomerKey = customerKey;
		mCustomerSecret = customerSecret;
		mConnectionHandling = new HttpConnectionHandling(mCustomerKey,
				mCustomerSecret);
	}

	@Override
	protected void onPreExecute() {
		mProgressDialog.show();
	}

	@Override
	protected String doInBackground(String... searchTerms) {
		String result = null;
		if (searchTerms.length > 0) {
			result = mConnectionHandling.getSearchStream(searchTerms[0]);
		}
		return result;
	}

	@Override
	protected void onPostExecute(String result) {
		mProgressDialog.dismiss();
		List<Search> allTweets = jsonToSearches(result);

		for (Search search : allTweets) {
			Log.i(LOG_TAG, search.getText());
		}

		filterTweets(allTweets);
		mActivity.populateTweets(tweets);
	}

	private void filterTweets(List<Search> allTweets) {
		int allTweetsSize = allTweets.size();
		int indexAllTweets, indexHashTags;
		for (indexAllTweets = 0; indexAllTweets < allTweetsSize; indexAllTweets++) {
			if (allTweets.get(indexAllTweets).getRetweetCount() > 0) {
				int hashTagsSize = allTweets.get(indexAllTweets)
						.getUserEntity().getHashTags().size();
				for (indexHashTags = 0; indexHashTags < hashTagsSize; indexHashTags++) {
					if (allTweets.get(indexAllTweets).getUserEntity()
							.getHashTags().get(indexHashTags).getHashTagText()
							.equalsIgnoreCase(HASHTAG)) {
						tweets.add(allTweets.get(indexAllTweets).getText());
					}
				}
			}
		}
	}

	private List<Search> jsonToSearches(String result) {
		List<Search> searches = new ArrayList<Search>();
		if (result != null && result.length() > 0) {
			try {
				Gson gson = new Gson();
				SearchResults searchResults = gson.fromJson(result,
						SearchResults.class);
				searches = searchResults.getStatuses();
			} catch (IllegalStateException ex) {
			}
		}
		return searches;
	}
}
