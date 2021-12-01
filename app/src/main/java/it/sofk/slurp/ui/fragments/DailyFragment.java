package it.sofk.slurp.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import it.sofk.slurp.R;
import it.sofk.slurp.database.ViewModel;
import it.sofk.slurp.database.entity.FoodInstance;
import it.sofk.slurp.databinding.FragmentDailyBinding;
import it.sofk.slurp.enumeration.Frequency;
import it.sofk.slurp.ui.adapters.DailyFragmentAdapter;

public class DailyFragment extends Fragment implements DailyFragmentAdapter.ClickListener {

    private FragmentDailyBinding binding;
    private ViewModel viewModel;
    private DailyFragmentAdapter dailyFragmentAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dailyFragmentAdapter = new DailyFragmentAdapter(getResources().getColor(R.color.purple_200, requireActivity().getTheme()),
                getResources().getColor(R.color.purple_500, requireActivity().getTheme()));
        dailyFragmentAdapter.setClickListener(this);

        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);
        viewModel.getFoodIstances(Frequency.DAILY).observe(requireActivity(), dailyFragmentAdapter::submitData);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDailyBinding.inflate(inflater);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        binding.dailyRecyclerview.setLayoutManager(gridLayoutManager);
        binding.dailyRecyclerview.setAdapter(dailyFragmentAdapter);

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