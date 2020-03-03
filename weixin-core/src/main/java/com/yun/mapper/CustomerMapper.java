package com.yun.mapper;

import com.yun.pojo.Customer;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


//@Repository
public interface CustomerMapper {

    @SelectProvider(type = Provider.class, method = "search")
    List<Customer> search(Map<String, String> keys);

    class Provider {

        public String search(Map<String, String> keys) {

            String key = keys.get("key");


            String sql = new SQL() {
                {
                    SELECT(" * ");
                    FROM("customer");

                    if (StringUtils.isNoneBlank(key)) {
                        WHERE(" customer_name like '%" + key + "%' OR address like '%" + key + "%'");
                    }

                }
            }.toString();
            return sql;
        }

    }
}
