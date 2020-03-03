package com.yun.mapper;

import com.yun.pojo.ProductLog;
import com.yun.vo.PlaceVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;


@Repository
public interface ProductLogMapper {

    @SelectProvider(type = Provider.class, method = "search")
    List<ProductLog> search(Map<String, Object> keys);

    class Provider {
        public String search(Map<String, String> keys) {
            String type = keys.get("type");
            String serialNumber = keys.get("serialNumber");
            String sql = new SQL() {
                {
                    SELECT(" * ");
                    FROM("product_log");
                    if(StringUtils.isNoneBlank(type)){
                        WHERE("type = " + type);
                    }
                    if(StringUtils.isNoneBlank(serialNumber)){
                        WHERE("serial_number = '" + serialNumber + "'");
                    }
                    ORDER_BY("id desc");
                }
            }.toString();
            return sql;
        }
    }
}
