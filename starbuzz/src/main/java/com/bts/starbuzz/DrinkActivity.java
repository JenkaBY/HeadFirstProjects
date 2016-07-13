package com.bts.starbuzz;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends AppCompatActivity {
    public final static String EXTRA_DRINKNO = "drinkNO";
    private static final String DB_NAME = "DRINK";
    private static final String NAME = "NAME";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String IMAGE_RESOURCE_ID = "IMAGE_RESOURCES_ID";
    private static final String FAVORITE = "FAVORITE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);
        int drinkNo = (Integer) getIntent().getExtras().getInt(EXTRA_DRINKNO);
        new OnCreateTask().execute(drinkNo);
        /*
        try {
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            SQLiteDatabase db = starbuzzDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query(
                    DB_NAME,
                    new String[]{NAME, DESCRIPTION, IMAGE_RESOURCE_ID, FAVORITE}, "_id = ?",
                    new String[]{Integer.toString(drinkNo)},
                    null, null, null);
            if (cursor.moveToFirst()) {
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                int photoId = cursor.getInt(2);
                boolean isFavorite = cursor.getInt(3) == 1;

                ImageView photo = (ImageView) findViewById(R.id.photo);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);

                TextView name = (TextView) findViewById(R.id.name);
                name.setText(nameText);

                TextView description = (TextView) findViewById(R.id.description);
                description.setText(descriptionText);

                CheckBox favorite = (CheckBox) findViewById(R.id.checkbox_favorite);
                favorite.setChecked(isFavorite);
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this,
                    "Database unavailable DrinkActivity.onCrate", Toast.LENGTH_SHORT);
            toast.show();
        }
        */
    }

    public void onFavoriteClicked(View view) {
        int drinkNo = (Integer) getIntent().getExtras().getInt(EXTRA_DRINKNO);
        new UpdateDrinkTask().execute(drinkNo);
    }
    /*
        CheckBox favorite = (CheckBox) findViewById(R.id.checkbox_favorite);
        ContentValues drinkValues = new ContentValues();
        drinkValues.put(FAVORITE, favorite.isChecked());
        try {
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            SQLiteDatabase db = starbuzzDatabaseHelper.getWritableDatabase();
            db.update(
                    DB_NAME, drinkValues, "_id = ?",
                    new String[]{Integer.toString(drinkNo)});
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable FavoriteClicked", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    */

    private class UpdateDrinkTask extends AsyncTask<Integer, Void, Boolean> {
        ContentValues drinkValues;

        protected void onPreExecute() {
            CheckBox favorite = (CheckBox) findViewById(R.id.checkbox_favorite);
            drinkValues = new ContentValues();
            drinkValues.put(FAVORITE, favorite.isChecked());
        }

        @Override
        protected Boolean doInBackground(Integer... drinks) {
            int drinkNo = drinks[0];
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(DrinkActivity.this);
            try {

                SQLiteDatabase db = starbuzzDatabaseHelper.getWritableDatabase();
                db.update(
                        DB_NAME, drinkValues, "_id = ?",
                        new String[]{Integer.toString(drinkNo)});
                db.close();
                return true;
            } catch (SQLiteException e) {
                return false;
            }
        }

        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast toast = Toast.makeText(DrinkActivity.this,
                        "Database unavailable FavoriteClicked", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    private class OnCreateTask extends AsyncTask<Integer, Void, Boolean> {
        //Cursor cursor;
        //
        ContentValues cursorValues;
        //SQLiteOpenHelper starbuzzDatabaseHelper;
        SQLiteDatabase db;
        //protected void onPreExecute() {
        //}

        @Override
        protected Boolean doInBackground(Integer... drinks) {
            int drinkNo = drinks[0];
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(DrinkActivity.this);
            try {

                Cursor cursor;
                db = starbuzzDatabaseHelper.getReadableDatabase();
                cursor = db.query(
                        DB_NAME,
                        new String[]{NAME, DESCRIPTION, IMAGE_RESOURCE_ID, FAVORITE}, "_id = ?",
                        new String[]{Integer.toString(drinkNo)},
                        null, null, null);
                if (cursor.moveToFirst()) {
                    /*
                    String nameText = cursor.getString(0);
                    String descriptionText = cursor.getString(1);
                    int photoId = cursor.getInt(2);
                    boolean isFavorite = cursor.getInt(3) == 1;
                    */
                    cursorValues.put(NAME,cursor.getString(0));
                    cursorValues.put(DESCRIPTION,cursor.getString(1));
                    cursorValues.put(IMAGE_RESOURCE_ID,cursor.getInt(2));
                    cursorValues.put(FAVORITE,cursor.getInt(3) == 1);
                }
                cursor.close();
                //db.close();
                return true;
            } catch (SQLiteException e) {
                return false;
            }
        }

        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast toast = Toast.makeText(DrinkActivity.this,
                        "Database unavailable onCreateTask", Toast.LENGTH_SHORT);
                toast.show();
                //cursor.close();
                db.close();
            } else {
                if (cursorValues != null) {
                    //if (cursor.moveToFirst()) {

                        String nameText =(String) cursorValues.get(NAME);
                        String descriptionText = (String) cursorValues.get(DESCRIPTION);
                        int photoId =(Integer) cursorValues.get(IMAGE_RESOURCE_ID);
                        boolean isFavorite = (Boolean) cursorValues.get(FAVORITE);

                        ImageView photo = (ImageView) findViewById(R.id.photo);
                        photo.setImageResource(photoId);
                        photo.setContentDescription(nameText);

                        TextView name = (TextView) findViewById(R.id.name);
                        name.setText(nameText);

                        TextView description = (TextView) findViewById(R.id.description);
                        description.setText(descriptionText);

                        CheckBox favorite = (CheckBox) findViewById(R.id.checkbox_favorite);
                        favorite.setChecked(isFavorite);

                    //}
                    //cursor.close();
                    db.close();
                }
            }
        }
    }
}
