package com.example.shivang.contacts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shivang on 04/07/17.
 */

public class ContactOpenHelper extends SQLiteOpenHelper {

    public final static String CONTACT_TABLE_NAME  = "Contact";
    public final static String CONTACT_NAME  = "name";
    public final static String CONTACT_ID  = "_id";
    public final static String CONTACT_NUMBER  = "number";
    public final static String CONTACT_EMAIL  = "email";
    public final static String CONTACT_CATEGORY  = "category";
    public  static ContactOpenHelper contactOpenHelper;

    public static ContactOpenHelper getOpenHelperInstance(Context context){

        if(contactOpenHelper == null){
            contactOpenHelper = new ContactOpenHelper(context);
        }
        return contactOpenHelper;

    }

    public ContactOpenHelper(Context context) {
        super(context, "Contacts.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + CONTACT_TABLE_NAME +" (" + CONTACT_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + CONTACT_NAME +" TEXT, "
                + CONTACT_NUMBER + " TEXT, "
                + CONTACT_EMAIL + " TEXT, " + CONTACT_CATEGORY + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
