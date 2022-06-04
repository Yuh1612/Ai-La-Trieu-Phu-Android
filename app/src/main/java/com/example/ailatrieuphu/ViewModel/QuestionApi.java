package com.example.ailatrieuphu.ViewModel;

import com.example.ailatrieuphu.Model.Question;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface QuestionApi {
    @GET("api/ques/getrand")
    public Single<List<Question>> getQuestions();
}
