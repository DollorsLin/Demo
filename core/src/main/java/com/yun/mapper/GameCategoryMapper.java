package com.yun.mapper;

import com.yun.vo.GameCategoryVO;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;


@Repository
public interface GameCategoryMapper {

    @SelectProvider(type = Provider.class, method = "search")
    List<GameCategoryVO> search(Map<String, Object> keys);

    class Provider {

        public String search(Map<String, String> keys) {

            String serialId = keys.get("serialId");

            String sql = new SQL() {
                {
                    SELECT(" a.id, a.name, a.remark, a.add_time, a.serial_id, b.name as serial_name ");
                    FROM("game_category a left join serial b on a.serial_id = b.id");
                    if(Objects.nonNull(serialId)){
                        WHERE(" serial_id = " + serialId);
                    }
                    ORDER_BY("a.id");
                }
            }.toString();
            return sql;
        }

    }
}
