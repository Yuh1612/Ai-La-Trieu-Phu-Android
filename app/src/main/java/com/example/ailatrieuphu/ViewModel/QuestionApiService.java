package com.example.ailatrieuphu.ViewModel;

import com.example.ailatrieuphu.Model.Question;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionApiService {
    private static final String BASE_URL = "";
    private QuestionApi questionApi;

    public  QuestionApiService(){
        questionApi = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(QuestionApi.class);
    }
    public Single<List<Question>> getQuestions() { return questionApi.getQuestions();}
}
