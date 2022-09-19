package it.sofk.slurp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.SimpleItemAnimator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.sofk.slurp.R;
import it.sofk.slurp.database.ViewModel;
import it.sofk.slurp.databinding.FragmentWeeklyBinding;
import it.sofk.slurp.dto.FoodDTO;
import it.sofk.slurp.enumeration.Frequency;
import it.sofk.slurp.ui.adapters.WeeklyFragmentAdapter;

public class WeeklyFragment extends Fragment implements WeeklyFragmentAdapter.ClickListener {

    private FragmentWeeklyBinding binding;
    private ViewModel viewModel;
    private WeeklyFragmentAdapter weeklyFragmentAdapter;
    public static final FoodDTO spaceHolder = new FoodDTO("spaceHolder", 0.1,0.0, LocalDate.now());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);

        weeklyFragmentAdapter = new WeeklyFragmentAdapter(getActivity(), viewModel);
        weeklyFragmentAdapter.setClickListener(this);

        viewModel.getFoods(Frequency.WEEKLY, LocalDate.now()).observe(requireActivity(), foodDTOS -> {
            weeklyFragmentAdapter.submitData(foodDTOS);
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWeeklyBinding.inflate(inflater);
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        binding.weeklyRecyclerview.setLayoutManager(layout);
        binding.weeklyRecyclerview.setAdapter(weeklyFragmentAdapter);
        ((SimpleItemAnimator) binding.weeklyRecyclerview.getItemAnimator()).setSupportsChangeAnimations(false);
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
}