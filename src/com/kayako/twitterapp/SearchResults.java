package com.kayako.twitterapp;


import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class SearchResults {

	@SerializedName("statuses")
	private List<Search> mStatuses;


	public SearchResults(){
		mStatuses = new ArrayList<Search>();
	}
	public List<Search> getStatuses() {
		if(mStatuses == null){
			return new ArrayList<Search>();
		}
		return mStatuses;
	}

	public void setStatuses(List<Search> statuses) {
		this.mStatuses = statuses;
	}
}
