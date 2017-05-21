package com.example.hedgehog.kursach;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hedgehog.kursach.database.DaoMaster;
import com.example.hedgehog.kursach.database.DaoSession;
import com.example.hedgehog.kursach.database.Films;
import com.example.hedgehog.kursach.database.FilmsDao;
import com.squareup.picasso.Picasso;

public class FilmDetailActivity extends AppCompatActivity {

    private DaoMaster.DevOpenHelper helper;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);

        helper = new DaoMaster.DevOpenHelper(this, "onlineCinemaDatabase", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        FilmsDao filmsDao = daoSession.getFilmsDao();
        Films film = filmsDao.queryBuilder()
                .where(FilmsDao.Properties.FilmId.eq(getIntent().getExtras().get("filmId")))
                .list()
                .get(0);

        setTitle(film.getName());

        ImageView filmImageView = (ImageView) findViewById(R.id.film_image_view);
        TextView filmYear = (TextView) findViewById(R.id.film_year);
        TextView filmGenres = (TextView) findViewById(R.id.film_genres);
//        TextView filmName = (TextView) findViewById(R.id.film_name);
        TextView filmDescription = (TextView) findViewById(R.id.film_description);



        Picasso.with(getBaseContext())
                .load(film.getImageUrl())
                .resize(900, 1440)
                .into(filmImageView);

        filmYear.setText(film.getName() + " (" + film.getAgeLimit() + "+)");
        filmGenres.setText("Жанры: " + film.getGenres());
//        filmName.setText(film.getName());
        filmDescription.setText("Описание: " + film.getDescription());


    }
}
