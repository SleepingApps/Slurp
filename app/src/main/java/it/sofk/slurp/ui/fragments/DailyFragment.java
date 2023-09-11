package it.sofk.slurp.ui.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import it.sofk.slurp.database.ViewModel;
import it.sofk.slurp.databinding.FragmentDailyBinding;
import it.sofk.slurp.dto.FoodDTO;
import it.sofk.slurp.enumeration.Frequency;
import it.sofk.slurp.ui.adapters.DailyFragmentAdapter;

public class DailyFragment extends Fragment implements DailyFragmentAdapter.ClickListener {

    private FragmentDailyBinding binding;
    private ViewModel viewModel;
    private DailyFragmentAdapter dailyFragmentAdapter;
    private LinearLayoutManager layout;
    private RecyclerView.SmoothScroller smoothScroller;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dailyFragmentAdapter = new DailyFragmentAdapter();
        dailyFragmentAdapter.setClickListener(this);

        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);
        viewModel.dailyFoodDTO.observe(requireActivity(),
                foodDTOS -> dailyFragmentAdapter.submitFood(foodDTOS));
        viewModel.getExamples(Frequency.DAILY).observe(requireActivity(),
                exampleDTOS -> dailyFragmentAdapter.submitExamples(exampleDTOS));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDailyBinding.inflate(inflater);

        layout = new LinearLayoutManager(getContext());
        binding.dailyRecyclerview.setLayoutManager(layout);
        binding.dailyRecyclerview.setAdapter(dailyFragmentAdapter);
        smoothScroller = new LinearSmoothScroller(this.getContext()) {
            @Override protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_ANY;
            }
        };
        ((SimpleItemAnimator) binding.dailyRecyclerview.getItemAnimator()).setSupportsChangeAnimations(false);

        return binding.getRoot();
    }

    @Override
    public void onPlusClick(FoodDTO food) {
        viewModel.update(food);
    }

    @Override
    public void onMinusClick(FoodDTO food) {
        viewModel.update(food);
    }

    @Override
    public void onItemExpansion(int itemIndex, DailyFragmentAdapter.ViewHolder viewHolder) {
        AnimatorSet animator = viewHolder.resizer.expandAnimator();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                smoothScroller.setTargetPosition(itemIndex);
                layout.startSmoothScroll(smoothScroller);
            }
        });
        animator.start();
    }

    @Override
    public void onItemShrinkage(int itemIndex, DailyFragmentAdapter.ViewHolder viewHolder) {
        AnimatorSet animator = viewHolder.resizer.shrinkAnimator();
        animator.start();
    }
}