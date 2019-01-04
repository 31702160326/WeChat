package com.example.administrator.wechat;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 简书ID：天哥在奔跑
 * 原创Android教程：http://www.jianshu.com/p/9618c038135f
 * 教程答疑专用QQ群：667833258
 */
public class PhoneBookFragment extends Fragment {

    final OkHttpClient client = new OkHttpClient();
    ArrayList<UserInfo> userList;
    ListView lv;
        private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case 2:
                    // 更新UI以显示获取到的网络数据
                    String result = msg.obj.toString();
                    USER(result);
                    lv.setAdapter(new UserListAdapter(getActivity(),userList));
                    break;
            }

        }

    };




    public  void get_Y(String url,final Handler handler){
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(Call arg0, Response arg1) throws IOException {
                if(arg1.isSuccessful()){
                    String result=arg1.body().string();
                    Log.i("test","get异步请求成功，返回returns的值:"+ result);
                    Message msg2=new Message();
                    msg2.what=2;
                    msg2.obj=result;
                    handler.sendMessage(msg2);
                }
                else{
                    Log.i("test","get异步请求成功，返回NO:");
                }

            }

            @Override
            public void onFailure(Call arg0, IOException arg1) {
                Log.i("test","get异步请求失败"+ arg0.toString());

            }
        });



    }

    private void USER(String result) {
        try {
            JSONArray jsonMinfo = new JSONArray(result);
            //JSONObject resultJson = jsonMinfo.getJSONObject("result");

            //JSONArray WeatherArray = resultJson.getJSONArray("future");
            userList = new ArrayList<UserInfo>();
            for (int i = 0; i < jsonMinfo.length(); i++) {
                JSONObject futureJson = jsonMinfo.getJSONObject(i);

                String name = futureJson.getString("name");
                String user = futureJson.getString("user");

                UserInfo futureInfo = new UserInfo(name,user);
                userList.add(futureInfo);
            }


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }



    public PhoneBookFragment() {

    }

    public static PhoneBookFragment newInstance() {
        PhoneBookFragment fragment = new PhoneBookFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phone_book, container, false);
        lv = (ListView) view.findViewById(R.id.weatherlist);
        get_Y("http://123.207.85.214/chat/member.php",handler);
        return view;
    }

}
