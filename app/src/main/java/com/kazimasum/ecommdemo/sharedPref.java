package com.kazimasum.ecommdemo;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class sharedPref {
    private static sharedPref mInstance;
    private Context mCntx;


    public sharedPref(Context cntx) {
        mCntx = cntx;
    }

    public static synchronized sharedPref getInstance(Context mCntx){
        if(mInstance==null){
            mInstance = new sharedPref(mCntx);
        }
        return mInstance;
    }

    void saveUser(userprofileresponsemodel modal){
        SharedPreferences sp =mCntx.getSharedPreferences("credentials", MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("email", modal.getEmail());
        editor.putString("name", modal.getName());
        editor.putString("mobile", modal.getMobile());
        editor.commit();
        editor.apply();
    }

    public userprofileresponsemodel getUser()
    {
        SharedPreferences sp =mCntx.getSharedPreferences("credentials", MODE_PRIVATE);
        return new userprofileresponsemodel(
                sp.getString("email", null),
                sp.getString("name", null),
                sp.getString("mobile", null)

        );
    }

}