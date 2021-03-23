package ru.market.retrofit.CategoryTests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.market.retrofit.dto.Category;
import ru.market.retrofit.service.CategoryService;
import ru.market.retrofit.util.RetrofitUtils;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CategoryNegativeTests {

    static CategoryService categoryService;

    @BeforeAll
    static void beforeAll() {
        categoryService = RetrofitUtils.getRetrofit().create(CategoryService.class);
    }

    @DisplayName("getCategory негативный тест на проверку 404 ошибки")
    @Test
    void getCategoryNegativeTest() throws IOException {

        Response<Category> response = categoryService
                .getCategory(0)
                .execute();
        assertThat(response.errorBody());
        assertThat(response.code()).as("Ошибка не 404").isEqualTo(404);

    }
}
