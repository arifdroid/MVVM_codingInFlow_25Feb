package com.example.mvvm_codinginflow_25feb;

//will have to interfaces or abstract,
//no body in method will be created
//because room will generate it for us

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

//generally good to create on dao per one entity or table.

@Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    //here we define operation ourself from query
    @Query("DELETE FROM note_table")
    void deleteAllNote();

    @Query("SELECT *FROM note_table ORDER BY priority DESC")
    LiveData<List<Note>> getAllNotes();

    //with livedata, any changes in note table, activity will be notified.
    //value livedata also updated


}
