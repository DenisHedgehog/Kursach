package com.example.hedgehog.kursach;

import android.content.Entity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hedgehog.kursach.database.DaoMaster;
import com.example.hedgehog.kursach.database.DaoSession;
import com.example.hedgehog.kursach.database.Films;
import com.example.hedgehog.kursach.database.FilmsDao;


import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {

    ListView listView = null;
    ArrayList<Film> films;

    private DaoMaster.DevOpenHelper helper;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;

    public void setCustomFilmAdapter(CustomFilmAdapter customFilmAdapter) {
        this.customFilmAdapter = customFilmAdapter;
    }

    public CustomFilmAdapter getCustomFilmAdapter() {
        return customFilmAdapter;
    }

    private CustomFilmAdapter customFilmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

//        DaoSession daoSession = ((App) getApplication()).getDaoSession();
//        noteDao = daoSession.getNoteDao();
//
//        DevOpenHelper helper = new DevOpenHelper(this, "notes-db");
//        Database db = helper.getWritableDb();
//        daoSession = new DaoMaster(db).newSession();

        helper = new DaoMaster.DevOpenHelper(this, "onlineCinemaDatabase", null);
        db = helper.getWritableDatabase();
//        helper.onUpgrade(helper.getReadableDb(), 6, 7);
        daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        FilmsDao filmsDao = daoSession.getFilmsDao();
        filmsDao.deleteAll();


//        Films films = new Films();
//        films.setName("Test name");
//        films.setDescriprion("Test description");
//        films.setAgeLimit(13);

//        TextView textView = (TextView) findViewById(R.id.test_text_view);

//        daoSession.insert(films);

//        Films f = new Films(null, "Name test", "test genres", "Description text", 2007, 16, 0, null);
//        daoSession.insert(f);

        ArrayList<Films> filmes = (ArrayList<Films>) filmsDao.loadAll();
        ConnectToDB connectToDB = null;
        try {
            connectToDB = new ConnectToDB(filmes);
            filmes = connectToDB.execute().get();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

//        textView.setText("Count = " + filmes.size());

        for (Films f1 : filmes) {
            daoSession.insert(f1);
        }


        setCustomFilmAdapter(new CustomFilmAdapter(getApplicationContext(), filmes));

//        Toast toast1 = Toast.makeText(MainActivity.this, customFilmAdapter.toString(), Toast.LENGTH_LONG);
//        toast1.show();

        try {
            listView = (ListView) findViewById(R.id.film_list_id);
//            Toast toast1 = Toast.makeText(MainActivity.this, listView.toString(), Toast.LENGTH_LONG);
//            toast1.show();
            listView.setAdapter(getCustomFilmAdapter());
            Utils.setListViewHeightBasedOnChildren(listView);
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Intent filmDetails = new Intent(getApplicationContext(), FilmDetailActivity.class);
//                    filmDetails.putExtra("filmId", filmes.get(position).getFilmId());
//                    startActivity(filmDetails);
//                }
//            });

        } catch (Exception e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(MainActivity.this, "WTF, ADAPTER?" + e, Toast.LENGTH_LONG);
            toast.show();
        }

//        Toast toast1 = Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG);

//        toast1.show();

    }

}
