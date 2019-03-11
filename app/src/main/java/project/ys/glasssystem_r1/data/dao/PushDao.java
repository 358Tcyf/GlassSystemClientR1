package project.ys.glasssystem_r1.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import project.ys.glasssystem_r1.data.entity.Push;


@Dao
public interface PushDao {

    @Query("select * from push_table order by push_createTime asc")
    List<Push> getAll();

    @Query("select * from push_table where id = :id")
    Push getOne(int id);

    @Insert
    void insert(Push... entities);

    @Delete
    void delete(Push entity);

    @Delete
    void deleteAll(List<Push> entities);

    @Update
    void update(Push entity);

}
