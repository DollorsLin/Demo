package com.yun.mapper;

import com.yun.pojo.ProductToUser;
import com.yun.vo.PlayDetailVO;
import com.yun.vo.PlayMusicVO;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PlayDetailMapper {

    @SelectProvider(type = Provider.class, method = "list")
    List<PlayDetailVO> list(Integer userId);

    @SelectProvider(type = Provider.class, method = "difficultyList")
    List<PlayMusicVO> difficultyList(Integer userId);



    class Provider {

        public String list(Integer userId) {
            String sql = new SQL() {
                {
                    SELECT(" c.song_name,max(a.score) as score,b.role_name,a.role_id,a.song_id");
                    FROM("play_detail a ");
                    LEFT_OUTER_JOIN("role b on b.id = a.role_id");
                    LEFT_OUTER_JOIN("song c on c.id = a.song_id");
                    WHERE("a.user_id = " + userId);
                    GROUP_BY("b.id,a.song_id ");
                    ORDER_BY("b.id");
                }
            }.toString();
            return sql;
        }

        public String difficultyList(Integer userId) {
            String sql = new SQL() {
                {
                    SELECT(" a.difficulty,c.song_name,max(a.score) as score,b.role_name,a.role_id,a.song_id");
                    FROM("play_detail a ");
                    LEFT_OUTER_JOIN("role b on b.id = a.role_id");
                    LEFT_OUTER_JOIN("song c on c.id = a.song_id");
                    WHERE("a.user_id = " + userId);
                    GROUP_BY("b.id,a.song_id,a.difficulty ");
                    ORDER_BY("b.id");
                }
            }.toString();
            return sql;
        }



    }
}
