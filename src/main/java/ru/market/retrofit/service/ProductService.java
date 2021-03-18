package ru.market.retrofit.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;
import ru.market.retrofit.dto.Category;
import ru.market.retrofit.dto.Product;

public interface ProductService {

    @GET("products")
    Call<Product> getProductAll();

    @GET("products/{id}")
    Call<Product> getProduct(@Path("id") int id);

    @POST("products")
    Call<Product> postProduct(@Body Product createProductRequest);

    @DELETE("products/{id}")
    Call<ResponseBody> deleteProduct(@Path("id") int id);

    @PUT("products")
    Call<Product> putProduct(@Body Product createProductRequest);

}
