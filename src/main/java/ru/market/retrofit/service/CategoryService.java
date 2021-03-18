package ru.market.retrofit.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import ru.market.retrofit.dto.Category;

public interface CategoryService {

    @GET ("categories/{id}")
    Call<Category> getCategory (@Path ("id") int id);
}
