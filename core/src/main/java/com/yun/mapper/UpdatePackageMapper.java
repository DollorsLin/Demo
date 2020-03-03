package com.yun.mapper;

import com.yun.pojo.UpdatePackage;
import com.yun.vo.UpdatePackageVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;


@Repository
public interface UpdatePackageMapper {

    @SelectProvider(type = Provider.class, method = "search")
    List<UpdatePackageVO> search(Map<String, String> keys);

    @SelectProvider(type = Provider.class, method = "getNewPackage")
    List<UpdatePackage> getNewPackage(Integer categoryId, Integer version, Integer productCategoryId);

    @SelectProvider(type = Provider.class, method = "findResourseUpdateByCategoryIdAndVersion")
    List<UpdatePackage> findResourseUpdateByCategoryIdAndVersion(Integer categoryId, Integer version);



    @SelectProvider(type = Provider.class, method = "findNewManualUpdatePackage")
    List<UpdatePackageVO> findNewManualUpdatePackage(Integer productCategoryId);

    /**
     * 升级游戏类型，比如收费之后，换游戏
     * @param categoryId
     * @return
     */
    @SelectProvider(type = Provider.class, method = "findNewGamePackage")
    UpdatePackage findNewGamePackage(Integer categoryId);


    /**
     * 更新包的数量
     * @param categoryId
     * @return
     */
    @SelectProvider(type = Provider.class, method = "countUpdatePackage")
    Integer countUpdatePackage(Integer categoryId, Integer version, Integer productCategoryId);


    class Provider {
        public String search(Map<String, String> keys) {
            String sql = new SQL() {
                {
                    SELECT(" a.id, a.category_id, a.product_category_id, a.version, a.file_path, a.add_time, a.remark, a.is_deleted, a.package_type, a.is_auto, a.md5, b.category_name as product_category_name, c.name as category_name");
                    FROM("update_package a left join product_category b ON a.product_category_id = b.id left join game_category c ON a.category_id = c.id");
                    WHERE( " a.is_deleted = 0 ");
                }
            }.toString();
            return sql;
        }

        public String getNewPackage(Integer categoryId, Integer version, Integer productCategoryId) {
            String sql = new SQL() {
                {
                    SELECT(" * ");
                    FROM("update_package");
                    WHERE( " is_deleted = 0 ");
                    WHERE(" category_id = " + categoryId);
                    WHERE(" product_category_id = " + productCategoryId);

                    if(Objects.nonNull(version)){
                        WHERE(" version > " + version);
                    }
                    WHERE(" is_auto = " + 0);
                    ORDER_BY(" id desc limit 1");
                }
            }.toString();
            return sql;
        }

        public String findResourseUpdateByCategoryIdAndVersion(Integer categoryId, Integer version) {
            String sql = new SQL() {
                {
                    SELECT(" * ");
                    FROM("update_package");
                    WHERE( " is_deleted = 0 ");
                    WHERE(" category_id = " + categoryId);
                    WHERE(" version > " + version);
                    WHERE(" package_type = 2");
                    WHERE(" is_auto = " + 0);
                    ORDER_BY(" id desc limit 1");
                }
            }.toString();
            return sql;
        }

// TODO 获取不同游戏类型的最新的手动更新包
        public String findNewManualUpdatePackage(Integer productCategoryId) {
            String sql = new SQL() {
                {
                    SELECT(" a.id, a.category_id, a.product_category_id, a.version, a.file_path, a.add_time, a.remark, a.is_deleted, a.package_type, a.is_auto, a.md5, b.category_name as product_category_name, c.name as category_name  ");
                    FROM("update_package a left join product_category b ON a.product_category_id = b.id left join game_category c ON a.category_id = c.id");
                    WHERE( " a.is_deleted = 0 ");
                    WHERE(" a.product_category_id = " + productCategoryId);
                    WHERE(" a.is_auto = " + 1);
                    ORDER_BY(" a.id desc");
                }
            }.toString();
            return sql;
        }

        public String findNewGamePackage(Integer categoryId) {
            String sql = new SQL() {
                {
                    SELECT(" * ");
                    FROM("update_package");
                    WHERE( " is_deleted = 0 ");
                    WHERE(" category_id = " + categoryId);
                    WHERE(" package_type = 1");
                    ORDER_BY(" id desc limit 1");
                }
            }.toString();
            return sql;
        }

        public String countUpdatePackage(Integer categoryId, Integer version, Integer productCategoryId) {
            String sql = new SQL() {
                {
                    SELECT(" count(*) ");
                    FROM("update_package");
                    WHERE( " is_deleted = 0 ");
                    WHERE( " is_auto = 0 ");
                    WHERE(" category_id = " + categoryId);
                    WHERE(" product_category_id = " + productCategoryId);
                    WHERE(" version > " + version);
                    ORDER_BY(" id desc limit 1");
                }
            }.toString();
            return sql;
        }

    }

}
