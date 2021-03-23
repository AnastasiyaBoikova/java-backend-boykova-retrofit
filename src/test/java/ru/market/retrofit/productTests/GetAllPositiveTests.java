package ru.market.retrofit.productTests;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import retrofit2.Response;
import ru.geekbrains.java4.lesson6.db.model.ProductsExample;
import ru.market.retrofit.category.enums.CategoryType;
import ru.market.retrofit.dto.Product;
import ru.market.retrofit.service.ProductService;
import ru.market.retrofit.util.ProductSqlSession;
import ru.market.retrofit.util.RetrofitUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GetAllPositiveTests {
    static ProductService productService;

    ProductSqlSession productSqlSession = new ProductSqlSession();

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtils.getRetrofit().create(ProductService.class);
    }

    @SneakyThrows
    @DisplayName("getAllProduct позитивный тест")
    @Test
    void GetProductTests() {
        Response<Product> response = productService
                .getProductAll().execute();
        assertThat(response.code()).as("Продукты не найдены").isEqualTo(200);
        assertThat(response.body().getProducts().size()).as("Количество всех продуктов не совпадает")
                .isEqualTo(productSqlSession.productMapper().selectByExample(new ProductsExample()).size());
    }
}
