package com.figengungor.bakingapp_udacity.data.remote;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by figengungor on 4/17/2018.
 */

public class BakingServiceFactory {

    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net";
    private static final int HTTP_READ_TIMEOUT = 10;
    private static final int HTTP_CONNECT_TIMEOUT = 6;

    public static BakingService createService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(createOkHttpClient())
                .build();

        return retrofit.create(BakingService.class);
    }

    private static OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient().newBuilder();
        httpClientBuilder.connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.addInterceptor(createLoggingInterceptor());
        return httpClientBuilder.build();
    }

    private static HttpLoggingInterceptor createLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return logging;
    }

}
