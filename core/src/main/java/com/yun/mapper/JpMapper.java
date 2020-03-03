package com.yun.mapper;

import com.yun.vo.JpVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import javax.persistence.OrderBy;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Repository
public interface JpMapper {

    @SelectProvider(type = Provider.class, method = "search")
    List<JpVO> search(Map<String, Object> keys);

    @SelectProvider(type = Provider.class, method = "deleteByTime")
    Integer deleteByTime(Map<String, Object> keys);

    class Provider {

        public String search(Map<String, String> keys) {
            String startTime = keys.get("startTime");
            String endTime = keys.get("endTime");
            String key = keys.get("key");
            String categoryId = keys.get("categoryId");
            String customerId = keys.get("customerId");
            String placeId = keys.get("placeId");
            String sql = new SQL() {
                {
                    SELECT("  e.category_name, d.customer_name, c.place_name, a.product_id, a.create_time, a.ticket, a.serial_number ");
                    FROM(" jp a LEFT JOIN product b ON a.product_id = b.id LEFT JOIN place c ON b.place_id = c.id LEFT JOIN customer d ON c.customer_id = d.id LEFT JOIN product_category e ON b.category_id = e.id ");
                    if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
                        WHERE(" '" + startTime + "' < a.create_time  and a.create_time < '" + endTime + "'");
                    }
                    if (StringUtils.isNoneBlank(key)) {
                        WHERE(" ( d.customer_name like '%" + key + "%' OR c.place_name like '%" + key + "%' OR e.category_name like '%" + key + "%')");
                    }
                    if (StringUtils.isNoneBlank(customerId)) {
                        WHERE("  d.id =" + customerId);
                    }
                    if (StringUtils.isNoneBlank(placeId)) {
                        WHERE("  b.id =" + placeId);
                    }
                    if (StringUtils.isNoneBlank(categoryId)) {
                        WHERE("  e.id =" + categoryId);
                    }
                    GROUP_BY(" a.product_id, a.create_time ");
                    ORDER_BY("a.id desc");
                }
            }.toString();
            return sql;
        }

        public String deleteByTime(Map<String, String> keys) {
            String productId = keys.get("productId");
            String startTime = keys.get("startTime");
            String endTime = keys.get("endTime");
            String sql = new SQL() {
                {
                    DELETE_FROM("jp");
                    WHERE("product_id=" + productId);
                    if (StringUtils.isNoneBlank(startTime) && StringUtils.isNotBlank(endTime)) {
                        WHERE("add_time >= '" + startTime + "' and add_time <='" + endTime + "'");
                    } else {
                        if (StringUtils.isNoneBlank(startTime)) {
                            WHERE("add_time >= '" + startTime + "'");
                        }
                        if (StringUtils.isNoneBlank(endTime)) {
                            WHERE("add_time <='" + endTime + "'");
                        }
                    }
                }
            }.toString();
            return sql;
        }

    }
}
