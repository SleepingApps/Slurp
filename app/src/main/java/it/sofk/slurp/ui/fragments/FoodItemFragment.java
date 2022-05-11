package it.sofk.slurp.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.time.LocalDate;
import java.util.List;

import it.sofk.slurp.R;
import it.sofk.slurp.database.ViewModel;
import it.sofk.slurp.databinding.FragmentFoodItemBinding;
import it.sofk.slurp.databinding.FragmentProfileBinding;
import it.sofk.slurp.dto.FoodDTO;
import it.sofk.slurp.enumeration.Frequency;

public class FoodItemFragment extends Fragment {

    FragmentFoodItemBinding binding;
    ViewModel viewModel;

    public FoodItemFragment() {
    }

    public static FoodItemFragment newInstance(String param1, String param2) {
        FoodItemFragment fragment = new FoodItemFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);
        viewModel.getSelectedFood().observe(requireActivity(), foodDTO -> {
            binding.progressRing.initialise(requireActivity(), (int)foodDTO.getMaxPortions());
            binding.progressRing.setProgress((float)foodDTO.getEatenPortions(), false);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFoodItemBinding.inflate(inflater);
        return binding.getRoot();
    }
}