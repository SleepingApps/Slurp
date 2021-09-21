package it.sofk.slurp.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.sofk.slurp.database.ViewModel;
import it.sofk.slurp.database.entity.FoodInstance;
import it.sofk.slurp.databinding.FragmentDailyBinding;
import it.sofk.slurp.databinding.FragmentWeeklyBinding;
import it.sofk.slurp.enumeration.Frequency;
import it.sofk.slurp.ui.adapters.WeeklyFragmentAdapter;

public class WeeklyFragment extends Fragment implements WeeklyFragmentAdapter.ClickListener {

    private FragmentWeeklyBinding binding;
    private ViewModel viewModel;
    private WeeklyFragmentAdapter weeklyFragmentAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        weeklyFragmentAdapter = new WeeklyFragmentAdapter();
        weeklyFragmentAdapter.setClickListener(this);

        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);
        viewModel.getFoodIstances(Frequency.WEEKLY).observe(requireActivity(), weeklyFragmentAdapter::submitData);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWeeklyBinding.inflate(inflater);
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        binding.weeklyRecyclerview.setLayoutManager(layout);
        binding.weeklyRecyclerview.setAdapter(weeklyFragmentAdapter);

        return binding.getRoot();
    }

    @Override
    public void onPlusClick(FoodInstance foodInstance) {
        viewModel.update(foodInstance);
    }

    @Override
    public void onMinusClick(FoodInstance foodInstance) {
        viewModel.update(foodInstance);
    }
}