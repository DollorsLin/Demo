package com.yun.mapper;

import com.yun.pojo.LoginDetail;
import com.yun.pojo.PlayDetail;
import com.yun.pojo.PlayerAccountDetail;
import com.yun.pojo.User;
import com.yun.vo.ScanVO;
import com.yun.vo.SongVO;
import com.yun.vo.UserToRoleVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;


@Repository
public interface UserMapper {

    @SelectProvider(type = Provider.class, method = "list")
    List<User> list(Map<String, Object> keys);

    @SelectProvider(type = Provider.class, method = "accountList")
    List<PlayerAccountDetail> accountList(Integer userId);

    @SelectProvider(type = Provider.class, method = "expList")
    List<UserToRoleVO> expList(Integer userId);

    @SelectProvider(type = Provider.class, method = "loginList")
    List<LoginDetail> loginList(Integer userId);

    @SelectProvider(type = Provider.class, method = "detailList")
    List<PlayDetail> detailList(Integer userId);

    @SelectProvider(type = Provider.class, method = "rank")
    List<UserToRoleVO> rank(Map<String, Object> params);

    @SelectProvider(type = Provider.class, method = "songTotal")
    List<SongVO> songTotal(Map<String, Object> params);

    @SelectProvider(type = Provider.class, method = "getSongDetail")
    List<SongVO> getSongDetail(Map<String, Object> params);

    @SelectProvider(type = Provider.class, method = "scanTotal")
    List<ScanVO> scanTotal(Map<String, Object> params);

    @SelectProvider(type = Provider.class, method = "getScanDetail")
    List<ScanVO> getScanDetail(Map<String, Object> params);

    class Provider {

        public String list(Map<String, String> keys) {
//            String startTime = keys.get("startTime");
//            String endTime = keys.get("endTime");
            String key = keys.get("key");
            String userId = keys.get("userId");
            String sql = new SQL() {
                {
                    SELECT("a.*");
                    FROM("user a");
//                    if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
//                        WHERE(" '" + startTime + "' < a.create_time  and a.create_time < '" + endTime + "'");
//                    }
                    if (StringUtils.isNoneBlank(key)) {
                        WHERE(" ( a.wx_nick_name like '%" + key + "%')");
                    }
                    ORDER_BY("a.last_access desc");
                }
            }.toString();
            return sql;
        }

        public String accountList(Integer userId) {
            String sql = new SQL() {
                {
                    SELECT("a.*");
                    FROM("player_account_detail a");
                    WHERE("a.user_id =" + userId);
                    ORDER_BY("a.id desc");
                }
            }.toString();
            return sql;
        }

        public String expList(Integer userId) {
            String sql = new SQL() {
                {
                    SELECT("a.*,b.role_name");
                    FROM("user_to_role a");
                    LEFT_OUTER_JOIN("role b on b.id = a.role_id");
                    WHERE("a.user_id =" + userId);
                    ORDER_BY("a.id desc");
                }
            }.toString();
            return sql;
        }

        public String loginList(Integer userId) {
            String sql = new SQL() {
                {
                    SELECT("*");
                    FROM("login_detail");
                    WHERE("user_id =" + userId);
                    ORDER_BY("id desc");
                }
            }.toString();
            return sql;
        }

        public String detailList(Integer userId) {
            String sql = new SQL() {
                {
                    SELECT("a.*");
                    FROM("play_detail a");
                    WHERE("a.user_id =" + userId);
                    ORDER_BY("a.id desc");
                }
            }.toString();
            return sql;
        }

        public String rank(Map<String, String> keys) {
            String startTime = keys.get("startTime");
            String endTime = keys.get("endTime");
            String sql = new SQL() {
                {
                    SELECT("a.*,b.role_name");
                    FROM("user_to_role a");
                    LEFT_OUTER_JOIN("role b on b.id = a.role_id");
                    if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
                        WHERE(" '" + startTime + "' < a.last_update  and a.last_update < '" + endTime + "'");
                    }
                    ORDER_BY("a.level desc,a.exp desc,a.last_update desc");
                }
            }.toString();
            return sql;
        }

