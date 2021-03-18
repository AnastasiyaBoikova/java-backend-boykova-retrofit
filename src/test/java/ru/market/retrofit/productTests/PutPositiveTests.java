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

public class PutPositiveTests {
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
        productPrice = response.body().getPrice();
        productTitle = response.body().getTitle();
        categoryTitle = response.body().getCategoryTitle();

    }

    @SneakyThrows
    @DisplayName("putProduct позитивный тест")
    @Test
    void PutProductPositiveTests() {

        product = new Product()
                .withId(productId)
                .withCategoryTitle(CategoryType.FOOD.getTitle())
                .withPrice(productPrice / 2)
                .withTitle(productTitle);

        Response<Product> response = productService
                .putProduct(product).execute();

        assertThat(response.code()).as("Статус не 200").isEqualTo(200);
        assertThat(response.body().getCategoryTitle()).as("Во время Put изменилась категория.").isEqualTo(categoryTitle);
        assertThat(response.body().getId()).as("Во время Put изменился id.").isEqualTo(productId);
        assertThat(response.body().getTitle()).as("Во время Put изменился тип товара.").isEqualTo(productTitle);
        assertThat(response.body().getPrice()).as("Цена не изменилась").isNotEqualTo(product);

    }

    @AfterEach
    void tearDown() {
        try {
            Response<ResponseBody> response =
                    productService.deleteProduct(productId)
                            .execute();
            assertThat(response.isSuccessful()).isTrue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


