package com.yun.mapper;

import com.yun.pojo.ProductToUser;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductToUserMapper {

    @SelectProvider(type = Provider.class, method = "search")
    ProductToUser search(String openId);

//    @SelectProvider(type = Provider.class, method = "getBySerialNumber")
//    ProductToUser getBySerialNumber(String serialNumber);

    class Provider {

        public String search(String openId) {
            String sql = new SQL() {
                {
                    SELECT(" a.* ");
                    FROM("product_to_user a ");
                    LEFT_OUTER_JOIN("user b on b.id = a.user_id");
                    WHERE("b.open_id = '" + openId + "'");

                }
            }.toString();
            return sql;
        }



    }
}
