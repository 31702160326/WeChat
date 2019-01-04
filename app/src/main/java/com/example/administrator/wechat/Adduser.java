package com.example.administrator.wechat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Adduser extends AppCompatActivity implements View.OnClickListener {
    private EditText et_user;
    private EditText et_name;
    private EditText et_password;
    private Button btn_add;

    //    private GoogleApiClient client;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case 1:
                    // 更新UI以显示获取到的网络数据
                    String result = msg.obj.toString();
                    JSONObject jsonObj = null;
                    try {
                        jsonObj = new JSONObject(result);
                        String status = jsonObj.optString("status");
                        if (status.equals("注册成功")){
                            Toast.makeText(Adduser.this, status, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Adduser.this,LoginActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(Adduser.this, "注册失败", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
            }

        }

    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adduser);
        et_user = (EditText) findViewById(R.id.et_user);
        et_password = (EditText) findViewById(R.id.et_password);
        et_name = (EditText) findViewById(R.id.et_name);
        findViewById(R.id.btn_add).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add :
//                String user=et_user.getText().toString();
//                String password= et_password.getText().toString();
//                String name= et_name.getText().toString();


                new Thread()
                {
                    public void run()
                    {
                        JSONObject response = null;
                        try{
                            HttpClient httpClient = new DefaultHttpClient();
                            HttpPost httpPost = new HttpPost("http://123.207.85.214/chat/register.php");
                            List<NameValuePair> params = new ArrayList<NameValuePair>();
                            params.add(new BasicNameValuePair("user", et_user.getText().toString()));
                            params.add(new BasicNameValuePair("password", et_password.getText().toString()));
                            params.add(new BasicNameValuePair("name", et_name.getText().toString()));
                            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,"UTF-8");
                            httpPost.setEntity(entity);
                            HttpResponse httpResponse =  httpClient.execute(httpPost);
                            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                                HttpEntity entity2 = httpResponse.getEntity();
                                String result = EntityUtils.toString(entity2, "utf-8");


                                Message m = new Message();
                                m.what = 1;
                                m.obj = result;
                                handler.sendMessage(m);
                            }
                        }catch(Exception e){e.printStackTrace();}
                    };
                }.start();
                break;
        }
    }



}
