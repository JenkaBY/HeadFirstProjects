package com.bts.beeradviser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YKuzmich on 17.03.2016.
 */
public class BeerExpert {
    List<String> getBrands(String beer_colors){
        List<String> brands = new ArrayList<>();
        switch (beer_colors){
            case "amber":
                brands.add("Amber 1");
                brands.add("Amber 2");
                brands.add("Amber 3");
                break;
            case "dark":
                brands.add("Dark 1");
                brands.add("Dark 2");
                brands.add("Dark 3");
                break;
            case "brown":
                brands.add("Brown 1");
                brands.add("Brown 2");
                brands.add("Brown 3");
                break;
            case "light":
                brands.add("light 1");
                brands.add("light 2");
                brands.add("light 3");
                brands.add("light 2");
                brands.add("light 3");
                break;
            default: brands.add("Don't drink beer");
                break;
        }
        return brands;
    }
}
