package it.sofk.slurp.food;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import it.sofk.slurp.food.daily.DailyFragment;
import it.sofk.slurp.food.occasionally.OccasionallyFragment;
import it.sofk.slurp.food.weekly.WeeklyFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new DailyFragment();
            case 1:
                return new WeeklyFragment();
            case 2:
                return new OccasionallyFragment();
        }

        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
