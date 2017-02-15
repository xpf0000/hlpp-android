package com.X.hlpp;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by X on 2016/10/1.
 */

public interface ServicesAPI {

 //String APPUrl = "http://182.92.70.85/hlppapi/Public/Found/";
 String APPUrl = "https://api.ihlpp.com/Public/Found/";
 @GET("?service=User.getOrNickname")  //检测昵称是否重复
 Observable<HttpResult<Object>> userGetOrNickname(@Query("nickname") String nickname);

 @Multipart
 @POST("?service=Article.addPic")  //上传图片
 Observable<HttpResult<List<ImageModel>>> articleAddPic(@PartMap Map<String , RequestBody> params);

}


