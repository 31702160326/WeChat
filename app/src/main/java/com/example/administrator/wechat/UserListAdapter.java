package com.example.administrator.wechat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class UserListAdapter extends BaseAdapter {

	ArrayList<UserInfo> userList;
	LayoutInflater inflater;

	public UserListAdapter(Context context, ArrayList<UserInfo> userList) {
		inflater = LayoutInflater.from(context);
		this.userList = userList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return userList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	class ViewHolder {
		TextView name;
		TextView user;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
		ViewHolder holder;
		if (view == null) {
			view = inflater.inflate(R.layout.weather_list_item, null);
			holder = new ViewHolder();
			holder.name = (TextView) view.findViewById(R.id.name);
			holder.user = (TextView) view.findViewById(R.id.user);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.name.setText("账号："+userList.get(position).getName());
		holder.user.setText("用户名："+userList.get(position).getUser());

		return view;
	}

}
