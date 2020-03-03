package com.yun.mapper;

import com.yun.pojo.Score;
import com.yun.vo.Revenue;
import com.yun.vo.RevenueDetail;
import com.yun.vo.ScoreVO;
import com.yun.vo.StatisticsVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import javax.persistence.OrderBy;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Repository
public interface ScoreMapper {

    @SelectProvider(type = Provider.class, method = "search")
    List<ScoreVO> search(Map<String, Object> keys);

    @SelectProvider(type = Provider.class, method = "statistics")
    List<StatisticsVO> statistics(Map<String, Object> keys);

    @SelectProvider(type = Provider.class, method = "detail")
    List<StatisticsVO> detail(Map<String, Object> keys);

    @SelectProvider(type = Provider.class, method = "deleteByTime")
    Integer deleteByTime(Map<String, Object> keys);

    @SelectProvider(type = Provider.class, method = "getTotal")
    List<Revenue> getTotal(Map<String, Object> keys);

    @SelectProvider(type = Provider.class, method = "getTotalOne")
    List<Revenue> getTotalOne(Map<String, Object> keys);

    @SelectProvider(type = Provider.class, method = "getDetail")
    List<RevenueDetail> getDetail(Map<String, Object> keys);

    @SelectProvider(type = Provider.class, method = "getDetailOne")
    List<RevenueDetail> getDetailOne(Map<String, Object> params);

    @SelectProvider(type = Provider.class, method = "getDetailTwo")
    List<RevenueDetail> getDetailTwo(Map<String, Object> params);

    @SelectProvider(type = Provider.class, method = "getTotalTwo")
    List<Revenue> getTotalTwo(Map<String, Object> params);

    @SelectProvider(type = Provider.class, method = "getOrder")
    List<Revenue> getOrder(Map<String, Object> params);

    @SelectProvider(type = Provider.class, method = "getOrderDetail")
    List<RevenueDetail> getOrderDetail(Map<String, Object> params);


    class Provider {

        public String search(Map<String, String> keys) {
            String startTime = keys.get("startTime");
            String endTime = keys.get("endTime");
            String customerId = keys.get("customerId");
            String categoryId = keys.get("categoryId");
            String placeId = keys.get("placeId");
            String key = keys.get("key");
            String sql = new SQL() {
                {
                    SELECT(" e.category_name, d.customer_name, c.place_name,a.id, a.product_id , DATE_FORMAT(a.create_time, '%Y-%m-%d %T') create_time, a.ticket, a.coin, a.refund_coin, a.serial_number, a.outer_card ");
                    FROM(" score a LEFT JOIN product b ON a.product_id = b.id LEFT JOIN place c ON b.place_id = c.id LEFT JOIN customer d ON c.customer_id = d.id LEFT JOIN product_category e ON b.category_id = e.id ");
                    if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
                        WHERE(" '" + startTime + "' < a.create_time  and a.create_time < '" + endTime + "'");
                    }
                    if (StringUtils.isNoneBlank(key)) {
                        WHERE("  (d.customer_name like '%" + key + "%' OR c.place_name like '%" + key + "%' OR e.category_name like '%" + key + "%')");
                    }
                    if (StringUtils.isNoneBlank(placeId)) {
                        WHERE("  c.id =" + placeId);
                    }
                    if (StringUtils.isNoneBlank(customerId)) {
                        WHERE("  d.id =" + customerId);
                    }
                    if (StringUtils.isNoneBlank(categoryId)) {
                        WHERE("  e.id =" + categoryId);
                    }
                    ORDER_BY("a.id desc");
                }
            }.toString();
            return sql;
        }

