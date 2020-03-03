package com.yun.mapper;

import com.yun.pojo.HeartbeatLog;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface HeartbeatLogMapper {

    @SelectProvider(type = Provider.class, method = "search")
    List<HeartbeatLog> search(Map<String, Object> keys);

    class Provider {

        public String search(Map<String, String> keys) {
            String key = keys.get("key");
            String sql = new SQL() {
                {
                    SELECT(" * ");
                    FROM("heartbeat_log");
                    WHERE( " serial_number = '" + key + "'");
                    ORDER_BY(" add_time desc ");

                }
            }.toString();
            return sql;
        }

    }
}
