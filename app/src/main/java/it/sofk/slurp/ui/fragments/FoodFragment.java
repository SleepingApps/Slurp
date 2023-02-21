package it.sofk.slurp.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.sofk.slurp.R;
import it.sofk.slurp.database.ViewModel;
import it.sofk.slurp.databinding.FragmentFoodBinding;
import it.sofk.slurp.ui.adapters.FoodFragmentAdapter;
import it.sofk.slurp.ui.callback.DialogFoodFragmentCallBack;
import it.sofk.slurp.ui.extra.DialogPopupDays;

public class FoodFragment extends Fragment implements DialogFoodFragmentCallBack {

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
        holder = new Holder(this);
        if (viewModel.foodFragmentViewPagerPosition != -1) {
            binding.foodViewpager.setCurrentItem(viewModel.foodFragmentViewPagerPosition);
        }

        return binding.getRoot();
    }

    /*
    Questa funzione deve cambiare i foodDTO con quelli del giorno dati dalla funzione
     */
    @Override
    public void onSelectedDayFromDialog(LocalDate day, int number) {
        binding.foodMenu.getTabAt(0).setText("Giorno " + number + " ▾");
        Log.i("TEST", day.toString());
        //TODO

    }

    private class Holder extends ViewPager2.OnPageChangeCallback implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener, TabLayout.OnTabSelectedListener  {

        private DialogFoodFragmentCallBack callBack;
        private boolean lastWasDaily = false;

        Holder(DialogFoodFragmentCallBack callBack) {
            binding.foodViewpager.setAdapter(new FoodFragmentAdapter(FoodFragment.this.requireActivity()));
            binding.foodViewpager.registerOnPageChangeCallback(this);

            binding.foodMenu.addOnTabSelectedListener(this);
            this.callBack = callBack;
        }

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            binding.foodViewpager.setCurrentItem(tab.getPosition());

            if(binding.foodViewpager.getCurrentItem() == 0){
                if(lastWasDaily == false)
                    binding.foodMenu.getTabAt(0).setText(binding.foodMenu.getTabAt(0).getText() + " ▾");
                lastWasDaily = true;
            }
            else{
                if(lastWasDaily == true){
                    String t = (String) binding.foodMenu.getTabAt(0).getText();
                    binding.foodMenu.getTabAt(0).setText(t.subSequence(0, t.length() - 2));
                }
                lastWasDaily = false;
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
            if(binding.foodViewpager.getCurrentItem() == 0 && lastWasDaily) {
                viewModel.getCurrentWeek().observe(requireActivity(), week -> {
                    List<LocalDate> days = new ArrayList<>();
                    for (LocalDate day = week.getStartDate(); day.isBefore(week.getEndDate().plusDays(1)); day = day.plusDays(1)) {
                        days.add(day);
                    }
                    DialogPopupDays popup = new DialogPopupDays(getContext(), days, callBack);
                    popup.show(getActivity().getSupportFragmentManager(), "");
                });
            }
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
                    Log.i("SLIDE", "Daily");
                    return true;

                case R.id.menu_weekly:
                    viewModel.foodFragmentViewPagerPosition = 1;
                    binding.foodViewpager.setCurrentItem(1);
                    Log.i("SLIDE", "Week");
                    return true;

                case R.id.menu_occasionally:
                    viewModel.foodFragmentViewPagerPosition = 2;
                    binding.foodViewpager.setCurrentItem(2);
                    Log.i("SLIDE", "Occ");
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