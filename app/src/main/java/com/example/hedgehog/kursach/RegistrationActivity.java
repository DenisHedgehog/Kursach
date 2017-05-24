package com.example.hedgehog.kursach;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hedgehog.kursach.database.DaoMaster;
import com.example.hedgehog.kursach.database.DaoSession;
import com.example.hedgehog.kursach.database.Users;
import com.example.hedgehog.kursach.database.UsersDao;

public class RegistrationActivity extends AppCompatActivity {

    private static final String title = "Регистрация";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        setTitle(title);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "onlineCinemaDatabase", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        final UsersDao usersDao = daoSession.getUsersDao();

        final EditText emailEditText = (EditText) findViewById(R.id.registration_email_edit_text);
        final EditText passwordEditText = (EditText) findViewById(R.id.registration_password_edit_text);
        final EditText repeatPasswordEditText = (EditText) findViewById(R.id.registration_repeat_password_edit_text);
        final EditText ageEditText = (EditText) findViewById(R.id.registration_age_edit_text);

        Button createAccountButton = (Button) findViewById(R.id.create_account_id);
        Button cancelButton = (Button) findViewById(R.id.cancel_button_id);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmail(emailEditText.getText().toString()) && emailEditText.getText() != null) {
                    if (passwordIsEquals(passwordEditText.getText().toString(), repeatPasswordEditText.getText().toString()) && (passwordEditText.getText().toString().length() > 5) && (repeatPasswordEditText.getText().toString().length() > 5)) {
                        if (isInteger(ageEditText.getText().toString()) && (ageEditText.getText() != null)) {
                            if (usersDao.queryBuilder().where(UsersDao.Properties.Email.eq(emailEditText.getText().toString()))
                                    .build()
                                    .list()
                                    .size() == 0) {
                                Users user = new Users(null, emailEditText.getText().toString(), passwordEditText.getText().toString(), Integer.parseInt(ageEditText.getText().toString()));
                                usersDao.insert(user);
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                RegistrationActivity.this.finish();
                                Toast.makeText(getBaseContext(), "Создан пользователь: \n" + user.toString(), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getBaseContext(), "Пользователь с таким email уже существует", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getBaseContext(), "Неверный возраст", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "Пароли не совпадают", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Неверный email", Toast.LENGTH_LONG).show();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrationActivity.super.onBackPressed();
            }
        });

    }

    public boolean isEmail(String email) {
        char[] chars = email.toCharArray();
        int emailCharCount = 0;
        int endingCount = 0;
        String[] strings = {".ru", ".com"};
        for (char c : chars) {
            if (c == '@') {
                emailCharCount++;
            }
        }
        for (String s : strings) {
            if (email.contains(s)) {
                endingCount++;
            }
        }
        if (emailCharCount == 1 && endingCount > 0) {
            return true;
        } else {
            return  false;
        }
    }

    public boolean passwordIsEquals(String pass, String repeatPass) {
        if (pass.equals(repeatPass)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isInteger(String s) throws NumberFormatException {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
