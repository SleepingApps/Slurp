package it.sofk.slurp.ui.fragments;

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

import java.util.Objects;

import it.sofk.slurp.R;
import it.sofk.slurp.databinding.FragmentDailyBinding;
import it.sofk.slurp.databinding.FragmentFoodBinding;
import it.sofk.slurp.ui.adapters.FoodFragmentAdapter;

public class FoodFragment extends Fragment {

    private FragmentFoodBinding binding;
    private Holder holder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFoodBinding.inflate(inflater);
        holder = new Holder();
        return binding.getRoot();
    }

    private class Holder extends ViewPager2.OnPageChangeCallback implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

        Holder() {
            binding.foodViewpager.setAdapter(new FoodFragmentAdapter(FoodFragment.this.requireActivity()));
            binding.foodViewpager.registerOnPageChangeCallback(this);

            binding.menuDaily.setOnClickListener(this);
            binding.menuWeekly.setOnClickListener(this);
            binding.menuOccasionally.setOnClickListener(this);
        }

        @Override
        public void onPageSelected (int position) {
            switch (position) {
                case 0:
                    binding.menuDaily.setChecked(true);
                    break;
                case 1:
                    binding.menuWeekly.setChecked(true);
                    break;
                case 2:
                    binding.menuOccasionally.setChecked(true);
                    break;
            }
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_daily:
                    binding.foodViewpager.setCurrentItem(0);
                    return true;

                case R.id.menu_weekly:
                    binding.foodViewpager.setCurrentItem(1);
                    return true;

                case R.id.menu_occasionally:
                    binding.foodViewpager.setCurrentItem(2);
                    return true;
            }

            return false;
        }

        @Override
        public void onClick(View view) {
            if (view == binding.menuDaily) binding.foodViewpager.setCurrentItem(0);
            else if (view == binding.menuWeekly) binding.foodViewpager.setCurrentItem(1);
            else if (view == binding.menuOccasionally) binding.foodViewpager.setCurrentItem(2);
            else throw new AssertionError("Menu missing");
        }
    }
}