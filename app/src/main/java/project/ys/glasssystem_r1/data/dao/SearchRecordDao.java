package project.ys.glasssystem_r1.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import project.ys.glasssystem_r1.data.entity.SearchRecord;


@Dao
public interface SearchRecordDao {

    @Query("select * from search_record_table order by search_time desc")
    List<SearchRecord> getAll();

    @Query("select * from search_record_table order by search_time desc limit 5")
    List<SearchRecord> getFive();

    @Query("select * from search_record_table where search_record = :record")
    SearchRecord getOne(String record);

    @Insert
    void insert(SearchRecord... entities);

    @Delete
    void delete(SearchRecord entity);

    @Delete
    void deleteAll(List<SearchRecord> entities);

    @Update
    void update(SearchRecord entity);

}
