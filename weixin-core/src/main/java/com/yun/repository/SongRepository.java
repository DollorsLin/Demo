package com.yun.repository;


import com.yun.pojo.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SongRepository extends JpaRepository<Song, Integer> , JpaSpecificationExecutor<Song>{

    Song findSongById(Integer id);
}