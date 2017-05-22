package com.example.hedgehog.kursach;

import android.content.Entity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hedgehog.kursach.database.DaoMaster;
import com.example.hedgehog.kursach.database.DaoSession;
import com.example.hedgehog.kursach.database.Films;
import com.example.hedgehog.kursach.database.FilmsDao;
import com.example.hedgehog.kursach.database.Users;
import com.example.hedgehog.kursach.database.UsersDao;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {

    ListView listView = null;
    ArrayList<Film> films;

    private DaoMaster.DevOpenHelper helper;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private String activeAccount = null;

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

        helper = new DaoMaster.DevOpenHelper(this, "onlineCinemaDatabase", null);
        db = helper.getWritableDatabase();
        Toast.makeText(this, "Path: \n" + db.getPath(), Toast.LENGTH_LONG).show();
//        helper.onUpgrade(helper.getReadableDb(), 6, 7);
        daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        final FilmsDao filmsDao = daoSession.getFilmsDao();
//        filmsDao.deleteAll();

        UsersDao usersDao = daoSession.getUsersDao();
//        usersDao.deleteAll();
//        TextView textView = (TextView) findViewById(R.id.test_text_view);
//        List<Users> users = usersDao.queryBuilder().list();
//        String s = "Count of users: " + users.size() + "\n";
//        for (Users user : users) {
//            s += user.getEmail() + "    " + user.getPassword() + "\n";
//        }
//        textView.setText(s);

//        String[] firstnames = {"dmitry", "oleg", "denis", "alex", "ivan", "peter", "maria", "viktoria", "anna", "anastasia"};
//        String[] secondnames = {"hedgehog", "johnson", "black", "williams", "white", "skywalker"};
//        String[] mails = {"@mail.ru", "@gmail.com", "@yandex.ru", "@yahoo.com", "@rambler.com"};
//        int[] ages = {11, 14, 15, 16, 18, 21, 45, 25, 23, 28, 37};
//
//        TextView textView = (TextView) findViewById(R.id.test_text_view);
//        String s;
//        String sa = "";
//        usersDao.insert(new Users(null, "admin@admin.com", "admin123", 21));
//        for (int i = 0; i < 123; i++) {
//            s = generateName(firstnames, secondnames, mails);
//            if (usersDao.queryBuilder()
//                    .where(UsersDao.Properties.Email.eq(s))
//                    .build()
//                    .list()
//                    .size() == 0) {
//                Users user = new Users(null, s, generatePassword(secondnames, i), generateRandomAge(ages));
//                usersDao.insert(user);
//                sa += user.getEmail() + "    " + user.getPassword() + "\n";
//            }
//        }
//        textView.setText(sa);



        Spinner genresSpinner = (Spinner) findViewById(R.id.genres_spinner);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.list_of_genres, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genresSpinner.setAdapter(spinnerAdapter);

        genresSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            ArrayList<Films> filmes;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (parent.getItemAtPosition(position).toString()){

                    case "все жанры":
                        filmes = (ArrayList<Films>) filmsDao.loadAll();
                        break;
                    case "драма":
                        filmes = (ArrayList<Films>) filmsDao.queryBuilder().where(FilmsDao.Properties.Genres
                                .like("%" + parent.getItemAtPosition(position).toString() + "%"),
                                FilmsDao.Properties.Genres.notIn(filmsDao.queryBuilder().where(FilmsDao.Properties.Genres.like("%мелодрама%"),
                                        FilmsDao.Properties.Genres.notIn(filmsDao.queryBuilder().where(FilmsDao.Properties.Genres.like("%драма%")).list())).list())).list();
                        break;
                    default:
                        filmes = (ArrayList<Films>) filmsDao.queryBuilder().where(FilmsDao.Properties.Genres.like("%" + parent.getItemAtPosition(position).toString() + "%")).list();
                }

//                for (Films f : filmes) {
//                    f.setAgeLimit(getRandomAgeLimit());
//                    f.setYear(getRandomYear());
//                    filmsDao.update(f);
//                }

                setCustomFilmAdapter(new CustomFilmAdapter(getApplicationContext(), filmes));

//        Toast toast1 = Toast.makeText(MainActivity.this, customFilmAdapter.toString(), Toast.LENGTH_LONG);
//        toast1.show();

                try {
                    listView = (ListView) findViewById(R.id.film_list_id);
//            Toast toast1 = Toast.makeText(MainActivity.this, listView.toString(), Toast.LENGTH_LONG);
//            toast1.show();
                    listView.setAdapter(getCustomFilmAdapter());
                    Utils.setListViewHeightBasedOnChildren(listView);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent filmDetails = new Intent(getApplicationContext(), FilmDetailActivity.class);
                            filmDetails.putExtra("filmId", filmes.get(position).getFilmId());
                            startActivity(filmDetails);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast toast = Toast.makeText(MainActivity.this, "WTF, ADAPTER?" + e, Toast.LENGTH_LONG);
                    toast.show();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                setCustomFilmAdapter(new CustomFilmAdapter(getApplicationContext(), filmes));

//        Toast toast1 = Toast.makeText(MainActivity.this, customFilmAdapter.toString(), Toast.LENGTH_LONG);
//        toast1.show();

                try {
                    listView = (ListView) findViewById(R.id.film_list_id);
//            Toast toast1 = Toast.makeText(MainActivity.this, listView.toString(), Toast.LENGTH_LONG);
//            toast1.show();
                    listView.setAdapter(getCustomFilmAdapter());
                    Utils.setListViewHeightBasedOnChildren(listView);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent filmDetails = new Intent(getApplicationContext(), FilmDetailActivity.class);
                            filmDetails.putExtra("filmId", filmes.get(position).getFilmId());
                            startActivity(filmDetails);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast toast = Toast.makeText(MainActivity.this, "WTF, ADAPTER?" + e, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });



//        ConnectToDB connectToDB = null;
//        try {
//            connectToDB = new ConnectToDB(filmes);
//            filmes = connectToDB.execute().get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//
////        textView.setText("Count = " + filmes.size());
//
//        for (Films f1 : filmes) {
//            daoSession.insert(f1);
//        }





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.account_menu_id:
                Toast.makeText(MainActivity.this, "Active user is " + new ActiveUserSettings().getActiveUser(MainActivity.this), Toast.LENGTH_LONG).show();
                if (new ActiveUserSettings().getActiveUser(MainActivity.this) == null) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), UserAccountActivity.class);
                    intent.putExtra("activeUser", new ActiveUserSettings().getActiveUser(MainActivity.this));
                    startActivity(intent);
                }
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public String generateName(String[] firstnames, String[] secondnames, String[] mails) {
        return getRandom(firstnames) + getRandom(secondnames) + getRandom(mails) + "\n";
    }

    public String getRandom(String[] array) {
        Random random = new Random();
        int i = random.nextInt(array.length);
        return array[i];
    }

    public int getRandom(int[] array) {
        Random random = new Random();
        int i = random.nextInt(array.length);
        return array[i];
    }

    public String generatePassword(String[] array, int number) {
        return getRandom(array) + number;
    }

    public int generateRandomAge(int[] array) {
        return getRandom(array);
    }

    public int getRandomAgeLimit() {
        int[] array = {0, 6, 13, 16, 18};
        Random random = new Random();
        int i = random.nextInt(array.length);
        return array[i];
    }

    public int getRandomYear() {
        return new Random().nextInt(100) + 1917;
    }


}
