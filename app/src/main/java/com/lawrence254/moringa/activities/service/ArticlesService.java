package com.lawrence254.moringa.activities.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lawrence254.moringa.activities.Constants;
import com.lawrence254.moringa.activities.models.Article;
import com.lawrence254.moringa.activities.models.Articles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ArticlesService {
    private static OkHttpClient client = new OkHttpClient();
    public static void getArticle(Callback callback) {
        HttpUrl.Builder builder = HttpUrl.parse(Constants.ARTICLES_URL).newBuilder();

        String url = builder.build().toString();
//        Log.d("URL FOR ", "findNews is: "+url);

        Request request = new Request.Builder().url(url).build();

        Call call = client.newCall(request);

        call.enqueue(callback);

    }

    public static ArrayList<Article> processArticles(Response response){
        ArrayList<Article> article = new ArrayList<>();

        try {
            String json = response.body().string();

            if (response.isSuccessful()){
                JSONObject jsonObject = new JSONObject(json);
                JSONArray newsJson = jsonObject.getJSONArray("articles");
                Type collectionType = new TypeToken<List<Article>>() {}.getType();
                Gson gson = new GsonBuilder().create();
                article = gson.fromJson(newsJson.toString(), collectionType);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return article;
    }
}
