package com.kayako.twitterapp;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class UserEntity {
	
	@SerializedName("hashtags")
	private List<HashTag> mHashTags;

	
	public UserEntity (){
		mHashTags = new ArrayList<HashTag>();
	}
	public List<HashTag> getHashTags() {
		return mHashTags;
	}

	public void setHashTags(List<HashTag> hashTags) {
		this.mHashTags = hashTags;
	}
	
	
}
