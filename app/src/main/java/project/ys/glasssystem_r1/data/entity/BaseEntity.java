package project.ys.glasssystem_r1.data.entity;

import android.arch.persistence.room.PrimaryKey;

public class BaseEntity {
    @PrimaryKey(autoGenerate = true)
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
