package it.sofk.slurp.ui.extra;

import it.sofk.slurp.R;

public class FoodIconColours {

    public final int image;
    public final int color;

    public FoodIconColours(int image, int colour) {
        this.image = image;
        this.color = colour;
    }

    public static FoodIconColours GetFoodHelper(String foodName) {
        switch (foodName) {
            case "Acqua":
                return new FoodIconColours(R.drawable.waterbottle, 0xff2092bd);
            case "Grassi":
                return new FoodIconColours(R.drawable.oil, 0xffb3bf6f);
            case "Frutta":
                return new FoodIconColours(R.drawable.fruit, 0xffa2f6b4);
            case "Verdura":
                return new FoodIconColours(R.drawable.vegetables, 0xffa4e7ac);
            case "Latte e Yogurt":
                return new FoodIconColours(R.drawable.milkjar, 0xfffff3b2);
            case "Pane":
                return new FoodIconColours(R.drawable.bakery, 0xfff0b452);
            case "Pasta, riso, mais, farro, orzo, ecc.":
                return new FoodIconColours(R.drawable.spaguetti, 0xfffbeda0);
            case "Carne bianca":
                return new FoodIconColours(R.drawable.chicken, 0xffd09b54);
            case "Carne rossa":
                return new FoodIconColours(R.drawable.steak, 0xffbf7474);
            case "Cereali per la prima colazione":
                return new FoodIconColours(R.drawable.cereals, 0xffa5ddea);
            case "Formaggi":
                return new FoodIconColours(R.drawable.cheese, 0xfff0eea7);
            case "Frutta secca":
                return new FoodIconColours(R.drawable.nuts, 0xffe3b467);
            case "Legumi":
                return new FoodIconColours(R.drawable.redbeans, 0xffff7c00);
            case "Patate":
                return new FoodIconColours(R.drawable.potato, 0xffffca74);
            case "Pesce":
                return new FoodIconColours(R.drawable.fish, 0xffd0d0d0);
            case "Pesce conservato":
                return new FoodIconColours(R.drawable.cannedfood, 0xffe1e1e1);
            case "Prodotti da forno dolci":
                return new FoodIconColours(R.drawable.cookies, 0xfff69629);
            case "Sostituti del pane":
                return new FoodIconColours(R.drawable.cracker, 0xffead6a5);
            case "Uova":
                return new FoodIconColours(R.drawable.eggs, 0xfff9edc8);
        }

        return null;
    }
}
