package com.example.hedgehog.kursach;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hedgehog.kursach.database.Comments;
import com.example.hedgehog.kursach.database.CommentsDao;
import com.example.hedgehog.kursach.database.DaoMaster;
import com.example.hedgehog.kursach.database.DaoSession;
import com.example.hedgehog.kursach.database.Films;
import com.example.hedgehog.kursach.database.FilmsDao;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FilmDetailActivity extends AppCompatActivity {

    private DaoMaster.DevOpenHelper helper;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    FilmsDao filmsDao = null;
    Films film = null;
    Comments comment = null;
    ArrayList<Comments> comments = null;
    CommentsDao commentsDao = null;
    CustomCommentAdapter customCommentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);

        helper = new DaoMaster.DevOpenHelper(this, "onlineCinemaDatabase", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        filmsDao = daoSession.getFilmsDao();
        commentsDao = daoSession.getCommentsDao();

        film = filmsDao.queryBuilder()
                .where(FilmsDao.Properties.FilmId.eq(getIntent().getExtras().get("filmId")))
                .list()
                .get(0);

        setTitle(film.getName());

        ImageView filmImageView = (ImageView) findViewById(R.id.film_image_view);
        TextView filmYear = (TextView) findViewById(R.id.film_year);
        TextView filmGenres = (TextView) findViewById(R.id.film_genres);
        TextView filmDescription = (TextView) findViewById(R.id.film_description);
        TextView filmPrice = (TextView) findViewById(R.id.film_price);
        TextView commentsTextView = (TextView) findViewById(R.id.comments_text_view);
        ListView commentsListView = (ListView) findViewById(R.id.comments_list_view);
        final EditText commentEditText = (EditText) findViewById(R.id.new_comment_edit_text);
        Button sendCommentButton = (Button) findViewById(R.id.send_comment_button);

        comments = (ArrayList<Comments>) commentsDao.queryBuilder().where(CommentsDao.Properties.FilmId.eq(film.getFilmId())).list();

        customCommentAdapter = new CustomCommentAdapter(FilmDetailActivity.this, comments);
        commentsListView.setAdapter(customCommentAdapter);

        Utils.setListViewHeightBasedOnChildren(commentsListView);

        commentsTextView.setText("Комментарии (" + comments.size() + "):");

        sendCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commentEditText.getText().length() > 0) {
                    commentsDao.insert(new Comments(null, commentEditText.getText().toString(), new ActiveUserSettings().getActiveUserId(FilmDetailActivity.this), film.getFilmId()));
                    Toast.makeText(FilmDetailActivity.this, "Комментарий отправлен", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(FilmDetailActivity.this, "Комментарий не должен быть пустым", Toast.LENGTH_LONG).show();
                }
            }
        });

        Picasso.with(getBaseContext())
                .load(film.getImageUrl())
                .resize(900, 1440)
                .into(filmImageView);

        filmYear.setText(film.getName() + " (" + film.getYear() + ", " + film.getAgeLimit() + "+)");
        filmGenres.setText("Жанры: " + film.getGenres());
