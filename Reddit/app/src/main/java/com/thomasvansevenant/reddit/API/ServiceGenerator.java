package com.thomasvansevenant.reddit.API;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * The ServiceGenerator is our API/HTTP client heart.
 * In its current state, it only defines one method to
 * create a basic REST adapter for a given class/interface.
 */
public class ServiceGenerator {
    private static final String LOG_TAG = ServiceGenerator.class.getSimpleName();
    public static final String API_BASE_URL = "https://www.reddit.com";

    private static RestAdapter.Builder builder = new RestAdapter.Builder()
            .setEndpoint(API_BASE_URL)
            .setClient(new OkClient(new OkHttpClient()))
            .setConverter(
                    new GsonConverter(gsonBuilder()));

    public static <S> S createService(Class<S> serviceClass) {

        RestAdapter adapter = builder.build();
        return adapter.create(serviceClass);
    }

    private static Gson gsonBuilder() {
        Gson gson = new GsonBuilder()
                //This helps you avoid annotating every single field in your model.
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(ArrayList.class, new RedditDeserializer())
                .create();

        return gson;
    }
}
