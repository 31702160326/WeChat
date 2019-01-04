package com.example.administrator.wechat;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

/**
 * 简书ID：天哥在奔跑
 * 原创Android教程：http://www.jianshu.com/p/9618c038135f
 * 教程答疑专用QQ群：667833258
 */
public class MainActivity extends AppCompatActivity {
    final OkHttpClient client = new OkHttpClient();
    ArrayList<UserInfo> userList;
    ListView lv;

    private RadioGroup mRgTab;
    private List<Fragment> mFragmentList = new ArrayList<>();
//    private Handler handler = new Handler() {
//
//        @Override
//        public void handleMessage(Message msg) {
//            // TODO Auto-generated method stub
//            switch (msg.what) {
//                case 1:
//                    // 更新UI以显示获取到的网络数据
//                    String result = msg.obj.toString();
//                    USER(result);
//                    lv = (ListView) findViewById(R.id.weatherlist);
//                    lv.setAdapter(new UserListAdapter(getApplicationContext(),userList));
//                    break;
//            }
//
//        }
//
//    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRgTab = (RadioGroup) findViewById(R.id.rg_main);
        mRgTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        changeFragment(HomeFragment.class.getName());
                        break;
                    case R.id.rb_phone_book:
                        changeFragment(PhoneBookFragment.class.getName());
                       // getRequest();
                        break;
                    case R.id.rb_find:
                        changeFragment(FindFragment.class.getName());
                        break;
                    case R.id.rb_me:
                        changeFragment(MeFragment.class.getName());
                        break;
                }
            }
        });
        if(savedInstanceState == null){
            changeFragment(HomeFragment.class.getName());
        }
    }

    /**
     * show target fragment
     *
     * @param tag
     */
    public void changeFragment(String tag) {
        hideFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment fragment = getFragmentManager().findFragmentByTag(tag);
        if (fragment != null) {
            transaction.show(fragment);
        } else {
            if (tag.equals(HomeFragment.class.getName())) {
                Intent intent= getIntent();
                String login_user = intent.getStringExtra("user");
                String login_password = intent.getStringExtra("password");
                fragment = HomeFragment.newInstance(login_user,login_password);
            } else if (tag.equals(PhoneBookFragment.class.getName())) {
                fragment = PhoneBookFragment.newInstance();
            } else if (tag.equals(FindFragment.class.getName())) {
                fragment = FindFragment.newInstance();
            } else if (tag.equals(MeFragment.class.getName())) {
                fragment = MeFragment.newInstance();
            }
            mFragmentList.add(fragment);
            transaction.add(R.id.fl_container, fragment, fragment.getClass().getName());
        }
        transaction.commitAllowingStateLoss();

    }


    /**
     * hide all fragment
     */
    private void hideFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        for (Fragment f : mFragmentList) {
            ft.hide(f);
        }
        ft.commit();
    }

}
