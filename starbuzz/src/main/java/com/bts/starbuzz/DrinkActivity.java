package com.bts.starbuzz;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends AppCompatActivity {
    public final static String EXTRA_DRINKO = "drinkNO";
    private static final String DB_NAME = "DRINK";
    private static final String NAME = "NAME";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String IMAGE_RESOURCE_ID = "IMAGE_RESOURCES_ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);
        int drinkNo = (Integer) getIntent().getExtras().getInt(EXTRA_DRINKO);
        try {
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            SQLiteDatabase db = starbuzzDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query(DB_NAME, new String[]{NAME, DESCRIPTION, IMAGE_RESOURCE_ID}, "_id = ?",
                    new String[]{Integer.toString(drinkNo)}, null, null, null);
            if (cursor.moveToFirst()) {
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                int photoId = cursor.getInt(2);

                ImageView photo = (ImageView) findViewById(R.id.photo);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);

                TextView name = (TextView) findViewById(R.id.name);
                name.setText(nameText);

                TextView description = (TextView) findViewById(R.id.description);
                description.setText(descriptionText);
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable DrinkActivity", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
