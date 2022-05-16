package it.sofk.slurp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDate;

import it.sofk.slurp.database.ViewModel;
import it.sofk.slurp.database.entity.FoodInstance;
import it.sofk.slurp.database.entity.FoodType;
import it.sofk.slurp.databinding.FragmentStartWeekBinding;
import it.sofk.slurp.ui.viewmodels.StartWeekViewModel;

public class StartWeekFragment extends Fragment implements View.OnClickListener {

    private FragmentStartWeekBinding binding;
    private StartWeekViewModel viewModel;
    private ViewModel viewModelShared;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentStartWeekBinding.inflate(inflater);
        binding.btnStartWeek.setOnClickListener(this);
        viewModel = new ViewModelProvider(requireActivity()).get(StartWeekViewModel.class);
        viewModelShared = new ViewModelProvider(requireActivity()).get(ViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == binding.btnStartWeek.getId()){
            viewModel.getFoodTypes().observe(requireActivity(), foodTypes -> {
                for(FoodType foodType : foodTypes) {
                    LocalDate day = LocalDate.now();
                    for(int i = 0; i < 7; i++){
                        viewModel.insert(new FoodInstance(foodType.getName(), day));
                        day = day.plusDays(1);
                    }

                }
                viewModel.addWeek(LocalDate.now());
            });
            viewModelShared.setWeekStarted(Boolean.TRUE);


        }
    }

}