package it.sofk.slurp.food;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import it.sofk.slurp.BuildConfig;
import it.sofk.slurp.R;

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

    class Holder extends ViewPager2.OnPageChangeCallback implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

        //BottomNavigationView navViewFood;
        ViewPager2 pager;
        RadioButton menuDaily, menuWeekly, menuOccasionally;

        Holder(View view) {
            /*
            navViewFood = view.findViewById(R.id.nav_view_food);
            navViewFood.setOnNavigationItemSelectedListener(this);
            */
            pager = view.findViewById(R.id.food_viewpager);
            pager.setAdapter(new ViewPagerAdapter(FoodFragment.this.getActivity()));
            pager.registerOnPageChangeCallback(this);

            menuDaily = view.findViewById(R.id.menu_daily);
            menuDaily.setOnClickListener(this);

            menuWeekly = view.findViewById(R.id.menu_weekly);
            menuWeekly.setOnClickListener(this);

            menuOccasionally = view.findViewById(R.id.menu_occasionally);
            menuOccasionally.setOnClickListener(this);
        }

        @Override
        public void onPageSelected (int position) {
            switch (position) {
                case 0:
                    menuDaily.setChecked(true);
                    break;
                case 1:
                    menuWeekly.setChecked(true);
                    break;
                case 2:
                    menuOccasionally.setChecked(true);
                    break;
            }
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_daily:
                    pager.setCurrentItem(0);
                    return true;

                case R.id.menu_weekly:
                    pager.setCurrentItem(1);
                    return true;

                case R.id.menu_occasionally:
                    pager.setCurrentItem(2);
                    return true;
            }

            return false;
        }

        @Override
        public void onClick(View view) {
            if (view == menuDaily) pager.setCurrentItem(0);
            else if (view == menuWeekly) pager.setCurrentItem(1);
            else if (view == menuOccasionally) pager.setCurrentItem(2);
            else throw new AssertionError("Menu missing");
        }
    }
}