package com.yun.mapper;

import com.yun.pojo.Customer;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface CustomerMapper {

    @SelectProvider(type = Provider.class, method = "search")
    List<Customer> search(Map<String, String> keys);

    @SelectProvider(type = Provider.class, method = "check")
    List<Customer> check(Integer customerId);

    @SelectProvider(type = Provider.class, method = "getCustomerList")
    List<Customer> getCustomerList(Integer categoryId);

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
                    ORDER_BY("id");
                }
            }.toString();
            return sql;
        }

        public String check(Integer customerId) {
            String sql = new SQL() {
                {
                    SELECT(" * ");
                    FROM("customer a LEFT JOIN product b ON b.customer_id = a.id LEFT JOIN place c ON c.customer_id = a.id");
                    WHERE(" b.customer_id = " + customerId + " or c.customer_id = " + customerId);

                }
            }.toString();
            return sql;
        }

        public String getCustomerList(Integer categoryId) {
            String sql = new SQL() {
                {
                    SELECT(" a.* ");
                    FROM("customer a LEFT JOIN product b ON b.customer_id = a.id LEFT JOIN place c ON c.customer_id = a.id");
                    WHERE(" b.category_id = " + categoryId);
                    GROUP_BY("a.id");
                    ORDER_BY("a.id");
                }
            }.toString();
            return sql;
        }

    }
}
