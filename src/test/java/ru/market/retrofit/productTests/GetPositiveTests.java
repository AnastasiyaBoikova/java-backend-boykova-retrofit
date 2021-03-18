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

public class GetPositiveTests {
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
    @BeforeEach
    void setUp() {
        product = new Product()
                .withCategoryTitle(CategoryType.FOOD.getTitle())
                .withPrice((int) (Math.random() * 1000 + 1))
                .withTitle(faker.food().ingredient());

        Response<Product> response = productService
                .postProduct(product).execute();
        productId = response.body().getId();
        assertThat(response.code()).as("Продукт не создался").isEqualTo(201);
        productPrice = response.body().getPrice();
        productTitle = response.body().getTitle();
        categoryTitle = response.body().getCategoryTitle();
    }

    @SneakyThrows
    @DisplayName("getProduct позитивный тест")
    @Test
    void GetProductTests() {
        Response<Product> response = productService
                .getProduct(productId).execute();
        assertThat(response.code()).as("Продукт не найден").isEqualTo(200);
        assertThat(response.body().getTitle()).as("Тип товара не верный.").isEqualTo(productTitle);
        assertThat(response.body().getPrice()).as("Цена не верная").isEqualTo(productPrice);
        assertThat(response.body().getCategoryTitle()).as("Категория не верная.").isEqualTo(categoryTitle);


    }

    @SneakyThrows
    @AfterEach
    void tearDown() {

        try {
            Response<ResponseBody> response =
                    productService.deleteProduct(productId)
                            .execute();
            assertThat(response.isSuccessful()).isTrue();
            assertThat(response.code()).as("Продукт не удален").isEqualTo(200);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
