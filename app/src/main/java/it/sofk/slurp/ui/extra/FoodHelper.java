package it.sofk.slurp.ui.extra;

import android.content.Context;
import android.graphics.Color;

import it.sofk.slurp.R;

public class FoodHelper {

    public final int image;
    public final int color;

    public FoodHelper(int image, int colour) {
        this.image = image;
        this.color = colour;
    }

    public static FoodHelper GetFoodHelper(String foodName) {
        switch (foodName) {
            case "Acqua":
                return new FoodHelper(R.drawable.waterbottle, 0xff2092bd);
            case "Grassi":
                return new FoodHelper(R.drawable.oil, 0xffb3bf6f);
            case "Frutta":
                return new FoodHelper(R.drawable.fruit, 0xffa2f6b4);
            case "Verdura":
                return new FoodHelper(R.drawable.vegetables, 0xffa4e7ac);
            case "Latte e Yogurt":
                return new FoodHelper(R.drawable.milkjar, 0xfffff3b2);
            case "Pane":
                return new FoodHelper(R.drawable.bakery, 0xfff0b452);
            case "Pasta, riso, mais, farro, orzo, ecc.":
                return new FoodHelper(R.drawable.spaguetti, 0xfffbeda0);
            case "Carne bianca":
                return new FoodHelper(R.drawable.chicken, 0xffd09b54);
            case "Carne rossa":
                return new FoodHelper(R.drawable.steak, 0xffbf7474);
            case "Cereali per la prima colazione":
                return new FoodHelper(R.drawable.cereals, 0xffa5ddea);
            case "Formaggi":
                return new FoodHelper(R.drawable.cheese, 0xfff0eea7);
            case "Frutta secca":
                return new FoodHelper(R.drawable.nuts, 0xffe3b467);
            case "Legumi":
                return new FoodHelper(R.drawable.redbeans, 0xffff7c00);
            case "Patate":
                return new FoodHelper(R.drawable.potato, 0xffffca74);
            case "Pesce":
                return new FoodHelper(R.drawable.fish, 0xffd0d0d0);
            case "Pesce conservato":
                return new FoodHelper(R.drawable.cannedfood, 0xffe1e1e1);
            case "Prodotti da forno dolci":
                return new FoodHelper(R.drawable.cookies, 0xfff69629);
            case "Sostituti del pane":
                return new FoodHelper(R.drawable.cracker, 0xffead6a5);
            case "Uova":
                return new FoodHelper(R.drawable.eggs, 0xfff9edc8);
        }

        return null;
    }
}
