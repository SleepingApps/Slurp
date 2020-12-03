package it.sofk.slurp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FoodFragment extends Fragment {

    Holder holder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);

        holder = new Holder(view);
        return view;
    }

    class Holder implements BottomNavigationView.OnNavigationItemSelectedListener {

        BottomNavigationView navViewFood;
        NavHostFragment navFragFood;
        NavController navController;

        Holder(View view) {
            navViewFood = view.findViewById(R.id.nav_view_food);
            navViewFood.setOnNavigationItemSelectedListener(this);

            navFragFood = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.nav_frag_food);
            assert navFragFood != null;
            navController = navFragFood.getNavController();
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()){
                case R.id.menu_daily:
                    navController.navigate(R.id.dailyFragment);
                    break;
                case R.id.menu_weekly:
                    navController.navigate(R.id.weeklyFragment);
                    break;
                case R.id.menu_occasionally:
                    navController.navigate(R.id.occasionallyFragment);
                    break;
            }
            return false;
        }
    }
}