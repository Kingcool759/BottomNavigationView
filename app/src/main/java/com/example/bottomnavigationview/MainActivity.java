package com.example.bottomnavigationview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.bottomnavigationview.fragment.HomeFragment;
import com.example.bottomnavigationview.fragment.ProjectFragment;
import com.example.bottomnavigationview.fragment.SettingFragment;
import com.example.bottomnavigationview.fragment.SystemFragment;
import com.example.bottomnavigationview.fragment.WeChatFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Fragment[] mFragments = new Fragment[5];
    private BottomNavigationView mBottomNav;
    private int mPreFragmentFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initFragment();
        selectFragment();
    }

    private void initView() {
        mBottomNav = findViewById(R.id.mBottomNav);

    }

    private void initFragment() {


        mFragments[0] = new HomeFragment();
        mFragments[1] = new WeChatFragment();
        mFragments[2] = new ProjectFragment();
        mFragments[3] = new SystemFragment();
        mFragments[4] = new SettingFragment();
        initLoadFragment(R.id.mContainerView, 0, mFragments);
    }

    // 参数一 是一个FrameLayout的ID，用来动态加载Fragment，
    private void initLoadFragment(int containerId, int showFragment, Fragment... fragments) {
        //获取到FragmentManager实例的同时去开启事物
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < fragments.length; i++) {
            //首先将Fragment添加到事务中
            transaction.add(containerId, fragments[i], fragments[i].getClass().getName());
            //默认展示 fragments[showFragment]
            //这里做首次Fragment的展示，如果不是指定的Fragment就先隐藏，需要的时候再显示出来
            if (i != showFragment)
                transaction.hide(fragments[i]);
        }
        //提交事物
        transaction.commitAllowingStateLoss();

    }

    private void selectFragment() {
        //注册监听事件
        mBottomNav.setItemIconTintList(null);
        mBottomNav.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.home:
                    showAndHideFragment(mFragments[0], mFragments[mPreFragmentFlag]);
                    mPreFragmentFlag = 0;
                    break;
                case R.id.wechat:
                    showAndHideFragment(mFragments[1], mFragments[mPreFragmentFlag]);
                    mPreFragmentFlag = 1;
                    break;
                case R.id.project:
                    showAndHideFragment(mFragments[2], mFragments[mPreFragmentFlag]);
                    mPreFragmentFlag = 2;
                    break;
                case R.id.system:
                    showAndHideFragment(mFragments[3], mFragments[mPreFragmentFlag]);
                    mPreFragmentFlag = 3;
                    break;
                case R.id.setting:
                    showAndHideFragment(mFragments[4], mFragments[mPreFragmentFlag]);
                    mPreFragmentFlag = 4;
                    break;
            }
            return true;
        });
    }

    //加载不同的Fragment
    private void showAndHideFragment(Fragment show, Fragment hide) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (show != hide)
            transaction.show(show).hide(hide).commitAllowingStateLoss();

    }
}