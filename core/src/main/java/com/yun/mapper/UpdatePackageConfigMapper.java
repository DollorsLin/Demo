package com.yun.mapper;

import com.yun.pojo.UpdatePackageConfig;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;


@Repository
public interface UpdatePackageConfigMapper {

    @SelectProvider(type = Provider.class, method = "get")
    UpdatePackageConfig get(Integer updatePackageId, String serialNumber);


    class Provider {
        public String get(Integer updatePackageId, String serialNumber) {
            String sql = new SQL() {
                {
                    SELECT(" * ");
                    FROM("update_package_config");
                    WHERE( " is_deleted = 0 ");
                    WHERE( " update_package_id = " + updatePackageId);
                    WHERE( " serial_numbers like '%" + serialNumber +"%'");
                }
            }.toString();
            return sql + " limit 1";
        }
    }

}
