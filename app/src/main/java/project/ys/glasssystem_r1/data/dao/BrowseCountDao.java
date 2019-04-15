package project.ys.glasssystem_r1.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import project.ys.glasssystem_r1.data.entity.BrowseCount;
import project.ys.glasssystem_r1.data.entity.Push;
import project.ys.glasssystem_r1.data.entity.SearchRecord;


@Dao
public interface BrowseCountDao {

    @Query("select * from browse_count_table where receiver_uuid = :receive")
    List<BrowseCount> getAll(String receive);

    @Query("select * from browse_count_table where receiver_uuid = :no and push_tag = :tag")
    BrowseCount findByNoAndTag(String no,String tag);
    @Insert
    void insert(BrowseCount... entities);

    @Delete
    void delete(BrowseCount entity);

    @Delete
    void deleteAll(List<BrowseCount> entities);

    @Update
    void update(BrowseCount entity);

}
