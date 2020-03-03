package com.yun.mapper;

import com.yun.vo.ProductVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


@Repository
public interface ProductMapper {

    @SelectProvider(type = Provider.class, method = "search")
    List<ProductVO> search(Map<String, Object> keys);

    @SelectProvider(type = Provider.class, method = "max")
    Integer max(Map<String, Object> keys);



    class Provider {
        public String search(Map<String, String> keys) {
            String key = keys.get("key");
            String serialId = keys.get("serialId");
            Integer placeId = Integer.parseInt(keys.get("placeId"));
            Integer customerId = Integer.parseInt(keys.get("customerId"));
            Integer categoryId = Integer.parseInt(keys.get("categoryId"));

            String sql = new SQL() {
                {
//                    String column = " a.id, a.customer_id, a.place_id, a.category_id, a.add_time, a.out_time, a.remark, a.serial_id, b.category_name, c.place_name, c.address, c.tel, d.customer_name, a.position_num ";
                    String column = " a.*, b.category_name, c.place_name, c.address, c.tel, d.customer_name ";
                    SELECT(column);
                    FROM("product a LEFT JOIN product_category b ON a.category_id=b.id LEFT JOIN place c on a.place_id=c.id  LEFT JOIN customer d on a.customer_id=d.id LEFT JOIN serial_number e on e.product_id = a.id");
                    if (StringUtils.isNoneBlank(serialId)) {
                        WHERE("a.serial_id = " + serialId);
                    }
                    if (StringUtils.isNoneBlank(key)) {
                        WHERE(" (d.customer_name like '%" + key + "%' OR c.place_name like '%" + key + "%' OR b.category_name like '%" + key + "%' OR a.remark like '%" + key + "%' OR e.serial_number like '%" + key + "%' OR c.address like '%" + key + "%')");
                    }
                    if (placeId != 0) {
                        WHERE("a.place_id =" + placeId);
                    }
                    if (customerId != 0) {
                        WHERE("a.customer_id =" + customerId);
                    }
                    if (categoryId != 0) {
                        WHERE("b.id =" + categoryId);
                    }
                    GROUP_BY(" a.id ");
                    ORDER_BY("a.id");
                }
            }.toString();
            return sql;
        }


        public String max(Map<String, String> keys) {
            String key = keys.get("key");
            String productId = keys.get("productId");
            String categoryId = keys.get("categoryId");
            String sql = new SQL() {
                {

                    SELECT("max(a.position_num)");
                    FROM("product a LEFT JOIN product_category b ON a.category_id=b.id LEFT JOIN place c on a.place_id=c.id  LEFT JOIN customer d on a.customer_id=d.id ");
                    if (StringUtils.isNoneBlank(productId)) {
                        WHERE("a.id = " + productId);
                    }
                    if (StringUtils.isNoneBlank(categoryId)) {
                        WHERE("b.id = " + categoryId);
                    }
                    if (StringUtils.isNoneBlank(key)) {
                        WHERE(" (d.customer_name like '%" + key + "%' OR c.place_name like '%" + key + "%' OR b.category_name like '%" + key + "%' OR a.remark like '%" + key + "%')");
                    }
                }
            }.toString();
            return sql;
        }



    }
}
