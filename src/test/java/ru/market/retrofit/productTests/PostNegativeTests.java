package ru.market.retrofit.productTests;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.*;
import retrofit2.Response;
import ru.market.retrofit.category.enums.CategoryType;
import ru.market.retrofit.dto.Error;
import ru.market.retrofit.dto.Product;
import ru.market.retrofit.service.ProductService;
import ru.market.retrofit.util.ProductSqlSession;
import ru.market.retrofit.util.RetrofitUtils;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PostNegativeTests {
    static ProductService productService;
    Product product;
    Faker faker = new Faker();
    ProductSqlSession productSqlSession = new ProductSqlSession();

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit().create(ProductService.class);
    }

    @SneakyThrows
    @DisplayName("postProduct негативный тест c указанием id")
    @Test
    void postProductNegativeTest() {

        product = new Product()
                .withCategoryTitle(CategoryType.FOOD.getTitle())
                .withPrice((int) (Math.random() * 1000 + 1))
                .withTitle(faker.food().ingredient())
                .withId((long) (Math.random() * 1000 + 1));
        Long productId = product.getId();

        Response<Product> response = productService
                .postProduct(product).execute();

        assertThat(response.code()).as("Статус не 400").isEqualTo(400);
        assertThat(productSqlSession.productMapper().selectByPrimaryKey(productId))
                .as("Продукт все равно создался").isEqualTo(null);
    }

    @SneakyThrows
    @DisplayName("postProduct негативный тест Минусовая цена")
    @Test
    void postProductNegative2Test() {

        product = new Product()
                .withCategoryTitle(CategoryType.FOOD.getTitle())
                .withPrice((int) (Math.random() * -1000 + 1))
                .withTitle(faker.food().ingredient());
        Long productId = product.getId();

        Response<Product> response = productService
                .postProduct(product).execute();

        assertThat(response.code()).as("Статус не 400").isEqualTo(400);
        assertThat(productSqlSession.productMapper().selectByPrimaryKey(productId))
                .as("Продукт создался c минусовой ценой").isEqualTo(null);
    }

    @SneakyThrows
    @DisplayName("postProduct негативный тест без названия")
    @Test
    void postProductNegative3Test() {

        product = new Product()
                .withCategoryTitle(CategoryType.FOOD.getTitle())
                .withPrice((int) (Math.random() * 1000 + 1));

        Long productId = product.getId();

        Response<Product> response = productService
                .postProduct(product).execute();

        assertThat(response.code()).as("Статус не 400").isEqualTo(400);
        assertThat(productSqlSession.productMapper().selectByPrimaryKey(productId))
                .as("Продукт создался без названия").isEqualTo(null);
    }

    @SneakyThrows
    @DisplayName("postProduct негативный тест без категории")
    @Test
    void postProductNegative4Test() {

        product = new Product()
                .withPrice((int) (Math.random() * 1000 + 1))
                .withTitle(faker.food().ingredient());

        Long productId = product.getId();
        Response<Product> response = productService
                .postProduct(product).execute();
        assertThat(response.code()).as("Статус не 400").isEqualTo(400);
        assertThat(productSqlSession.productMapper().selectByPrimaryKey(productId))
                .as("Продукт создался без категории").isEqualTo(null);
    }

    @SneakyThrows
    @DisplayName("postProduct негативный тест только c id")
    @Test
    void postProductNegative5Test() {

        product = new Product();
        Long productId = product.getId();
        Response<Product> response = productService
                .postProduct(product).execute();
        assertThat(response.code()).as("Статус не 404").isEqualTo(404);
        assertThat(productSqlSession.productMapper().selectByPrimaryKey(productId))
                .as("Продукт создался только c id").isEqualTo(null);
    }
}


