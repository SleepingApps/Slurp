package it.sofk.slurp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.SimpleItemAnimator;

import it.sofk.slurp.R;
import it.sofk.slurp.database.ViewModel;
import it.sofk.slurp.databinding.FragmentDailyBinding;
import it.sofk.slurp.dto.FoodDTO;
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
        viewModel.getFoods(Frequency.DAILY).observe(requireActivity(), dailyFragmentAdapter::submitData);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDailyBinding.inflate(inflater);
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        binding.dailyRecyclerview.setLayoutManager(layout);
        binding.dailyRecyclerview.setAdapter(dailyFragmentAdapter);
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
}