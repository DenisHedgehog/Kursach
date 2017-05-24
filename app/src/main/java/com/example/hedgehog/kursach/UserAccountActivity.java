package com.example.hedgehog.kursach;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        final DaoSession daoSession = daoMaster.newSession();

        final UsersDao usersDao = daoSession.getUsersDao();

        TextView userIdTextView = (TextView) findViewById(R.id.user_id_text_view);
        TextView userEmailTextView = (TextView) findViewById(R.id.user_email_text_view);
        TextView userAgeTextView = (TextView) findViewById(R.id.user_age_text_view);

        Button changePasswordButton = (Button) findViewById(R.id.change_password_button);
        Button exitAccountButton = (Button) findViewById(R.id.exit_account_button);

        exitAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ActiveUserSettings().removeActiveUser(UserAccountActivity.this);
                UserAccountActivity.super.onBackPressed();
            }
        });

        changePasswordButton.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(UserAccountActivity.this);
                LayoutInflater inflater = UserAccountActivity.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.change_password_alert_dialog, null);
                final EditText oldPasswordEditText = (android.widget.EditText) view.findViewById(R.id.old_password);
                final EditText newPasswordEditText = (android.widget.EditText) view.findViewById(R.id.new_password);
                final EditText repeatNewPasswordEditText = (android.widget.EditText) view.findViewById(R.id.repeat_new_password);
                builder.setView(view)
                        .setCancelable(false)
                        .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (oldPasswordEditText.getText().toString()
                                        .equals(usersDao.load(new ActiveUserSettings().getActiveUserId(UserAccountActivity.this)).getPassword())) {

                                    if (newPasswordEditText.getText().toString().equals(repeatNewPasswordEditText.getText().toString()) &&
                                            newPasswordEditText.getText().toString().length() > 5) {

                                        Users user = usersDao.load(new ActiveUserSettings().getActiveUserId(UserAccountActivity.this));
                                        user.setPassword(newPasswordEditText.getText().toString());
                                        usersDao.update(user);
                                        Toast.makeText(getApplicationContext(), "Пароль успешно изменён", Toast.LENGTH_LONG).show();
                                        dialog.cancel();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Новые пароли не совпадают", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Неверно введен старый пароль", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).create().show();

            }
        });

        Users user = usersDao.queryBuilder().where(UsersDao.Properties.Email.eq(getIntent().getStringExtra("activeUser"))).build().list().get(0);

        userIdTextView.setText("Идентификатор пользователя: " + user.getUserId());
        userEmailTextView.setText("Электронная почта: " + user.getEmail());
        userAgeTextView.setText("Возраст: " + user.getAge());

    }

    @Nullable
    @Override
    protected Dialog onCreateDialog(int id, Bundle args) {
        return super.onCreateDialog(id, args);
    }
}
