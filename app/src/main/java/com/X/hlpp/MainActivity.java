package com.X.hlpp;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TResult;

import org.json.JSONObject;

public class MainActivity extends TakePhotoActivity {

    private WebView web;
    Handler handlers = new Handler();


    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface","NewApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

                return false;

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String u) {

                String url = u.toLowerCase();
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
                        HtmlMsgModel obj = new Gson().fromJson(str,HtmlMsgModel.class);
                        handleMsg(obj);
                    }
                });

            }

        }, "android");

        String title = getIntent().getStringExtra("title");
        String url = getIntent().getStringExtra("url");

        title = "河洛泡泡";
        url= "http://192.168.1.105/activity_wap/index.html";


        web.loadUrl(url);


    }

    private void handleMsg(HtmlMsgModel obj)
    {
        System.out.println(obj.toString());

        getTakePhoto().onPickMultiple(9);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);

        XNetUtil.APPPrintln(result);

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
}