        public String detail(Map<String, String> keys) {
            String startTime = keys.get("startTime");
            String endTime = keys.get("endTime");
            String key = keys.get("key");

            String productId = keys.get("productId");
            String sql = new SQL() {
                {
                    SELECT(" e.category_name, d.customer_name, c.place_name, a.product_id,a.position, " +
                            "DATE_FORMAT(a.create_time, '%Y-%m-%d') create_time,sum(a.coin) as coin,sum(a.ticket) as ticket");
                    FROM(" score a LEFT JOIN product b ON a.product_id = b.id LEFT JOIN place c ON b.place_id = c.id LEFT JOIN customer d ON c.customer_id = d.id LEFT JOIN product_category e ON b.category_id = e.id ");
                    if (Objects.nonNull(startTime) && Objects.nonNull(endTime)) {
                        WHERE(" '" + startTime + "' < a.create_time  and a.create_time < '" + endTime + "'");
                    }
                    if (StringUtils.isNoneBlank(key)) {
                        WHERE(" ( d.customer_name like '%" + key + "%' OR c.place_name like '%" + key + "%' OR e.category_name like '%" + key + "%' )");
                    }
                    WHERE("b.id =" + productId);
                    GROUP_BY("DATE_FORMAT(a.create_time,'%Y-%m-%d'), a.position");
                    ORDER_BY("a.create_time");
                }
            }.toString();
            return sql;
        }


        public String statistics(Map<String, String> keys) {
            String endTime = keys.get("endTime");
            String startTime = keys.get("startTime");
            String key = keys.get("key");
            String categoryId = keys.get("categoryId");
            String placeId = keys.get("placeId");
            String customerId = keys.get("customerId");

            String sql = new SQL() {
                {
                    SELECT(" e.category_name, d.customer_name, c.place_name, a.product_id,a.position, " +
                            "sum(a.coin) as coin,sum(a.ticket) as ticket");
                    FROM(" score a LEFT JOIN product b ON a.product_id = b.id LEFT JOIN place c ON b.place_id = c.id LEFT JOIN customer d ON c.customer_id = d.id LEFT JOIN product_category e ON b.category_id = e.id ");
                    if (Objects.nonNull(startTime) && Objects.nonNull(endTime)) {
                        WHERE(" '" + startTime + "' < a.create_time  and a.create_time < '" + endTime + "'");
                    }
                    if (StringUtils.isNoneBlank(key)) {
                        WHERE(" ( d.customer_name like '%" + key + "%' OR c.place_name like '%" + key + "%' OR e.category_name like '%" + key + "%' )");
                    }
                    if (StringUtils.isNoneBlank(categoryId)) {
                        WHERE("e.id =" + categoryId);
                    }
                    if (StringUtils.isNoneBlank(placeId)) {
                        WHERE("b.place_id =" + placeId);
                    }
                    if (StringUtils.isNoneBlank(customerId)) {
                        WHERE("d.id =" + customerId);
                    }
                    GROUP_BY("a.product_id,a.position");
                }
            }.toString();
            return sql;
        }

        public String deleteByTime(Map<String, String> keys) {
            String productId = keys.get("productId");
            String endTime = keys.get("endTime");
            String startTime = keys.get("startTime");
            String sql = new SQL() {
                {
                    DELETE_FROM("score");
                    WHERE("product_id=" + productId);
                    if (StringUtils.isNoneBlank(startTime) && StringUtils.isNotBlank(endTime)) {
                        WHERE("create_time >= '" + startTime + "' and create_time <='" + endTime + "'");
                    } else {
                        if (StringUtils.isNoneBlank(startTime)) {
                            WHERE("create_time >= '" + startTime + "'");
                        }
                        if (StringUtils.isNoneBlank(endTime)) {
                            WHERE("create_time <='" + endTime + "'");
                        }
                    }
                }
            }.toString();
            return sql;
        }

