package com.X.hlpp;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by X on 2016/10/1.
 */

public interface ServicesAPI {

 String APPUrl = "http://182.92.70.85/hfapi/Public/Found/";

 @GET("?service=User.getOrNickname")  //检测昵称是否重复
 Observable<HttpResult<Object>> userGetOrNickname(@Query("nickname") String nickname);

}


