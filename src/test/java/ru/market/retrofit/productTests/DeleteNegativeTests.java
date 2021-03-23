package ru.market.retrofit.productTests;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.*;
import retrofit2.Response;
import ru.market.retrofit.category.enums.CategoryType;
import ru.market.retrofit.dto.Product;
import ru.market.retrofit.service.ProductService;
import ru.market.retrofit.util.RetrofitUtils;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DeleteNegativeTests {
    static ProductService productService;
    Long productId = 10000L;

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit().create(ProductService.class);
    }

    @DisplayName("deleteProduct негативный тест по несуществующему Id")
    @Test
    void DeleteProductNegativeTests() {

        try {
            Response<ResponseBody> response =

                    productService.deleteProduct(productId)
                            .execute();
            assertThat(response.errorBody().string());
            assertThat(response.code()).as("Статус ответа не 204").isEqualTo(204);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
