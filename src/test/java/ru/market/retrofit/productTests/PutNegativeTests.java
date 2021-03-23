package ru.market.retrofit.productTests;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.market.retrofit.category.enums.CategoryType;
import ru.market.retrofit.dto.Product;
import ru.market.retrofit.service.ProductService;
import ru.market.retrofit.util.ProductSqlSession;
import ru.market.retrofit.util.RetrofitUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PutNegativeTests {

    static ProductService productService;
    Product product;
    Faker faker = new Faker();
    ProductSqlSession productSqlSession = new ProductSqlSession();
    Long productId;


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

        assertThat(productSqlSession.productMapper().selectByPrimaryKey(productId))
                .as("Продукт не создался").isNotNull();
        assertThat(productSqlSession.productMapper().selectByPrimaryKey(productId).getTitle())
                .as("Название отсутствует").isNotNull();
        assertThat(productSqlSession.productMapper().selectByPrimaryKey(productId).getCategory_id())
                .as("Категория продукта отсутствует").isNotNull();
    }

    @SneakyThrows
    @DisplayName("putProduct негативный тест без названия")
    @Test
    void PutProductNegative1Tests() {

        product = new Product()
                .withId(productId)
                .withCategoryTitle(CategoryType.FOOD.getTitle())
                .withPrice((int) (Math.random() * 1000 + 1));

        Long prodId = product.getId();

        Response<Product> response = productService
                .putProduct(product).execute();

        assertThat(response.code()).as("Статус не 404").isEqualTo(404);
        assertThat(productSqlSession.productMapper().selectByPrimaryKey(prodId).getTitle())
                .as("Название заменилось на null").isNotNull();


    }

    @SneakyThrows
    @DisplayName("putProduct негативный тест без категории")
    @Test
    void PutProductNegative2Tests() {

        product = new Product()
                .withId(productId)
                .withPrice((int) (Math.random() * 1000 + 1))
                .withTitle(faker.food().ingredient());

        Long prodId = product.getId();

        Response<Product> response = productService
                .putProduct(product).execute();

        assertThat(response.code()).as("Статус не 404").isEqualTo(404);
        assertThat(productSqlSession.productMapper().selectByPrimaryKey(prodId))
                .as("Категория заменилась на null").isNotNull();


    }

    @SneakyThrows
    @DisplayName("putProduct негативный тест без цены")
    @Test
    void PutProductNegative3Tests() {

        product = new Product()
                .withId(productId)
                .withCategoryTitle(CategoryType.FOOD.getTitle())
                .withTitle(faker.food().ingredient());

        Long prodId = product.getId();

        Response<Product> response = productService
                .putProduct(product).execute();

        assertThat(response.code()).as("Статус не 404").isEqualTo(404);
        assertThat(productSqlSession.productMapper().selectByPrimaryKey(prodId))
                .as("Цена заменилась на null").isNotNull();
    }
}


