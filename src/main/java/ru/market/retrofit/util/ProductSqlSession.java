package ru.market.retrofit.util;

import lombok.SneakyThrows;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import ru.geekbrains.java4.lesson6.db.dao.CategoriesMapper;
import ru.geekbrains.java4.lesson6.db.dao.ProductsMapper;

import java.io.InputStream;

public class ProductSqlSession {
    private static String resources = "mybatisConfig.xml";

    @SneakyThrows
    public ProductsMapper productMapper() {

        InputStream is = Resources.getResourceAsStream(resources);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        SqlSession session = sqlSessionFactory.openSession(true);
        return session.getMapper(ProductsMapper.class);
    }
}
