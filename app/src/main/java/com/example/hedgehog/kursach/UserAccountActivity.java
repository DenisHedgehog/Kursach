package com.example.hedgehog.kursach;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hedgehog.kursach.database.DaoMaster;
import com.example.hedgehog.kursach.database.DaoSession;
import com.example.hedgehog.kursach.database.Users;
import com.example.hedgehog.kursach.database.UsersDao;

public class UserAccountActivity extends AppCompatActivity {

    private static final String title = "Личный кабинет";
    private static final String STATE_ACCOUNT = "activeUser";

    private DaoMaster.DevOpenHelper helper;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private String activeAccount = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        setTitle(title);

        helper = new DaoMaster.DevOpenHelper(this, "onlineCinemaDatabase", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();

        final UsersDao usersDao = daoSession.getUsersDao();

        TextView userIdTextView = (TextView) findViewById(R.id.user_id_text_view);
        TextView userEmailTextView = (TextView) findViewById(R.id.user_email_text_view);
        TextView userAgeTextView = (TextView) findViewById(R.id.user_age_text_view);

        Button userFavoriteFilmsButton = (Button) findViewById(R.id.user_favorite_films_button);
        Button userWatchedFilmsButton = (Button) findViewById(R.id.user_watched_films_button);
        Button changePasswordButton = (Button) findViewById(R.id.change_password_button);
        Button exitAccountButton = (Button) findViewById(R.id.exit_account_button);

        exitAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ActiveUserSettings().removeActiveUser(UserAccountActivity.this);
                UserAccountActivity.super.onBackPressed();
            }
        });

        Users user = usersDao.queryBuilder().where(UsersDao.Properties.Email.eq(getIntent().getStringExtra("activeUser"))).build().list().get(0);

        userIdTextView.setText("Идентификатор пользователя: " + user.getUserId());
        userEmailTextView.setText("Электронная почта: " + user.getEmail());
        userAgeTextView.setText("Возраст: " + user.getAge());

    }
}
