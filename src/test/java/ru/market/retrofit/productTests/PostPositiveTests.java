package ru.market.retrofit.productTests;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.*;
import retrofit2.Response;
import ru.market.retrofit.category.enums.CategoryType;
import ru.market.retrofit.dto.Product;
import ru.market.retrofit.service.ProductService;
import ru.market.retrofit.util.ProductSqlSession;
import ru.market.retrofit.util.RetrofitUtils;
import java.io.IOException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PostPositiveTests {
    static ProductService productService;
    Product product;
    Faker faker = new Faker();
    static Long productId;
    ProductSqlSession productSqlSession = new ProductSqlSession();

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit().create(ProductService.class);
    }

    @BeforeEach
    void setUp() {
        product = new Product()
                .withCategoryTitle(CategoryType.FOOD.getTitle())
                .withPrice((int) (Math.random() * 1000 + 1))
                .withTitle(faker.food().ingredient());

    }

    @SneakyThrows
    @DisplayName("postProduct позитивный тест")
    @Test
    void postProductPositiveTest() {

        Response<Product> response = productService
                .postProduct(product).execute();
        productId = response.body().getId();
        assertThat(response.code()).as("Статус не 201").isEqualTo(201);
        assertThat(response.body().getId()).as("id не присвоилось").isNotNull();
        assertThat(productSqlSession.productMapper().selectByPrimaryKey(productId))
                .as("продукт не создался").isNotNull();
    }

    @AfterEach
    void tearDown() {
//        try {
//            Response<ResponseBody> response =
//                    productService.deleteProduct(productId)
//                            .execute();
//            assertThat(response.isSuccessful()).isTrue();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        productSqlSession.productMapper().deleteByPrimaryKey(productId);
        assertThat(productSqlSession.productMapper().selectByPrimaryKey(productId))
                .as("Тестовый продукт не удалился").isEqualTo(null);
    }
}


