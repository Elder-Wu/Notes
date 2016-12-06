package com.wuzhanglao.niubi.utils;

import com.wuzhanglao.niubi.mvp.model.HeWeatherBean;
import com.wuzhanglao.niubi.mvp.model.ShanbayResp;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
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
    String BASE_URL_SHANBAY = "https://api.shanbay.com/";

    class Factory {
        public static final NetworkService create(String baseUrl) {
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logInterceptor)//日志拦截器
                    .addNetworkInterceptor(new CacheInterceptor())
                    .connectTimeout(15, TimeUnit.SECONDS)//设置连接超时
                    .retryOnConnectionFailure(false)
                    .cache(new Cache(MyApplication.getInstance().getCacheDir(), 1024 * 1024))
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

    class CacheInterceptor implements Interceptor {

        private int retryCount = 3;

        @Override
        public Response intercept(Chain chain) throws IOException {
            // TODO: 2016/11/9 可以进行网络重连接等操作
            //@link 这篇博客写的很详细:http://www.jianshu.com/p/faa46bbe8a2e
            Request request = chain.request();
            Response response = chain.proceed(request);
            if (response.isSuccessful()) {
                response.newBuilder()
                        .removeHeader("Cache-Control")
                        //缓存保留时间(30天)
                        .header("Cache-Control", "max-age=" + 3600 * 24 * 30)
                        .build();
            }
            return response;
        }
    }

    @GET("x3/weather")
    Observable<HeWeatherBean> getWeatherService(@Query("city") String city, @Query("key") String key);

    @GET("x3/attractions")
    Observable<HeWeatherBean> getScenicService(@Query("cityid") String cityid, @Query("key") String key);

    @GET("bdc/search")
    Observable<ShanbayResp> getTranslation(@Query("word") String word);
}
