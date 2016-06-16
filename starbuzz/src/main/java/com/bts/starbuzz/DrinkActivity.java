package com.bts.starbuzz;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends AppCompatActivity {
    public final static String EXTRA_DRINKO = "drinkNO";
    private static final String DB_NAME = "DRINK";
    private static final String NAME = "NAME";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String IMAGE_RESOURCE_ID = "IMAGE_RESOURCES_ID";
    private static final String FAVORITE = "FAVORITE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);
        int drinkNo = (Integer) getIntent().getExtras().getInt(EXTRA_DRINKO);
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
            Toast toast = Toast.makeText(this, "Database unavailable DrinkActivity", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onFavoriteClicked(View view) {
        int drinkNo = (Integer) getIntent().getExtras().getInt(EXTRA_DRINKO);
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
}