        public String getTotal(Map<String, String> keys) {
            String endTime = keys.get("endDate");
            String startTime = keys.get("beginDate");
            Integer categoryId = Integer.valueOf(keys.get("categoryId"));
            Integer placeId = Integer.valueOf(keys.get("placeId"));
            String customerId = keys.get("customerId");

            String sql = new SQL() {
                {
                    SELECT("(case when sum(a.coin) = 0 then '-' when  sum(a.coin) > 0 then sum(a.coin) END) as '投币',(CASE WHEN c.place_name = NULL THEN '-'  else c.place_name END )  as '场地名称'," +
                            "(case when sum(a.ticket) = 0 then '-' when  sum(a.ticket) > 0 then sum(a.ticket) END) as '彩票'," +
//                            "(case when sum(a.outer_card) = 0 then '-' when  sum(a.outer_card) > 0 then sum(a.outer_card) END) as '出卡数'," +
                            "(case when sum(a.ticket) = 0 then '-' when sum(a.coin) = 0 then '-' when  sum(a.ticket)/sum(a.coin) > 0 then ROUND(sum(a.ticket)/sum(a.coin),2) END) as '出票率',a.product_id as 'id'");
//                            "ROUND(sum(a.outer_card)/sum(a.coin),2) as '出卡率'");
                    FROM(" score a LEFT JOIN product b ON a.product_id = b.id LEFT JOIN place c ON b.place_id = c.id LEFT JOIN customer d ON c.customer_id = d.id LEFT JOIN product_category e ON b.category_id = e.id ");
                    if (Objects.nonNull(startTime) && Objects.nonNull(endTime)) {
                        WHERE(" '" + startTime + "' < a.create_time  and a.create_time < '" + endTime + "'");
                    }
                    if (categoryId != 0) {
                        WHERE("e.id =" + categoryId);
                    }
                    if (placeId != 0) {
                        WHERE("b.place_id =" + placeId);
                    }
                    if (StringUtils.isNotBlank(customerId) && !customerId.equals("0")) {
                        WHERE("d.id in (" + customerId + ")");
                    }
                    GROUP_BY("a.product_id");
                }
            }.toString();
            return sql;
        }

        public String getTotalOne(Map<String, String> keys) {
            String endTime = keys.get("endDate");
            String startTime = keys.get("beginDate");
            Integer categoryId = Integer.valueOf(keys.get("categoryId"));
            Integer placeId = Integer.valueOf(keys.get("placeId"));
            String customerId = keys.get("customerId");

            String sql = new SQL() {
                {
                    SELECT("(case when sum(a.coin) = 0 then '-' when  sum(a.coin) > 0 then sum(a.coin) END) as '投币',(CASE WHEN c.place_name = NULL THEN '-'  else c.place_name END )  as '场地名称',b.id," +
                            "(case when sum(a.outer_card) = 0 then '-' when  sum(a.outer_card) > 0 then sum(a.outer_card) END) as '出卡数'," +
                            "(case when sum(a.outer_card)/sum(a.coin) = 0 or null then '-' when  sum(a.outer_card)/sum(a.coin) > 0 then ROUND(sum(a.outer_card)/sum(a.coin),2) END) as '出卡率'");
                    FROM(" score a LEFT JOIN product b ON a.product_id = b.id LEFT JOIN place c ON b.place_id = c.id LEFT JOIN customer d ON c.customer_id = d.id LEFT JOIN product_category e ON b.category_id = e.id ");
                    if (Objects.nonNull(startTime) && Objects.nonNull(endTime)) {
                        WHERE(" '" + startTime + "' < a.create_time  and a.create_time < '" + endTime + "'");
                    }
                    if (categoryId != 0) {
                        WHERE("e.id =" + categoryId);
                    }
                    if (placeId != 0) {
                        WHERE("b.place_id =" + placeId);
                    }
                    if (StringUtils.isNotBlank(customerId) && !customerId.equals("0")) {
                        WHERE("d.id in (" + customerId + ")");
                    }
                    GROUP_BY("a.product_id");
                }
            }.toString();
            return sql;
        }

