package com.yun.mapper;

import com.yun.pojo.Place;
import com.yun.vo.PlaceVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface PlaceMapper {

    @SelectProvider(type = Provider.class, method = "search")
    List<PlaceVO> search(Map<String, Object> keys);

    @SelectProvider(type = Provider.class, method = "getPlaceList")
    List<Place> getPlaceList(Integer categoryId, Integer customerId);

    class Provider {

        public String search(Map<String, String> keys) {
            String customerId = keys.get("customerId");
            String sql = new SQL() {
                {
                    SELECT(" * ");
                    FROM("place a left join customer b on a.customer_id = b.id");
                    if (StringUtils.isNoneBlank(customerId)) {
                        WHERE("b.id =" + customerId);
                    }
                    ORDER_BY("a.id");
                }
            }.toString();
            return sql;
        }

        public String getPlaceList(Integer categoryId, Integer customerId) {
            String sql = new SQL() {
                {
                    SELECT(" c.* ");
                    FROM("customer a LEFT JOIN product b ON b.customer_id = a.id LEFT JOIN place c ON c.customer_id = a.id");
                    if (categoryId != 0) {
                        WHERE("b.category_id =" + categoryId);
                    }
                    if (customerId != 0) {
                        WHERE("a.id =" + customerId);
                    }
                    GROUP_BY("c.id");
                    ORDER_BY("c.id");
                }
            }.toString();
            return sql;
        }

    }
}
