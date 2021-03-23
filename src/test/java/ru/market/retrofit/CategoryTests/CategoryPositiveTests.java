package ru.market.retrofit.CategoryTests;


import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import ru.geekbrains.java4.lesson6.db.model.Categories;
import ru.geekbrains.java4.lesson6.db.model.CategoriesExample;
import ru.geekbrains.java4.lesson6.db.model.Products;
import ru.geekbrains.java4.lesson6.db.model.ProductsExample;
import ru.market.retrofit.dto.Category;
import ru.market.retrofit.dto.Product;
import ru.market.retrofit.service.CategoryService;
import ru.market.retrofit.util.CategoriesSqlSession;
import ru.market.retrofit.util.ProductSqlSession;
import ru.market.retrofit.util.RetrofitUtils;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static ru.market.retrofit.category.enums.CategoryType.FOOD;

public class CategoryPositiveTests {

    static CategoryService categoryService;

    Integer catId;
    Long prodId;
    String catTitle = "book";
    CategoriesSqlSession categoriesSession = new CategoriesSqlSession();
    ProductSqlSession productSqlSession = new ProductSqlSession();
    Categories cat = new Categories();
    Faker faker = new Faker();

    @BeforeAll
    static void beforeAll() {
        categoryService = RetrofitUtils.getRetrofit().create(CategoryService.class);
    }

    @BeforeEach
    void setUp() {

        cat.setTitle(catTitle);
        categoriesSession.categoriesMapper().insert(cat);

        List<Categories> categoriesAll = categoriesSession.categoriesMapper().selectByExample(new CategoriesExample());
        for (Categories categories : categoriesAll) {

            if (categories.getTitle() != null && categories.getTitle().equals(catTitle)) {
                catId = categories.getId();
                System.out.println(catId);
            }
        }
        productSqlSession.productMapper().insert(new Products(faker.book().title(), (int) (Math.random() * 1000 + 1), catId));

    }

    @DisplayName("getCategory позитивный тест")
    @Test
    void getCategoryPositiveTest() throws IOException {

        Response<Category> response = categoryService
                .getCategory(catId)
                .execute();

        assertThat(response.body().getId()).as("Id категории не верен").isEqualTo(catId);
        assertThat(response.body().getTitle()).as("Тип продукта не book").isEqualTo(catTitle);
        assertThat(response.body().getProducts().size()).as("Количество продуктов в категории book не 1").isEqualTo(1);
        assertThat(response.code()).as("Статус не 200").isEqualTo(200);

        List<Product> productsCategory = response.body().getProducts();
        for (Product products : productsCategory) {
            assertThat(products.getCategoryTitle()).as("У продукта не верная категория").isEqualTo("book");
        }
    }

    @AfterEach
    void tearDown() {

        List<Products> productsAll = productSqlSession.productMapper().selectByExample(new ProductsExample());
        for (Products products : productsAll) {

            if (products.getCategory_id() != null && products.getCategory_id().equals(catId)) {
                prodId = products.getId();
                System.out.println(prodId);
            }
        }
        productSqlSession.productMapper().deleteByPrimaryKey(prodId);
        assertThat(productSqlSession.productMapper().selectByPrimaryKey(prodId))
                .as("Тестовый продукт не удалился").isEqualTo(null);
        categoriesSession.categoriesMapper().deleteByPrimaryKey(catId);
        assertThat(categoriesSession.categoriesMapper().selectByPrimaryKey(catId))
                .as("Тестовая категория не удалилась").isEqualTo(null);

    }



}
