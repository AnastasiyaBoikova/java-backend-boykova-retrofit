package ru.market.retrofit.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ru.market.retrofit.dto.Category;
import ru.market.retrofit.dto.Product;

public interface ErrorService {

    @GET ("/market/api/v1/categories/{id}")
    Call<Error> getCategoriesError (@Path ("id") int id);

    @POST("products")
    Call<Product> postProductError (@Body Product createProductRequest);
}
