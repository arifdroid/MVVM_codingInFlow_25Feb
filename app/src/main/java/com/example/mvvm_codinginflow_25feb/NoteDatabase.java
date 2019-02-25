package com.example.mvvm_codinginflow_25feb;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Note.class},version = 1) //migration strategy

public abstract class NoteDatabase extends RoomDatabase {



    //>> INSERT AFTER REPOSITORY
    // we populate database right after we created.
    // we start with table with already notes in it.
    // >> CONTINUE BELOW



    //we gonna create this class into singleton
    //means only one instance everywhere



    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context){

        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                        NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)  //use to create onCreate, populate database 
                    .build();
        }

        return instance;
    }


    //>> INSERT AFTER REPOSITORY
    // we populate database right after we created.
    // we start with table with already notes in it.
    //need to do it in asynctask
    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new PopulateDbAsycntask(instance).execute();

        }
    };

    private static class PopulateDbAsycntask extends AsyncTask<Void,Void,Void>{

        private NoteDao noteDao;

        private PopulateDbAsycntask(NoteDatabase db){
            noteDao= db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title 1","Description 1", 1));
            noteDao.insert(new Note("Title 2","Description 2", 2));
            noteDao.insert(new Note("Title 3","Description 3", 3));
            return null;
        }
    }


}
