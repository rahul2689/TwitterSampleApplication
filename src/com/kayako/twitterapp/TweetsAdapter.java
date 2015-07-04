package com.kayako.twitterapp;

import java.util.List;

import com.kayako.twitterapp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TweetsAdapter extends BaseAdapter {

	private Context mContext;
	private List<String> mTweets;

	public TweetsAdapter(Context context, List<String> tweets) {
		mContext = context;
		mTweets = tweets;
	}

	@Override
	public int getCount() {
		return mTweets.size();
	}

	@Override
	public String getItem(int position) {
		return mTweets.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null || convertView.getTag() == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.tweets_list, parent, false);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.mTweetsTv.setText(mTweets.get(position));
		return convertView;
	}

	class ViewHolder {
		private TextView mTweetsTv;

		public ViewHolder(View view) {
			mTweetsTv = (TextView) view.findViewById(R.id.tv_tweets_list_text);
		}
	}
}
