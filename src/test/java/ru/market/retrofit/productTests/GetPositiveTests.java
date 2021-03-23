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

public class GetPositiveTests {
    static ProductService productService;
    Product product;
    Faker faker = new Faker();
    static Long productId;
    static int productPrice;
    static String productTitle;
    static String categoryTitle;
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
        productPrice = response.body().getPrice();
        productTitle = response.body().getTitle();
        categoryTitle = response.body().getCategoryTitle();
        assertThat(productSqlSession.productMapper().selectByPrimaryKey(productId))
                .as("Продукт не создался").isNotNull();
        assertThat(productSqlSession.productMapper().selectByPrimaryKey(productId).getTitle())
                .as("Название отсутствует").isNotNull();
        assertThat(productSqlSession.productMapper().selectByPrimaryKey(productId).getCategory_id())
                .as("Категория продукта отсутствует").isNotNull();
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
        assertThat(response.body().getId()).as("Id не равень null").isNotNull();

    }

    @SneakyThrows
    @AfterEach
    void tearDown() {

        productSqlSession.productMapper().deleteByPrimaryKey(productId);

        assertThat(productSqlSession.productMapper().selectByPrimaryKey(productId))
                .as("Тестовый продукт не удален").isEqualTo(null);
    }
}
