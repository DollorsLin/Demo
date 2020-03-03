package com.yun.mapper;

import com.yun.pojo.Place;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface ProductCategoryMapper {

    @SelectProvider(type = Provider.class, method = "search")
    List<Place> search(Map<String, String> keys);

    class Provider {

        public String search(Map<String, String> keys) {
            String sql = new SQL() {
                {
                    SELECT(" * ");
                    FROM("product_category");
                    ORDER_BY("id");
                }
            }.toString();
            return sql;
        }

    }
}
