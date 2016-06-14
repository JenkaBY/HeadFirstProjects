package com.bts.starbuzz;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by YKuzmich on 14.06.2016.
 */
public class StarbuzzDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "starbuzz";
    private static final int DB_VERSION = 2;
    private static final String TB_DRINK = "DRINK";

    StarbuzzDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMydatabase(db, 0, DB_VERSION);
    }

    private void updateMydatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            String sql = "CREATE TABLE " + TB_DRINK +
                    "(_id INTEGER PRIMARY AUTOINCREMENT, NAME TEXT,DESCRIPTION TEXT, IMAGE_RESOURCES_ID INTEGER);";
            db.execSQL(sql);
            insertDrink(db, "Latte", "Espresso and steamed milk", R.drawable.latte);
            insertDrink(db, "Cappuccino", "Espress, hot milk and steamed-milk foam", R.drawable.cappuccino);
            insertDrink(db, "Filter", "Our best drip coffee", R.drawable.filter);
        }
        if (oldVersion < 2) {
            String sqlInsertColumn = "ALTER TABLE " + TB_DRINK + " COLUMN FAVORITE NUMERIC;";
            db.execSQL(sqlInsertColumn);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMydatabase(db, oldVersion, newVersion);
    }

    private static void insertDrink(SQLiteDatabase db, String name, String description, int resourceID) {
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("NAME", name);
        drinkValues.put("DESCRIPTION", description);
        drinkValues.put("IMAGE_RESOURCES_ID", resourceID);
        db.insert(TB_DRINK, null, drinkValues);
    }
}
