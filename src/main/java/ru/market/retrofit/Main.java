package ru.market.retrofit;

import lombok.SneakyThrows;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import ru.geekbrains.java4.lesson6.db.dao.CategoriesMapper;
import ru.geekbrains.java4.lesson6.db.model.Categories;
import ru.geekbrains.java4.lesson6.db.model.CategoriesExample;

import java.io.InputStream;
import java.util.List;

public class Main {

    private static String resources = "mybatisConfig.xml";

    @SneakyThrows
    public static void main(String[] args) {

        InputStream is = Resources.getResourceAsStream(resources);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        SqlSession session = sqlSessionFactory.openSession(true);

        CategoriesMapper categoriesMapper = session.getMapper(CategoriesMapper.class);
        long count = categoriesMapper.countByExample(new CategoriesExample());
        System.out.println(count);

        Categories categories_1 = categoriesMapper.selectByPrimaryKey(1);
        List<Categories> list = categoriesMapper.selectByExample(new CategoriesExample());
        System.out.println(categories_1.getTitle());

    }

}
