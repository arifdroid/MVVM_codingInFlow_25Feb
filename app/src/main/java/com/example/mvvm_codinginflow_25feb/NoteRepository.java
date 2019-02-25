package com.example.mvvm_codinginflow_25feb;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application){

        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();   //is the singleton,, created by room libraries

        allNotes = noteDao.getAllNotes(); //from interface NoteDao

    }

    //other database operation. we need to specify ourselves

    //background task, aysnctask.

    public void insert(Note note){

        new InsertNoteAsyncTask(noteDao).execute(note);

    }
    public void delete(Note note){

        new DeleteNoteAsyncTask(noteDao).execute(note);

    }
    public void update(Note note){
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }
    public void deleteAllNotes(Note note){
        new DeleteNoteAsyncTask(noteDao).execute();

    }


    ///////////// these up here are the API that repository expose to the outside
    ///so view model has just to call, update, insert , delete, deleteall etc.

    //below is automatically takes care by room, background thread
    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }

    ///////// asynctask DO IN BACKGROUND THREAD. SERVICES.

    private static class InsertNoteAsyncTask extends AsyncTask<Note,Void,Void>{

        //need this to make database operation
        private NoteDao noteDao;


        //constructor since, aync class is static, we cant access NoteDao directly, so we pass over constructor
        private InsertNoteAsyncTask(NoteDao noteDao){
        this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {

            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note,Void,Void>{

        //need this to make database operation
        private NoteDao noteDao;


        //constructor since, aync class is static, we cant access NoteDao directly, so we pass over constructor
        private UpdateNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {

            noteDao.update(notes[0]);
            return null;
        }
    }
    private static class DeleteNoteAsyncTask extends AsyncTask<Note,Void,Void>{

        //need this to make database operation
        private NoteDao noteDao;


        //constructor since, aync class is static, we cant access NoteDao directly, so we pass over constructor
        private DeleteNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {

            noteDao.delete(notes[0]);
            return null;
        }
    }
    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void,Void,Void>{

        //need this to make database operation
        private NoteDao noteDao;


        //constructor since, aync class is static, we cant access NoteDao directly, so we pass over constructor
        private DeleteAllNoteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... notes) {

            noteDao.deleteAllNote();
            return null;
        }
    }
}