//        filmName.setText(film.getName());
        filmDescription.setText("Описание: " + film.getDescription());
        if (film.getPrice() == 0) {
            filmPrice.setText("Цена: бесплатно");
        } else {
            filmPrice.setText("Цена: " + film.getPrice());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.film_menu, menu);
        if (new ActiveUserSettings().getActiveUser(FilmDetailActivity.this) != null && new ActiveUserSettings().getActiveUser(FilmDetailActivity.this).equals("admin@admin.com")) {
            menu.getItem(0).setEnabled(true);
            menu.getItem(0).setVisible(true);
        } else {
            menu.getItem(0).setEnabled(false);
            menu.getItem(0).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.change_film:
                final AlertDialog.Builder builder = new AlertDialog.Builder(FilmDetailActivity.this);
                LayoutInflater inflater = LayoutInflater.from(FilmDetailActivity.this);

                View view = inflater.inflate(R.layout.add_or_change_film_alert_dialog, null);
                final EditText nameEditText = (android.widget.EditText) view.findViewById(R.id.new_film_name);
                final EditText genresEditText = (android.widget.EditText) view.findViewById(R.id.new_film_genres);
                final EditText yearEditText = (android.widget.EditText) view.findViewById(R.id.new_film_year);
                final EditText ageLimitEditText = (android.widget.EditText) view.findViewById(R.id.new_film_age_limit);
                final EditText descriptionEditText = (android.widget.EditText) view.findViewById(R.id.new_film_descriptiom);
                final EditText priceEditText = (android.widget.EditText) view.findViewById(R.id.new_film_price);
                final EditText imageUrlEditText = (android.widget.EditText) view.findViewById(R.id.new_film_image_url);

                nameEditText.setText(film.getName());
                genresEditText.setText(film.getGenres());
                if (film.getYear() > 0) {
                    yearEditText.setText("" + film.getYear() + "", TextView.BufferType.EDITABLE);
                } else  {
                    yearEditText.setText("0", TextView.BufferType.EDITABLE);
                }
                ageLimitEditText.setText("" + film.getAgeLimit() + "", TextView.BufferType.EDITABLE);
                descriptionEditText.setText(film.getDescription());
                priceEditText.setText("" + film.getPrice() + "", TextView.BufferType.EDITABLE);
                imageUrlEditText.setText(film.getImageUrl());

                builder.setView(view)
                        .setCancelable(false)
                        .setPositiveButton("Изменить", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (nameEditText.getText().toString().length() > 0) {

                                    if (descriptionEditText.getText().toString().length() > 0) {

                                        int ageLimit;
                                        if (ageLimitEditText.getText().toString().length() > 0) {
                                            ageLimit = Integer.parseInt(ageLimitEditText.getText().toString());
                                        } else {
                                            ageLimit = 0;
                                        }

                                        String imageUrl;
                                        if (imageUrlEditText.getText().toString().length() > 0) {
                                            imageUrl = imageUrlEditText.getText().toString();
                                        } else {
                                            imageUrl = "https://maxcdn.icons8.com/Share/icon/Photo_Video//film_21600.png";
                                        }

                                        int price;
                                        if(priceEditText.getText().toString().length() > 0) {
                                            price = Integer.parseInt(priceEditText.getText().toString());
                                        } else {
                                            price = 0;
                                        }

                                        film.setName(nameEditText.getText().toString());
                                        film.setDescriprion(descriptionEditText.getText().toString());
                                        film.setAgeLimit(ageLimit);
                                        film.setGenres(genresEditText.getText().toString());
                                        film.setPrice(price);
                                        film.setImageUrl(imageUrl);
                                        film.setYear(Integer.parseInt(yearEditText.getText().toString()));

                                        filmsDao.update(film);

                                        Toast.makeText(FilmDetailActivity.this, "Фильм успешно изменён", Toast.LENGTH_LONG).show();

                                    } else {
                                        Toast.makeText(FilmDetailActivity.this, "Введите описание", Toast.LENGTH_LONG).show();
                                    }

                                } else {
                                    Toast.makeText(FilmDetailActivity.this, "Введите название название", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNeutralButton("Отмена", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Удалить", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                commentsDao.deleteInTx(commentsDao.queryBuilder().where(CommentsDao.Properties.FilmId.eq(film.getFilmId())).list());
                                filmsDao.delete(film);
                                dialog.cancel();
                                FilmDetailActivity.this.onBackPressed();
                                Toast.makeText(FilmDetailActivity.this, "Фильм успешно удалён", Toast.LENGTH_LONG).show();
                            }
                        }).show();
                break;
            default:
                super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostResume() {
        invalidateOptionsMenu();
        super.onPostResume();
    }
}
