package com.yun.service;

import com.yun.pojo.Song;
import com.yun.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 歌曲表
 *
 */
@Service
public class  SongService {

    @Autowired
    private SongRepository songRepository;

    public Song save(Song song) {
        return songRepository.save(song);
    }

    public List<Song> list( Map<String, Object> params){
        List<Song> list = songRepository.findAll();
        return list;
    }


    public Song getById(Integer id) {
        return songRepository.findSongById(id);
    }

    public void removeByIds(List<Integer> ids) {
        for (Integer id: ids){
            songRepository.deleteById(id);
        }
    }
}

