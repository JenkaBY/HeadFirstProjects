package com.bts.starbuzz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DrinkCategoryActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listView = getListView();
        ArrayAdapter listAdapter =
                new ArrayAdapter<Drink>(this,android.R.layout.simple_list_item_1,Drink.drinks);
        listView.setAdapter(listAdapter);
    }
    @Override
    public void onListItemClick(ListView listView,View itemView,int position,long id){
        Intent intent = new Intent(this,DrinkActivity.class);
        intent.putExtra(DrinkActivity.EXTRA_DRINKO, (int) id);
        startActivity(intent);
    }
}
