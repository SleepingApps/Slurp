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

import java.time.LocalDate;

import it.sofk.slurp.R;
import it.sofk.slurp.database.ViewModel;
import it.sofk.slurp.databinding.FragmentOccasionallyBinding;
import it.sofk.slurp.dto.FoodDTO;
import it.sofk.slurp.enumeration.Frequency;
import it.sofk.slurp.ui.adapters.OccasionallyFragmentAdapter;

public class OccasionallyFragment extends Fragment implements OccasionallyFragmentAdapter.ClickListener {

    private FragmentOccasionallyBinding binding;
    private ViewModel viewModel;
    private OccasionallyFragmentAdapter occasionallyFragmentAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        occasionallyFragmentAdapter = new OccasionallyFragmentAdapter(getResources().getColor(R.color.purple_200, requireActivity().getTheme()),
                getResources().getColor(R.color.purple_500, requireActivity().getTheme()));
        occasionallyFragmentAdapter.setClickListener(this);

        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);
        viewModel.getFoods(Frequency.OCCASIONALLY, LocalDate.now()).observe(requireActivity(), occasionallyFragmentAdapter::submitData);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOccasionallyBinding.inflate(inflater);
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        binding.occasionallyRecyclerview.setLayoutManager(layout);
        binding.occasionallyRecyclerview.setAdapter(occasionallyFragmentAdapter);
        ((SimpleItemAnimator) binding.occasionallyRecyclerview.getItemAnimator()).setSupportsChangeAnimations(false);

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