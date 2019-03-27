package project.ys.glasssystem_r1.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import project.ys.glasssystem_r1.data.entity.Alarm;


@Dao
public interface AlarmDao {

    @Query("select * from alarm_table  where receiver_uuid = :account order by alarm_createTime desc")
    List<Alarm> getAll(String account);

    @Query("select * from alarm_table where receiver_uuid = :account  order by receiver_haveRead asc , alarm_createTime desc")
    List<Alarm> getAllByRead(String account);


    @Query("select * from alarm_table where receiver_uuid = :account and alarm_title like :search or alarm_content like  :search order by alarm_createTime desc")
    List<Alarm> findWithReceiverAndSearchText(String account, String search);

    @Query("select * from alarm_table where receiver_uuid = :account and alarm_title like :search or alarm_content like  :search order by receiver_haveRead asc , alarm_createTime desc")
    List<Alarm> findWithReceiverAndSearchTextOrderByRead(String account, String search);

    @Insert
    void insert(Alarm... entities);

    @Delete
    void delete(Alarm entity);

    @Delete
    void deleteAll(List<Alarm> entities);

    @Update
    void update(Alarm entity);

}
