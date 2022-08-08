package it.sofk.slurp.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.time.LocalDate;
import java.util.Objects;

import it.sofk.slurp.R;
import it.sofk.slurp.database.ViewModel;
import it.sofk.slurp.databinding.FragmentDailyBinding;
import it.sofk.slurp.databinding.FragmentFoodBinding;
import it.sofk.slurp.enumeration.Frequency;
import it.sofk.slurp.ui.adapters.FoodFragmentAdapter;

public class FoodFragment extends Fragment {

    private FragmentFoodBinding binding;
    private ViewModel viewModel;
    private Holder holder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFoodBinding.inflate(inflater);
        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);
        holder = new Holder();
        if (viewModel.foodFragmentViewPagerPosition != -1) {
            binding.foodViewpager.setCurrentItem(viewModel.foodFragmentViewPagerPosition);
        }

        return binding.getRoot();
    }

    private class Holder extends ViewPager2.OnPageChangeCallback implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener, TabLayout.OnTabSelectedListener  {

        Holder() {
            binding.foodViewpager.setAdapter(new FoodFragmentAdapter(FoodFragment.this.requireActivity()));
            binding.foodViewpager.registerOnPageChangeCallback(this);

            binding.foodMenu.addOnTabSelectedListener(this);
        }

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            binding.foodViewpager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }

        @Override
        public void onPageSelected (int position) {
            TabLayout.Tab tab = binding.foodMenu.getTabAt(position);
            viewModel.foodFragmentViewPagerPosition = position;
            assert(tab != null);
            tab.select();
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_daily:
                    viewModel.foodFragmentViewPagerPosition = 0;
                    binding.foodViewpager.setCurrentItem(0);
                    return true;

                case R.id.menu_weekly:
                    viewModel.foodFragmentViewPagerPosition = 1;
                    binding.foodViewpager.setCurrentItem(1);
                    return true;

                case R.id.menu_occasionally:
                    viewModel.foodFragmentViewPagerPosition = 2;
                    binding.foodViewpager.setCurrentItem(2);
                    return true;
            }

            return false;
        }

        @Override
        public void onClick(View view) {
            /*
            if (view == binding.menuDaily) binding.foodViewpager.setCurrentItem(0);
            else if (view == binding.menuWeekly) binding.foodViewpager.setCurrentItem(1);
            else if (view == binding.menuOccasionally) binding.foodViewpager.setCurrentItem(2);
            else throw new AssertionError("Menu missing");*/
        }
    }
}