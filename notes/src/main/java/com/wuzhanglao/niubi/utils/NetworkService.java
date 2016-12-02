package com.wuzhanglao.niubi.utils;

import android.util.Log;

import com.wuzhanglao.niubi.mvp.model.HeBeiBeiBean;
import com.wuzhanglao.niubi.mvp.model.HeWeatherBean;
import com.wuzhanglao.niubi.mvp.model.ShanbayResp;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 包含了一系列的网络请求接口
 * Created by wuming on 16/10/19.
 */

public interface NetworkService {

    String BASE_URL_HeWeather = "https://api.heweather.com";
    String BASE_URL_HeBeiBei = "http://jiekou.hunlianquan.cn/";
    String BASE_URL_SHANBAY = "https://api.shanbay.com/";

    @GET("x3/weather")
    Observable<HeWeatherBean> getWeatherService(@Query("city") String city, @Query("key") String key);

    @GET("x3/attractions")
    Observable<HeWeatherBean> getScenicService(@Query("cityid") String cityid, @Query("key") String key);

    @POST("Home/Activity/account")
    Observable<HeBeiBeiBean> getHeBeiBeiDate(@Field("u_id") String u_id);

    @GET("bdc/search")
    Observable<ShanbayResp> getTranslation(@Query("word") String word);

    class Factory {
        public static final NetworkService create(String baseUrl) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)//日志拦截器
                    .addNetworkInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            // TODO: 2016/11/9 可以进行重连接等操作
                            //@link 这篇博客写的很详细:http://gold.xitu.io/entry/57b123497db2a200542a073b
                            Request request = chain.request();
                            Log.d("okhppt","request->"+request.toString());
                            return chain.proceed(chain.request());
                        }

                    })
                    .connectTimeout(15, TimeUnit.SECONDS)//设置连接超时
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())//json数据转换
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//将Callable接口转换成Observable接口
                    .client(client)//网络请求客户端为okhttp
                    .build();
            return retrofit.create(NetworkService.class);
        }
    }
}
