package com.example.administrator.wechat;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 简书ID：天哥在奔跑
 * 原创Android教程：http://www.jianshu.com/p/9618c038135f
 * 教程答疑专用QQ群：667833258
 */
public class HomeFragment extends Fragment {
    private EditText chat;
    ArrayList<ChatInfo> chatList;
    ListView lc;
    EditText et_lt;
    static final OkHttpClient client = new OkHttpClient();
    //    public HomeFragment() {
    //
    //    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case 3:
                    // 更新UI以显示获取到的网络数据
                    String result = msg.obj.toString();
                    Chat(result);
                    lc.setAdapter(new ChatListAdapter(getActivity(), chatList));
                    break;
            }

        }

    };


    public static HomeFragment newInstance(String login_user, String login_password) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("login_user", login_user);
        bundle.putString("login_password", login_password);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        lc = (ListView) view.findViewById(R.id.chatList);
        et_lt = (EditText) view.findViewById(R.id.et_lt);
        view.findViewById(R.id.btn_lt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String login_user = getArguments().getString("login_user");
                String login_password = getArguments().getString("login_password");
                Log.i("TAG", "聊天: " + login_user);
                Log.i("TAG", "聊天: " + login_password);
                String login_chat = et_lt.getText().toString().trim();

                Log.i("TAG", "聊天: " + login_chat);
                post_Form("http://123.207.85.214/chat/chat1.php", login_user, login_password, login_chat, handler);
            }
        });

        return view;
    }


    public static void post_Form(String url, String login_user, String login_password, String login_chat, final Handler handler) {
        FormBody requestBody = new FormBody.Builder()
                .add("user", login_user)
                .add("password", login_password)
                .add("chat", login_chat)
                .build();
        Request request = new Request.Builder().url(url).addHeader("Content-Type", "application/json; charset=UTF-8").post(requestBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call arg0, Response arg1) throws IOException {
                if (arg1.isSuccessful()) {
                    String returns = arg1.body().string();
                    Log.i("test", "post表单请求成功,返回ok=returns:" + returns);
                    Message msg3 = new Message();
                    msg3.what = 3;
                    msg3.obj = returns;
                    handler.sendMessage(msg3);
                } else {
                    Log.i("test", "post表单请求成功,返回NO");
                }
            }

            @Override
            public void onFailure(Call arg0, IOException arg1) {
                Log.i("test", "post表单请求失败");
            }
        });

    }


    private void Chat(String result) {
        try {
            JSONArray jsonMinfo = new JSONArray(result);
            //JSONObject resultJson = jsonMinfo.getJSONObject("result");

            //JSONArray WeatherArray = resultJson.getJSONArray("future");
            chatList = new ArrayList<ChatInfo>();
            for (int i = 0; i < jsonMinfo.length(); i++) {
                JSONObject futureJson = jsonMinfo.getJSONObject(i);

                String chat = futureJson.getString("chat");
                String time = futureJson.getString("time");
                String name = futureJson.getString("name");

                ChatInfo futureInfo = new ChatInfo(chat, time, name);
                chatList.add(futureInfo);
            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
