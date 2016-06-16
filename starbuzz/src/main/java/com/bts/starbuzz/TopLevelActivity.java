package com.bts.starbuzz;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class TopLevelActivity extends Activity {

    private static final String DB_NAME = "DRINK";
    private static final String NAME = "NAME";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String IMAGE_RESOURCE_ID = "IMAGE_RESOURCES_ID";
    private static final String FAVORITE = "FAVORITE";
    private SQLiteDatabase db;
    private Cursor cursorFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);
        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> listView, View v, int position, long id) {
                        switch (position) {
                            case 0: {
                                Intent intent =
                                        new Intent(TopLevelActivity.this, DrinkCategoryActivity.class);
                                startActivity(intent);
                                break;
                            }
                            default:
                                break;
                        }
                    }
                };

        ListView listView = (ListView) findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);

        int isFavorite = 1;
        ListView listViewFavorites = (ListView) findViewById(R.id.list_favorites);

        try {
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            db = starbuzzDatabaseHelper.getReadableDatabase();
            cursorFavorite = db.query(
                    DB_NAME,
                    new String[]{"_id", NAME}, "FAVORITE = ?",
                    new String[]{Integer.toString(isFavorite)},
                    null, null, null);

            CursorAdapter cursorAdapterFavorites = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                    cursorFavorite, new String[]{"NAME"}, new int[]{android.R.id.text1}, 0);
            listViewFavorites.setAdapter(cursorAdapterFavorites);

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable TopLevel", Toast.LENGTH_SHORT);
            toast.show();
        }
        listViewFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View v, int position, long id) {
                Intent intent = new Intent(TopLevelActivity.this, DrinkActivity.class);
                intent.putExtra(DrinkActivity.EXTRA_DRINKO, (int) id);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursorFavorite.close();
        db.close();
    }

    public void onRestart() {
        super.onRestart();
        try {
            int isFavorite = 1;
            ListView listViewFavorites = (ListView) findViewById(R.id.list_favorites);
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            db = starbuzzDatabaseHelper.getReadableDatabase();
            Cursor newCursorFavorite = db.query(
                    DB_NAME,
                    new String[]{"_id", NAME}, "FAVORITE = ?",
                    new String[]{Integer.toString(isFavorite)},
                    null, null, null);

            CursorAdapter cursorAdapterFavorites = (CursorAdapter) listViewFavorites.getAdapter();
            cursorAdapterFavorites.changeCursor(newCursorFavorite);
            cursorFavorite = newCursorFavorite;

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable ChangeCursor", Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}
