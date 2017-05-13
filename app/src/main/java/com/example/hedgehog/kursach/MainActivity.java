package com.example.hedgehog.kursach;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.greendao.query.Query;


import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ListView listView = null;
    ArrayList<Film> films;

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

        films = new ArrayList<>();
        films.add(new Film(1, "Asfds", "JBbjkhskdjflljhkjhgfjsdkgfjkhs jskdhfjhgsf", 16, 0));
        films.add(new Film(2, "Asfds 2", "JBbjkhskdjflljhkj jjjhsd dkgfjkhs jskdhfjhgsf", 12, 3));
        films.add(new Film(3, "Asfds 3", "JBbjkhskdjf jhg hgfjsdk iu jkhs jsk hj jhgsf", 18, 5));

        setCustomFilmAdapter(new CustomFilmAdapter(getApplicationContext(), films));

//        Toast toast1 = Toast.makeText(MainActivity.this, customFilmAdapter.toString(), Toast.LENGTH_LONG);
//        toast1.show();

        try {
            listView = (ListView) findViewById(R.id.film_list_id);
//            Toast toast1 = Toast.makeText(MainActivity.this, listView.toString(), Toast.LENGTH_LONG);
//            toast1.show();
            listView.setAdapter(getCustomFilmAdapter());
            Utils.setListViewHeightBasedOnChildren(listView);
        } catch (Exception e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(MainActivity.this, "WTF, ADAPTER?" + e, Toast.LENGTH_LONG);
            toast.show();
        }

        String s = "mmm";
        TextView textView = (TextView) findViewById(R.id.test_text_view);
//        Toast toast1 = Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG);
        ConnectToDB connectToDB = new ConnectToDB(textView);
        connectToDB.execute();
//        toast1.show();

    }

}
