package com.example.hedgehog.kursach;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;

import com.example.hedgehog.kursach.database.DaoMaster;
import com.example.hedgehog.kursach.database.DaoSession;
import com.example.hedgehog.kursach.database.Users;
import com.example.hedgehog.kursach.database.UsersDao;

import java.util.List;

/**
 * Created by hedgehog on 21.05.17.
 */

public class ActiveUserSettings {

    SharedPreferences sPref;

    final static String ACTIVE_USER = "activeUser";

    public void saveActiveUser(Activity activity, String activeUser) {
        sPref = activity.getSharedPreferences("activeUserConfig", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString(ACTIVE_USER, activeUser);
        editor.commit();
    }

    public String getActiveUser(Activity activity) {
        sPref = activity.getSharedPreferences("activeUserConfig", Context.MODE_PRIVATE);
        return sPref.getString(ACTIVE_USER, null);
    }

    public void removeActiveUser(Activity activity) {
        sPref = activity.getSharedPreferences("activeUserConfig", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.remove(ACTIVE_USER);
        editor.commit();
    }

    public Long getActiveUserId(Activity activity) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(activity, "onlineCinemaDatabase", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        final UsersDao usersDao = daoSession.getUsersDao();
        List<Users> userses = usersDao.queryBuilder().where(UsersDao.Properties.Email.eq(getActiveUser(activity))).list();
        return userses.get(0).getUserId();
    }

    public int getActiveUserAge(Activity activity) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(activity, "onlineCinemaDatabase", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        final UsersDao usersDao = daoSession.getUsersDao();
        List<Users> userses = usersDao.queryBuilder().where(UsersDao.Properties.Email.eq(getActiveUser(activity))).list();
        return userses.get(0).getAge();
    }

}
