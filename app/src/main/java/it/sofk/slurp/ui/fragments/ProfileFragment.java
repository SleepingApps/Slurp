package it.sofk.slurp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import it.sofk.slurp.database.UserViewModel;
import it.sofk.slurp.databinding.FragmentProfileBinding;
import it.sofk.slurp.enumeration.CaloricIntake;
import it.sofk.slurp.ui.adapters.HistoryAdapter;
import it.sofk.slurp.ui.viewmodels.HistoryViewModel;

public class ProfileFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {

    private FragmentProfileBinding binding;
    private UserViewModel userViewModel;
    private HistoryViewModel historyViewModel;
    private HistoryAdapter historyAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater);
        binding.seekBarCaloricIntake.setOnSeekBarChangeListener(this);

        historyAdapter = new HistoryAdapter();

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.getUserInformation().observe(requireActivity(), user -> {
            binding.seekBarCaloricIntake.setProgress(user.caloricIntake.i - 1);
        });

        historyViewModel = new ViewModelProvider(requireActivity()).get(HistoryViewModel.class);
        historyViewModel.getWeeks().observe(requireActivity(), historyAdapter::submitData);

        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        binding.recyclerViewHistory.setLayoutManager(layout);
        binding.recyclerViewHistory.setAdapter(historyAdapter);
        return binding.getRoot();
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        userViewModel.updateCaloricIntake(CaloricIntake.getFromIndex(progress + 1));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}