package com.bts.starbuzz;

/**
 * Created by Jenka on 29.03.2016.
 */
public class Drink {
    private String name;
    private String description;
    private int imageResourceId;
    private static final Drink[] drinks = {new Drink("Latte",
            "A couple of espresso shots with steamed milk", R.drawable.latte),
            new Drink("Latte",
                    "Cappuccino, hot milk, and a steamed milk foam", R.drawable.cappuccino),
            new Drink("Filter",
                    "Highest quality beans roasted and brewed fresh", R.drawable.filter)};

    public Drink(String name, String description, int imageResourceId) {
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }
    public String getName(){ return name;}
    public String getDescription(){ return name;}
    public int getImageResourceId(){return imageResourceId;}

    public String toString(){return this.name;}

}
