package com.yun.mapper;

import com.yun.pojo.LoginDetail;
import com.yun.pojo.PlayDetail;
import com.yun.pojo.PlayerAccountDetail;
import com.yun.pojo.User;
import com.yun.vo.UserToRoleVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface UserMapper {

    @SelectProvider(type = Provider.class, method = "list")
    List<User> list(Map<String, Object> keys);


    @SelectProvider(type = Provider.class, method = "rankList")
    List<UserToRoleVO> rankList(Map<String, Object> params);

    @SelectProvider(type = Provider.class, method = "rank")
    List<UserToRoleVO> rank(Integer userId);

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


        public String rankList(Map<String, String> keys) {
            String startTime = keys.get("startTime");
            String endTime = keys.get("endTime");
            String sql = new SQL() {
                {
                    SELECT("a.*,b.role_name,c.head_img_url,c.wx_nick_name");
                    FROM("user_to_role a");
                    LEFT_OUTER_JOIN("role b on b.id = a.role_id");
                    LEFT_OUTER_JOIN("user c on c.id = a.user_id");
                    if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
                        WHERE(" '" + startTime + "' < a.last_update  and a.last_update < '" + endTime + "'");
                    }
                    ORDER_BY("a.level desc,a.exp desc,a.last_update desc");
                }
            }.toString();
            return sql;
        }

        public String rank(Integer userId) {
            String sql = new SQL() {
                {
                    SELECT("b.*,c.role_name");
                    FROM(" (SELECT t.*, @rownum := @rownum + 1 AS rownum FROM (SELECT @rownum := 0) r, " +
                            "user_to_role AS t ORDER BY t.`level` DESC,t.exp DESC,t.last_update DESC ) as b");
                    LEFT_OUTER_JOIN("role c on c.id = b.role_id");
                    WHERE("b.user_id =" + userId);
                }
            }.toString();
            return sql;
        }
    }
}
