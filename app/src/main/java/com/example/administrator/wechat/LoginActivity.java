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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_user;
    private EditText et_password;

    private Button btn_login;
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
                        String user = jsonObj.optString("user");
                        String name = jsonObj.optString("name");
                        if (status.equals("登陆成功")){
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            intent.putExtra("user",user);
                            intent.putExtra("password",et_password.getText().toString());
                            startActivity(intent);
                        }else {
                            Toast.makeText(LoginActivity.this, "账号或密码错误，请重新输入", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.login_main);
        et_user = (EditText) findViewById(R.id.et_user);
        et_password = (EditText) findViewById(R.id.et_password);
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_add).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String user = et_user.getText().toString();
                String password = et_password.getText().toString();

                System.out.println(user);
                System.out.println(password);
                new Thread()
                {
                    public void run()
                    {
                        JSONObject response = null;
                        try{
                            HttpClient httpClient = new DefaultHttpClient();
                            HttpPost httpPost = new HttpPost("http://123.207.85.214/chat/login.php");
                            List<NameValuePair> params = new ArrayList<NameValuePair>();
                            params.add(new BasicNameValuePair("user", et_user.getText().toString()));
                            params.add(new BasicNameValuePair("password", et_password.getText().toString()));
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
            case R.id.btn_add:

                Intent intent = new Intent(this,Adduser.class);

                startActivity(intent);

                break;

        }
    }



}
