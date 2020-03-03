package com.yun.mapper;

import com.yun.vo.OrderVO;
import com.yun.vo.PlaceVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface OrderMapper {

    @SelectProvider(type = Provider.class, method = "list")
    List<OrderVO> list(Map<String, Object> keys);

    class Provider {

        public String list(Map<String, String> keys) {
            String key = keys.get("key");
            String startTime = keys.get("startTime");
            String endTime = keys.get("endTime");
            String categoryId = keys.get("categoryId");
            String customerId = keys.get("customerId");
            String placeId = keys.get("placeId");
            String payType = keys.get("payType");
            String orderStatus = keys.get("orderStatus");
            String status = keys.get("status");
            String sql = new SQL() {
                {
                    SELECT(" a.*,b.status,c.customer_name,d.place_name,e.category_name as product_name ");
                    FROM(" `order` a LEFT JOIN product_order b ON a.id = b.relate_id LEFT JOIN product p on p.id = a. product_id LEFT JOIN customer c on c.id = p.customer_id " +
                            "LEFT JOIN place d on p.place_id = d.id LEFT JOIN product_category e on e.id = p.category_id");
                    if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
                        WHERE(" '" + startTime + "' < a.order_time  and a.order_time < '" + endTime + "'");
                    }
                    if (StringUtils.isNoneBlank(key)) {
                        WHERE(" ( a.id like '%" + key + "%' OR a.other_order_no like '%" + key + "%' OR a.ums_order_no like '%" + key + "%' OR a.target_order_no like '%" + key + "%'" +
                                " OR e.category_name like '%" + key + "%')");
                    }
                    if (StringUtils.isNoneBlank(categoryId)) {
                        WHERE("  e.id =" + categoryId);
                    }
                    if (StringUtils.isNoneBlank(placeId)) {
                        WHERE("  d.id =" + placeId);
                    }
                    if (StringUtils.isNoneBlank(customerId)) {
                        WHERE("  c.id =" + customerId);
                    }
                    if (StringUtils.isNoneBlank(payType)) {
                        WHERE("  a.pay_type =" + payType);
                    }
                    if (StringUtils.isNoneBlank(orderStatus)) {
                        WHERE("  a.order_status =" + orderStatus);
                    }
                    if (StringUtils.isNoneBlank(status)) {
                        WHERE("  b.status =" + status);
                    }
//                    GROUP_BY(" a.id");
                    ORDER_BY("a.id desc");
                }
            }.toString();
            return sql;
        }

    }
}