        public String getTotalTwo(Map<String, String> keys) {
            String startTime = keys.get("beginDate");
            String endTime = keys.get("endDate");
            Integer categoryId = Integer.valueOf(keys.get("categoryId"));
            Integer placeId = Integer.valueOf(keys.get("placeId"));
            String customerId = keys.get("customerId");
            String sql = new SQL() {
                {
                    SELECT("(case when sum(a.coin) = 0 then '-' when  sum(a.coin) > 0 then sum(a.coin) END) as '投币',b.id,(CASE WHEN c.place_name = NULL THEN '-'  else c.place_name END )  as '场地名称'");
//                            "(case when sum(f.amount_in_fact) = 0 then '-' when  sum(f.amount_in_fact) > 0 then ROUND(sum(f.amount_in_fact),2) END) as '元'");
                    FROM("score a LEFT JOIN product b ON a.product_id = b.id LEFT JOIN place c ON b.place_id = c.id " +
                            "LEFT JOIN customer d ON c.customer_id = d.id LEFT JOIN product_category e ON b.category_id = e.id ");
//                            "LEFT JOIN `order` f on f.product_id = b.id");
//                    WHERE("f.order_status = 2");
                    if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
                        WHERE(" '" + startTime + "' < a.add_time  and a.add_time < '" + endTime + "'");
                    }
                    if (categoryId != 0) {
                        WHERE("e.id =" + categoryId);
                    }
                    if (placeId != 0) {
                        WHERE("b.place_id =" + placeId);
                    }
                    if (StringUtils.isNotBlank(customerId) && !customerId.equals("0")) {
                        WHERE("d.id in (" + customerId + ")");
                    }
                    GROUP_BY("b.id");
                    ORDER_BY("b.id");
                }
            }.toString();
            return sql;
        }

        public String getOrder(Map<String, String> keys) {
            String startTime = keys.get("beginDate");
            String endTime = keys.get("endDate");
//            Integer categoryId = Integer.valueOf(keys.get("categoryId"));
//            Integer placeId = Integer.valueOf(keys.get("placeId"));
            String customerId = keys.get("customerId");
            String sql = new SQL() {
                {
                    SELECT("b.id,"+
                            "(case when sum(f.amount_in_fact) = 0 then '-' when  sum(f.amount_in_fact) > 0 then ROUND(sum(f.amount_in_fact),2) END) as '元'");
                    FROM("product b  LEFT JOIN place c ON b.place_id = c.id " +
                            "LEFT JOIN customer d ON c.customer_id = d.id "+
                            "LEFT JOIN `order` f on f.product_id = b.id");
                    WHERE("f.order_status = 2");
                    if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
                        WHERE(" '" + startTime + "' < f.order_time  and f.order_time < '" + endTime + "'");
                    }
//                    if (categoryId != 0) {
//                        WHERE("e.id =" + categoryId);
//                    }
//                    if (placeId != 0) {
//                        WHERE("b.place_id =" + placeId);
//                    }
                    if (StringUtils.isNotBlank(customerId) && !customerId.equals("0")) {
                        WHERE("d.id in (" + customerId + ")");
                    }
                    GROUP_BY("b.id");
                    ORDER_BY("b.id");
                }
            }.toString();
            return sql;
        }

        public String getDetail(Map<String, String> keys) {
            String startTime = keys.get("beginDate");
            String endTime = keys.get("endDate");
            String productId = keys.get("id");
            String sql = new SQL() {
                {
                    SELECT("DATE_FORMAT(a.create_time, '%Y-%m-%d') as '时间', " +
                            "(case when sum(a.coin) = 0 then '-' when  sum(a.coin) > 0 then sum(a.coin) END) as '投币'," +
                            "(case when sum(a.ticket) = 0 then '-' when  sum(a.ticket) > 0 then sum(a.ticket) END) as '彩票',ROUND(sum(a.ticket)/sum(a.coin),2) as '出票率'");
//                            "(case when sum(a.outer_card) = 0 then '-' when  sum(a.outer_card) > 0 then sum(a.outer_card) END) as '出卡数'");
                    FROM(" score a LEFT JOIN product b ON a.product_id = b.id LEFT JOIN place c ON b.place_id = c.id LEFT JOIN customer d ON c.customer_id = d.id LEFT JOIN product_category e ON b.category_id = e.id ");
                    if (Objects.nonNull(startTime) && Objects.nonNull(endTime)) {
                        WHERE(" '" + startTime + "' < a.create_time  and a.create_time < '" + endTime + "'");
                    }
                    WHERE("b.id =" + productId);
                    GROUP_BY("DATE_FORMAT(a.create_time,'%Y-%m-%d')");
                    ORDER_BY("a.create_time");
                }
            }.toString();
            return sql;
        }

        public String getDetailOne(Map<String, String> keys) {
            String startTime = keys.get("beginDate");
            String endTime = keys.get("endDate");
            String productId = keys.get("id");
            String sql = new SQL() {
                {
                    SELECT("DATE_FORMAT(a.create_time, '%Y-%m-%d') as '时间', " +
                            "(case when sum(a.coin) = 0 then '-' when  sum(a.coin) > 0 then sum(a.coin) END) as '投币'," +
//                            "(case when sum(a.ticket) = 0 then '-' when  sum(a.ticket) > 0 then sum(a.ticket) END) as '彩票,'+" +
                                    "ROUND(sum(a.outer_card)/sum(a.coin),2) as '出卡率'," +
                            "(case when sum(a.outer_card) = 0 then '-' when  sum(a.outer_card) > 0 then sum(a.outer_card) END) as '出卡数'"
                            );
                    FROM(" score a LEFT JOIN product b ON a.product_id = b.id LEFT JOIN place c ON b.place_id = c.id LEFT JOIN customer d ON c.customer_id = d.id LEFT JOIN product_category e ON b.category_id = e.id ");
                    if (Objects.nonNull(startTime) && Objects.nonNull(endTime)) {
                        WHERE(" '" + startTime + "' < a.create_time  and a.create_time < '" + endTime + "'");
                    }
                    WHERE("b.id =" + productId);
                    GROUP_BY("DATE_FORMAT(a.create_time,'%Y-%m-%d')");
                    ORDER_BY("a.create_time");
                }
            }.toString();
            return sql;
        }

        public String getDetailTwo(Map<String, String> keys) {
            String startTime = keys.get("beginDate");
            String endTime = keys.get("endDate");
            String productId = keys.get("id");
            String sql = new SQL() {
                {
                    SELECT("DATE_FORMAT(a.create_time, '%Y-%m-%d') as '时间', " +
                                    "(case when sum(a.coin) = 0 then '-' when  sum(a.coin) > 0 then sum(a.coin) END) as '投币'"
                    );
                    FROM(" score a LEFT JOIN product b ON a.product_id = b.id LEFT JOIN place c ON b.place_id = c.id LEFT JOIN customer d ON c.customer_id = d.id LEFT JOIN product_category e ON b.category_id = e.id ");
                    if (Objects.nonNull(startTime) && Objects.nonNull(endTime)) {
                        WHERE(" '" + startTime + "' < a.create_time  and a.create_time < '" + endTime + "'");
                    }
                    WHERE("b.id =" + productId);
                    GROUP_BY("DATE_FORMAT(a.create_time,'%Y-%m-%d')");
                    ORDER_BY("a.create_time");
                }
            }.toString();
            return sql;
        }

        public String getOrderDetail(Map<String, String> keys) {
            String startTime = keys.get("beginDate");
            String endTime = keys.get("endDate");
            String productId = keys.get("id");
            String sql = new SQL() {
                {
                    SELECT("DATE_FORMAT(f.order_time, '%Y-%m-%d') as '时间',"+
                            "(case when sum(f.amount_in_fact) = 0 then '-' when  sum(f.amount_in_fact) > 0 then ROUND(sum(f.amount_in_fact),2) END) as '元'");
                    FROM("product b  LEFT JOIN place c ON b.place_id = c.id " +
                            "LEFT JOIN `order` f on f.product_id = b.id");
                    if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
                        WHERE(" '" + startTime + "' < f.order_time  and f.order_time < '" + endTime + "'");
                    }
                    WHERE("f.order_status = 2");
                    WHERE("b.id =" + productId);
                    GROUP_BY("DATE_FORMAT(f.order_time,'%Y-%m-%d')");
                    ORDER_BY("f.order_time");
                }
            }.toString();
            return sql;
        }

    }
}
