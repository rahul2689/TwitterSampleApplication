package com.kayako.twitterapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

import android.util.Base64;

import com.google.gson.Gson;

public class HttpConnectionHandling {
	private final static String TwitterTokenURL = "https://api.twitter.com/oauth2/token";
	private final static String TwitterSearchURL = "https://api.twitter.com/1.1/search/tweets.json?q=%23";
	private String mCustomerKey;
	private String mCustomerSecret;

	public HttpConnectionHandling (String customerKey, String customerSecret){
		mCustomerKey = customerKey;
		mCustomerSecret = customerSecret;
	}
	private Authenticated jsonToAuthenticated(String rawAuthorization) {
		Authenticated auth = null;
		if (rawAuthorization != null && rawAuthorization.length() > 0) {
			try {
				Gson gson = new Gson();
				auth = gson.fromJson(rawAuthorization, Authenticated.class);
			} catch (IllegalStateException ex) {
			}
		}
		return auth;
	}

	private String getResponseBody(HttpRequestBase request) {
		StringBuilder builder = new StringBuilder();
		try {

			DefaultHttpClient httpClient = new DefaultHttpClient(
					new BasicHttpParams());
			HttpResponse response = httpClient.execute(request);
			int statusCode = response.getStatusLine().getStatusCode();
			String reason = response.getStatusLine().getReasonPhrase();

			if (statusCode == 200) {

				HttpEntity entity = response.getEntity();
				InputStream inputStream = entity.getContent();

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream, "UTF-8"), 8);
				String line = null;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
				builder.append(reason);
			}
		} catch (UnsupportedEncodingException ex) {
		} catch (ClientProtocolException ex1) {
		} catch (IOException ex2) {
		}
		return builder.toString();
	}

	private String getStream(String url) {
		String results = null;

		try {
			String urlApiKey = URLEncoder.encode(mCustomerKey, "UTF-8");
			String urlApiSecret = URLEncoder.encode(mCustomerSecret,
					"UTF-8");

			String combined = urlApiKey + ":" + urlApiSecret;

			String base64Encoded = Base64.encodeToString(
					combined.getBytes(), Base64.NO_WRAP);

			HttpPost httpPost = new HttpPost(TwitterTokenURL);
			httpPost.setHeader("Authorization", "Basic " + base64Encoded);
			httpPost.setHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			httpPost.setEntity(new StringEntity(
					"grant_type=client_credentials"));
			String rawAuthorization = getResponseBody(httpPost);
			Authenticated auth = jsonToAuthenticated(rawAuthorization);

			if (auth != null && auth.token_type.equals("bearer")) {

				HttpGet httpGet = new HttpGet(url);

				httpGet.setHeader("Authorization", "Bearer "
						+ auth.access_token);
				httpGet.setHeader("Content-Type", "application/json");
				results = getResponseBody(httpGet);
			}
		} catch (UnsupportedEncodingException ex) {
		} catch (IllegalStateException ex1) {
		}
		return results;
	}

	public String getSearchStream(String searchTerm) {
		String results = null;
		try {
			String encodedUrl = URLEncoder.encode(searchTerm, "UTF-8");
			results = getStream(TwitterSearchURL + encodedUrl);
		} catch (UnsupportedEncodingException ex) {
		} catch (IllegalStateException ex1) {
		}
		return results;
	}

}
