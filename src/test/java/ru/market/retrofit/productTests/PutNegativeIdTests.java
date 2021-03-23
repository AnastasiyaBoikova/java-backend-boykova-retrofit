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

public class PutNegativeIdTests {
    static ProductService productService;
    Product product;
    Faker faker = new Faker();
    ProductSqlSession productSqlSession = new ProductSqlSession();


    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit().create(ProductService.class);
    }


    @SneakyThrows
    @DisplayName("putProduct негативный тест id не существует")
    @Test
    void PutProductNegativeTests() {

        product = new Product()
                .withId(10000L)
                .withCategoryTitle(CategoryType.FOOD.getTitle())
                .withPrice((int) (Math.random() * 1000 + 1))
                .withTitle(faker.food().ingredient());
        Long prodId = product.getId();

        Response<Product> response = productService
                .putProduct(product).execute();

        assertThat(response.code()).as("Статус не 404").isEqualTo(404);
        assertThat(productSqlSession.productMapper().selectByPrimaryKey(prodId))
                .as("Создался новый продукт по несуществуещему id").isNotNull();

    }

}


