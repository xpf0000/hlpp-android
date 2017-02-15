package com.X.hlpp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.bigkoo.alertview.OnItemClickListener;
import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.X.hlpp.LocationApplication.APPService;

public class MainActivity extends TakePhotoActivity {

    private WebView web;
    Handler handlers = new Handler();
    AlertView alertView;

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface","NewApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ShareSDK.initSDK(this,"androidv1101");

        web = (WebView) findViewById(R.id.web);

        // 设置支持JavaScript等
        WebSettings mWebSettings = web.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebSettings.setDomStorageEnabled(false);
        mWebSettings.setDatabaseEnabled(false);
        mWebSettings.setGeolocationEnabled(false);
        mWebSettings.setAppCacheEnabled(false);

        web.setWebViewClient(new WebViewClient(){


            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {

                return super.shouldInterceptRequest(view, request);

            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {

                return super.shouldInterceptRequest(view, url);


            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                String url = request.getUrl().toString().toLowerCase();

                if(!url.contains("http://") && !url.contains("https://"))
                {
                    view.stopLoading();
                }


                return false;

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String u) {

                String url = u.toLowerCase();

                if(!url.contains("http://") && !url.contains("https://"))
                {
                    view.stopLoading();
                }

                return false;
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);


            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);


            }
        });

        web.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {

                if (progress == 100) {

                }
            }

        });
        web.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void runAndroidMethod(final String str) {
                handlers.post(new Runnable() {

                    @Override
                    public void run() {

                        XNetUtil.APPPrintln("str: "+str);

                        HtmlMsgModel obj = new Gson().fromJson(str,HtmlMsgModel.class);

                        XNetUtil.APPPrintln("obj: "+obj.toString());

                        handleMsg(obj);
                    }
                });

            }

        }, "android");

        String title = getIntent().getStringExtra("title");
        String url = getIntent().getStringExtra("url");

        title = "河洛泡泡";
        url= "http://192.168.1.103/activity_wap/index.html";


        web.loadUrl(url);

        alertView = new AlertView("选择图片", null, "取消", null,
                new String[]{"拍照", "从相册中选择"},
                this, AlertView.Style.ActionSheet, new OnItemClickListener(){
            public void onItemClick(Object o,int p){

                position = p;

            }
        });

        File file = new File(getExternalFilesDir(""), "temp.jpg");
        final Uri uri = Uri.fromFile(file);

        alertView.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(Object o) {
                if(position == 0)
                {
                    getTakePhoto().onPickFromCapture(uri);
                }
                else if(position == 1)
                {
                    if(mutiple)
                    {
                        getTakePhoto().onPickMultiple(9);
                    }
                    else
                    {
                        getTakePhoto().onPickFromGallery();
                    }

                }
            }
        });


    }

    private int position = -1;
    private boolean mutiple = false;
    private HtmlMsgModel htmlMsg = new HtmlMsgModel();
    private void handleMsg(HtmlMsgModel obj)
    {
        htmlMsg = obj;
        System.out.println(obj.toString());

        if(obj.getType().equals("0") && obj.getMsg().equals("选择多张图片"))
        {
            mutiple = true;
            alertView.show();
        }
        else if(obj.getType().equals("0") && obj.getMsg().equals("选择单张图片"))
        {
            mutiple = false;
            alertView.show();
        }
        else if(obj.getType().equals("0") && obj.getMsg().equals("选择封面图片"))
        {
            mutiple = false;
            alertView.show();
        }
        else if(obj.getType().equals("1") && obj.getMsg().equals("分享到微信朋友圈"))
        {

        }
        else if(obj.getType().equals("1") && obj.getMsg().equals("分享到微信好友"))
        {

        }
        else if(obj.getType().equals("1") && obj.getMsg().equals("分享到QQ空间"))
        {
            QZone.ShareParams sp = new QZone.ShareParams();
            sp.setTitle(obj.getTitle());
            sp.setTitleUrl(obj.getUrl()); // 标题的超链接
            sp.setText(obj.getTitle());
            sp.setImageUrl(obj.getPic());
            sp.setSite("河洛泡泡");
            sp.setSiteUrl("http://www.ihlpp.cn");

            Platform qzone = ShareSDK.getPlatform (QZone.NAME);
// 设置分享事件回调（注：回调放在不能保证在主线程调用，不可以在里面直接处理UI操作）
            qzone.setPlatformActionListener (new PlatformActionListener() {
                public void onError(Platform arg0, int arg1, Throwable arg2) {
                    //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息

                    XNetUtil.APPPrintln(arg2.toString());
                    XActivityindicator.create(MainActivity.this).showErrorWithStatus(arg2.toString());

                }
                public void onComplete(Platform arg0, int arg1, HashMap arg2) {
                    //分享成功的回调
                    XActivityindicator.create(MainActivity.this).showSuccessWithStatus("分享成功");
                }
                public void onCancel(Platform arg0, int arg1) {
                    //取消分享的回调
                }
            });
// 执行图文分享
            qzone.share(sp);
        }
        else if(obj.getType().equals("1") && obj.getMsg().equals("分享到QQ好友"))
        {
            QQ.ShareParams sp = new QQ.ShareParams();
            sp.setTitle(obj.getTitle());
            sp.setTitleUrl(obj.getUrl()); // 标题的超链接
            sp.setText(obj.getTitle());
            sp.setImageUrl(obj.getPic());
            sp.setSite("河洛泡泡");
            sp.setSiteUrl("http://www.ihlpp.cn");

            Platform qq = ShareSDK.getPlatform (QQ.NAME);
// 设置分享事件回调（注：回调放在不能保证在主线程调用，不可以在里面直接处理UI操作）
            qq.setPlatformActionListener (new PlatformActionListener() {
                public void onError(Platform arg0, int arg1, Throwable arg2) {
                    //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息

                    XNetUtil.APPPrintln(arg2.toString());
                    XActivityindicator.create(MainActivity.this).showErrorWithStatus(arg2.toString());

                }
                public void onComplete(Platform arg0, int arg1, HashMap arg2) {
                    //分享成功的回调
                    XActivityindicator.create(MainActivity.this).showSuccessWithStatus("分享成功");
                }
                public void onCancel(Platform arg0, int arg1) {
                    //取消分享的回调
                }
            });
// 执行图文分享
            qq.share(sp);
        }

    }



    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);

        XNetUtil.APPPrintln(htmlMsg.getUid());
        XNetUtil.APPPrintln(htmlMsg.getUsername());

        Map<String , RequestBody> params = new HashMap<>();
        params.put("uid",createBody(htmlMsg.getUid()));
        params.put("username",createBody(htmlMsg.getUsername()));


        int i = 0;
        for(TImage tImage : result.getImages())
        {
            String path = tImage.getOriginalPath();
            XNetUtil.APPPrintln(path);
            Bitmap bitmap= BitmapFactory.decodeFile(path);
            XNetUtil.APPPrintln(bitmap);
            params.put("file[]\"; filename=\"xtest"+i+".jpg",createBody(bitmap));
            i++;
        }

        XActivityindicator.create(this).show();

        XNetUtil.Handle(APPService.articleAddPic(params), new XNetUtil.OnHttpResult<List<ImageModel>>() {
            @Override
            public void onError(Throwable e) {
                XActivityindicator.hide();
            }

            @Override
            public void onSuccess(List<ImageModel> imageModels) {

                for(ImageModel model : imageModels)
                {
                    XNetUtil.APPPrintln("ImageModel: "+model.toString());
                }

                String s2 = new Gson().toJson(imageModels);

                if(htmlMsg.getMsg().equals("选择封面图片"))
                {
                    web.loadUrl("javascript:publishVM.coverUpSuccess("+s2+")");
                }
                else
                {
                    web.loadUrl("javascript:publishVM.upsuccess("+s2+")");
                }

                XActivityindicator.hide();

            }
        });



    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
        XNetUtil.APPPrintln(msg);
    }

    @Override
    public void takeCancel() {
        super.takeCancel();

    }


    private RequestBody createBody(Object obj)
    {
        if(obj instanceof String)
        {
            return RequestBody.create(MediaType.parse("text/plain"), (String) obj);
        }
        else
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ((Bitmap)obj).compress(Bitmap.CompressFormat.JPEG, 50, baos);
            return RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray());
        }
    }


    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {

            XNetUtil.APPPrintln(web.getUrl());

            if(web.getUrl().toLowerCase().contains("index.html"))
            {
                isExit = true; // 准备退出
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                tExit = new Timer();
                tExit.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isExit = false; // 取消退出
                    }
                }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
            }
            else
            {
               web.goBack();
            }


        } else {

            finish();

        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click(); // 调用双击退出函数
        }
        return false;
    }
}
