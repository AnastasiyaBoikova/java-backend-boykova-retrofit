package ru.market.retrofit;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.geekbrains.java4.lesson6.db.dao.CategoriesMapper;
import ru.geekbrains.java4.lesson6.db.model.Categories;
import ru.geekbrains.java4.lesson6.db.model.CategoriesExample;
import ru.geekbrains.java4.lesson6.db.model.Products;
import ru.geekbrains.java4.lesson6.db.model.ProductsExample;
import ru.market.retrofit.dto.Category;
import ru.market.retrofit.service.CategoryService;
import ru.market.retrofit.util.CategoriesSqlSession;
import ru.market.retrofit.util.ProductSqlSession;
import ru.market.retrofit.util.RetrofitUtils;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.in;

public class Tests {
    static CategoryService categoryService;
    Integer catId = null;
    Long prodId;
    CategoriesSqlSession categoriesSession = new CategoriesSqlSession();
    //   int categoriesId = (int) countCategoriesNumber(categoriesSession.categoriesMapper());
    ProductSqlSession productSqlSession = new ProductSqlSession();
    Faker faker = new Faker();


    @BeforeAll
    static void beforeAll() {
        categoryService = RetrofitUtils.getRetrofit().create(CategoryService.class);
    }

    @SneakyThrows
    @Test
    void name() {

//        Categories cat = new Categories();
//        cat.setTitle("book");

//        categoriesSession.categoriesMapper().insert(cat);
//        productSqlSession.productMapper().insert(new Products(faker.book().title(), (int) (Math.random() * 1000 + 1), catId));


        List<Products> productsAll = productSqlSession.productMapper().selectByExample(new ProductsExample());
       // System.out.println(productsAll.toString());

        for (Products products : productsAll) {


            Integer category_id = products.getCategory_id();
            if(category_id!= null && category_id == 777){
                System.out.println(products.getId());
            }

        }


//        for (Products products : productsAll) {
//
//            if (products.getCategory_id().equals(777)) {
//                prodId = products.getId();
//                System.out.println(prodId);
//            }
//        }


    }

}
