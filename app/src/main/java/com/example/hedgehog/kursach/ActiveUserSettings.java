package com.example.hedgehog.kursach;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

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

}
