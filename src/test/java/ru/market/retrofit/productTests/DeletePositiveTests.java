package ru.market.retrofit.productTests;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.*;
import retrofit2.Response;
import ru.geekbrains.java4.lesson6.db.model.Products;
import ru.market.retrofit.category.enums.CategoryType;
import ru.market.retrofit.dto.Product;
import ru.market.retrofit.service.ProductService;
import ru.market.retrofit.util.ProductSqlSession;
import ru.market.retrofit.util.RetrofitUtils;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DeletePositiveTests {
    static ProductService productService;
    Product product;
    Faker faker = new Faker();
    static Long productId;
    ProductSqlSession productSqlSession = new ProductSqlSession();

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
//        assertThat(productSqlSession.productMapper().selectByPrimaryKey(productId).getTitle())
//                .as("Продукт не создался").isEqualTo(CategoryType.FOOD.getTitle());

    }
    @DisplayName("deleteProduct позитивный тест")
    @Test
    void DeleteProductTests() {

        try {
            Response<ResponseBody> response =
                    productService.deleteProduct(productId)
                            .execute();
            assertThat(response.isSuccessful()).isTrue();
            assertThat(response.code()).as("Статус не 200").isEqualTo(200);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @SneakyThrows
    @AfterEach
    void tearDown() {
//        Response<Product> response = productService
//                .getProduct(productId).execute();
//        assertThat(response.code()).as("Продукт не удалился").isEqualTo(404);
        assertThat(productSqlSession.productMapper().selectByPrimaryKey(productId)).isEqualTo(null);

    }
}
