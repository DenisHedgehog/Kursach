package com.example.hedgehog.kursach;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hedgehog.kursach.database.DaoMaster;
import com.example.hedgehog.kursach.database.DaoSession;
import com.example.hedgehog.kursach.database.FilmsDao;
import com.example.hedgehog.kursach.database.Users;
import com.example.hedgehog.kursach.database.UsersDao;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final String title = "Авторизация";
    private static final String STATE_ACCOUNT = "activeUser";

    private DaoMaster.DevOpenHelper helper;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private String activeAccount = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ActiveUserSettings au = new ActiveUserSettings();
//        String acUs = au.getActiveUser(LoginActivity.this);
//
//        if (acUs != null) {
//            activeAccount = acUs;
//            Intent intent = new Intent(getApplicationContext(), UserAccountActivity.class);
//            intent.putExtra("activeUser", activeAccount);
//            startActivity(intent);
//        }

        setContentView(R.layout.activity_login);

        setTitle(title);

        helper = new DaoMaster.DevOpenHelper(this, "onlineCinemaDatabase", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();

        final UsersDao usersDao = daoSession.getUsersDao();

        final EditText emailTextEdit = (EditText) findViewById(R.id.login_edit_id);
        final EditText passwordTextEdit = (EditText) findViewById(R.id.password_edit_id);
        Button enterButton = (Button) findViewById(R.id.login_button_id);
        TextView createAccountTextView = (TextView) findViewById(R.id.registation_text_view_id);

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Users> usersList = usersDao.queryBuilder().where(UsersDao.Properties.Email.eq(emailTextEdit.getText()), UsersDao.Properties.Password.eq(passwordTextEdit.getText().toString())).build().list();
                if (usersList.size() == 1) {
                    activeAccount = usersList.get(0).getEmail();
                    Intent intent = new Intent(getApplicationContext(), UserAccountActivity.class);
                    intent.putExtra("activeUser", activeAccount);
                    ActiveUserSettings activeUserSettings = new ActiveUserSettings();
                    activeUserSettings.saveActiveUser(LoginActivity.this, activeAccount);
                    Toast.makeText(LoginActivity.this, "Active user is " + new ActiveUserSettings().getActiveUser(LoginActivity.this), Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    LoginActivity.this.finish();
                } else {
                    Toast toast = Toast.makeText(getBaseContext(), "Неверные данные", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        createAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });

    }

}
