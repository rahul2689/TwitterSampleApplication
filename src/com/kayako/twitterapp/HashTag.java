package com.kayako.twitterapp;

import com.google.gson.annotations.SerializedName;

public class HashTag {

	@SerializedName("text")
	private String mHashTagText;

	public HashTag() {
		mHashTagText = "";
	}

	public String getHashTagText() {
		return mHashTagText;
	}

	public void setHashTagText(String hashTagText) {
		this.mHashTagText = hashTagText;
	}
}
