package com.example.administrator.day43_testhomework.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.day43_testhomework.R;
import com.example.administrator.day43_testhomework.adapter.MyViewPagerAdapter;
import com.example.administrator.day43_testhomework.fragment.FragmentOne;
import com.example.administrator.day43_testhomework.fragment.FragmentThree;
import com.example.administrator.day43_testhomework.fragment.FragmentTwo;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class MainActivity extends AppCompatActivity{

    private Toolbar toolBar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private List<Fragment> fragments;
    private MyViewPagerAdapter myViewPagerAdapter;

    public static final int TAKE_ALBUM=0;
    public static final int CROP=1;
    private ImageView circleImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setSupportActionBar(toolBar);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolBar, R.string.app_name,R.string.app_name);

        toggle.syncState();

        drawerLayout.setDrawerListener(toggle);

        //初始化viewPager
        initViewPager();
    }

    private void initViewPager() {
        fragments=new ArrayList<>();
        fragments.add(new FragmentOne());
        fragments.add(new FragmentTwo());
        fragments.add(new FragmentThree());

        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(myViewPagerAdapter);

        viewPager.setOffscreenPageLimit(2);

        tabLayout.setupWithViewPager(viewPager);
    }

    private void initView() {
        toolBar = ((Toolbar) findViewById(R.id.activity_main_toolBarId));
        drawerLayout = ((DrawerLayout) findViewById(R.id.activity_main_drawerLayoutId));
        tabLayout = ((TabLayout) findViewById(R.id.app_drawer_main_tabLayoutId));
        viewPager = ((ViewPager) findViewById(R.id.app_drawer_main_viewPagerId));
        circleImage = ((ImageView) findViewById(R.id.activity_main_imageId));

        circleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent intent=new Intent(Intent.ACTION_PICK);
            //设置数据路径和类型
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
            startActivityForResult(intent,TAKE_ALBUM);
            }
        });

    }

    //截取图片
    public void cropImage(Uri uri, int outputX, int outputY, int requestCode){
        //裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        //裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        //图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==TAKE_ALBUM&&resultCode==RESULT_OK){
            //获取图片路径
            Uri uri = data.getData();
            Log.d("1608", "---------------->onActivityResult: "+uri);
            Log.d("1608", "---------------->onActivityResult: "+uri.toString());
            //先进行裁剪
            cropImage(uri,200,200,CROP);

        }else if(requestCode==CROP&&resultCode==RESULT_OK){
            Uri uri = data.getData();
            Bitmap bitmap;
            if (uri!=null){
                bitmap = BitmapFactory.decodeFile(uri.toString());
            }else{
                bitmap= (Bitmap) data.getExtras().get("data");
            }

            if (bitmap!=null){
                circleImage.setImageBitmap(bitmap);
            }
        }
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_main_share:
                showShare();
                break;
            case R.id.activity_main_push:
                //初始化
                JPushInterface.setDebugMode(true);
                JPushInterface.init(this);

                break;
        }
    }

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
//关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
//oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("标题");
// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
// text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
//分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
// site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK();
    }
}
