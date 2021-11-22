package it.sofk.slurp.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import it.sofk.slurp.database.UserViewModel;
import it.sofk.slurp.databinding.FragmentProfileBinding;
import it.sofk.slurp.enumeration.CaloricIntake;

public class ProfileFragment extends Fragment implements SeekBar.OnSeekBarChangeListener, TextWatcher {

    private FragmentProfileBinding binding;
    private UserViewModel userViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater);
        binding.seekBarCaloricIntake.setOnSeekBarChangeListener(this);

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.getUserInformation().observe(requireActivity(), user -> {
            binding.editTextHeight.setText(String.valueOf(user.getHeight()));
            binding.editTextWeight.setText(String.valueOf(user.getWeight()));
            binding.seekBarCaloricIntake.setProgress(user.caloricIntake.i - 1);
        });

        binding.editTextWeight.addTextChangedListener(this);
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        userViewModel.updateHeight(Double.parseDouble(binding.editTextHeight.getText().toString()));
        userViewModel.updateWeight(Double.parseDouble(binding.editTextWeight.getText().toString()));
    }
}