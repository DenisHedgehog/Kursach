package com.example.hedgehog.kursach;

import android.database.sqlite.SQLiteDatabase;

import com.example.hedgehog.kursach.database.Films;
import com.example.hedgehog.kursach.database.UsersDao;

/**
 * Created by hedgehog on 24.05.17.
 */

public class Trigger {

    private Films film;
    private SQLiteDatabase db;

    public Films getFilm() {
        return film;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public Trigger(SQLiteDatabase db, Films film) {
        this.film = film;
        this.db = db;
    }

    public void trigger() {
        getDb().execSQL("CREATE TRIGGER deleteCommentsForFilmAfterFilmDelete AFTER DELETE ON Films" +
                "FOR EACH ROW" +
                "BEGIN" +
                "DELETE FROM Comments WHERE FILM_ID = '" + getFilm().getFilmId() +"'");
    }

}
