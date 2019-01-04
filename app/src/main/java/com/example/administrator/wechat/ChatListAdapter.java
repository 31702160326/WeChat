package com.example.administrator.wechat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/1/3.
 */

public class ChatListAdapter extends BaseAdapter {
    ArrayList<ChatInfo> chatList;
    LayoutInflater inflater;




    public ChatListAdapter(Context context, ArrayList<ChatInfo> chatList) {
        inflater = LayoutInflater.from(context);
        this.chatList = chatList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return chatList.size();
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
        TextView chat;
        TextView time;
        TextView name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View view = convertView;
        ChatListAdapter.ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.chat_list, null);
            holder = new ChatListAdapter.ViewHolder();
            holder.chat = (TextView) view.findViewById(R.id.chat);
            holder.time = (TextView) view.findViewById(R.id.time);
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ChatListAdapter.ViewHolder) view.getTag();
        }
        holder.chat.setText(chatList.get(position).getChat());
        holder.time.setText(chatList.get(position).getTime());
        holder.name.setText(chatList.get(position).getName());
        return view;
    }
}