        public String songTotal(Map<String, String> keys) {
            String startTime = keys.get("beginDate");
            String endTime = keys.get("endDate");
            Integer categoryId = Integer.valueOf(keys.get("categoryId"));
            Integer placeId = Integer.valueOf(keys.get("placeId"));
            String customerId = keys.get("customerId");
            String sql = new SQL() {
                {
                    SELECT("f.id,f.song_name as '歌曲',count(a.song_id) as '点播数',concat(ROUND((count(a.song_id)/(SELECT COUNT(1) FROM play_detail WHERE '" +
                             startTime + "' < a.add_time  and a.add_time < '" + endTime + "'))*100,2),'%') as '占比率'," +
                            "sum(case when a.miss=0 then 1 else 0 end) as '满分数'," +
                            "sum(case when a.percentage=100 then 1 else 0 end) as '全连数'");
                    FROM("play_detail a LEFT JOIN product b ON a.product_id = b.id LEFT JOIN place c ON b.place_id = c.id " +
                            "LEFT JOIN customer d ON c.customer_id = d.id LEFT JOIN product_category e ON b.category_id = e.id " +
                            "LEFT JOIN song f on f.id = a . song_id");
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
                    GROUP_BY("a.song_id");
                    ORDER_BY("a.song_id");
                }
            }.toString();
            return sql;
        }

        public String getSongDetail(Map<String, String> keys) {
            String startTime = keys.get("beginDate");
            String endTime = keys.get("endDate");
            String songId = keys.get("id");
            String sql = new SQL() {
                {
                    SELECT("DATE_FORMAT(a.add_time, '%Y-%m-%d') as '时间',f.song_name as '歌曲',count(a.song_id) as '点播数', " +
                            "sum(case when a.miss=0 then 1 else 0 end) as '满分数'," +
                            "sum(case when a.percentage=100 then 1 else 0 end) as '全连数'");
                    FROM(" play_detail a LEFT JOIN product b ON a.product_id = b.id LEFT JOIN place c ON b.place_id = c.id " +
                            "LEFT JOIN customer d ON c.customer_id = d.id LEFT JOIN product_category e ON b.category_id = e.id " +
                            "LEFT JOIN song f on f.id = a . song_id");
                    if (Objects.nonNull(startTime) && Objects.nonNull(endTime)) {
                        WHERE(" '" + startTime + "' < a.add_time  and a.add_time < '" + endTime + "'");
                    }
                    WHERE("f.id =" + songId);
                    GROUP_BY("DATE_FORMAT(a.add_time,'%Y-%m-%d'),f.id");
                    ORDER_BY("a.add_time");
                }
            }.toString();
            return sql;
        }



        public String scanTotal(Map<String, String> keys) {
            String startTime = keys.get("beginDate");
            Integer categoryId = Integer.valueOf(keys.get("categoryId"));
            String endTime = keys.get("endDate");
            String customerId = keys.get("customerId");
            Integer placeId = Integer.valueOf(keys.get("placeId"));
            String sql = new SQL() {
                {
                    SELECT("b.id,count(1) as '总次数',sum(case when a.pay_type=2 then 1 else 0 end) as '扫码数'," +
                            "concat(ROUND(sum(case when a.pay_type=2 then 1 else 0 end)/count(1)*100,2),'%') as '扫码率',(CASE WHEN c.place_name = NULL THEN '-'  else c.place_name END )  as '场地名称'");
                    FROM("play_detail a LEFT JOIN product b ON a.product_id = b.id LEFT JOIN place c ON b.place_id = c.id " +
                            "LEFT JOIN customer d ON c.customer_id = d.id LEFT JOIN product_category e ON b.category_id = e.id " +
                            "LEFT JOIN song f on f.id = a . song_id");
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
                        WHERE("d.id in (" + customerId+ ")");
                    }
                    GROUP_BY("b.id");
                    ORDER_BY("b.id");
                }
            }.toString();
            return sql;
        }

        public String getScanDetail(Map<String, String> keys) {
            String startTime = keys.get("beginDate");
            String endTime = keys.get("endDate");
            String productId = keys.get("id");
            String sql = new SQL() {
                {
                    SELECT("DATE_FORMAT(a.add_time, '%Y-%m-%d') as '时间',count(1) as '总次数'," +
                            "sum(case when a.pay_type=2 then 1 else 0 end) as '扫码数'");
                    FROM(" play_detail a LEFT JOIN product b ON a.product_id = b.id");
                    if (Objects.nonNull(startTime) && Objects.nonNull(endTime)) {
                        WHERE(" '" + startTime + "' < a.add_time  and a.add_time < '" + endTime + "'");
                    }
                    WHERE("b.id =" + productId);
                    GROUP_BY("DATE_FORMAT(a.add_time,'%Y-%m-%d')");
                    ORDER_BY("a.add_time");
                }
            }.toString();
            return sql;
        }
    }
}
