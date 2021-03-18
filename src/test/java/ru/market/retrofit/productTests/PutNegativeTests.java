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

public class PutNegativeTests {
    static ProductService productService;
    Product product;
    Faker faker = new Faker();
    static int productId;
    static int productPrice;
    static String productTitle;
    static String categoryTitle;


    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit().create(ProductService.class);
    }


    @SneakyThrows
    @DisplayName("putProduct негативный тест")
    @Test
    void PutProductNegativeTests() {

        product = new Product()
                .withId(10000)
                .withCategoryTitle(CategoryType.FOOD.getTitle())
                .withPrice((int) (Math.random() * 1000 + 1))
                .withTitle(faker.food().ingredient());

        Response<Product> response = productService
                .putProduct(product).execute();

        assertThat(response.code()).as("Статус не 404").isEqualTo(404);

    }

}


