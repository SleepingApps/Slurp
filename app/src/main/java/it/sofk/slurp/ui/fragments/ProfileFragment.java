package it.sofk.slurp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import it.sofk.slurp.database.UserViewModel;
import it.sofk.slurp.databinding.FragmentProfileBinding;
import it.sofk.slurp.enumeration.CaloricIntake;

public class ProfileFragment extends Fragment implements SeekBar.OnSeekBarChangeListener, View.OnFocusChangeListener {

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
            if(user.getHeight() != 0) binding.editTextHeight.setText(String.valueOf(user.getHeight()));
            if(user.getWeight() != 0)binding.editTextWeight.setText(String.valueOf(user.getWeight()));
            binding.seekBarCaloricIntake.setProgress(user.caloricIntake.i - 1);
        });

        binding.editTextWeight.setOnFocusChangeListener(this);
        binding.editTextHeight.setOnFocusChangeListener(this);
        return binding.getRoot();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(v.getId() == binding.editTextWeight.getId() && !hasFocus){
            if(binding.editTextWeight.getText() == null || binding.editTextWeight.getText().toString().equals("")){
                userViewModel.updateWeight(0d);
            }else {
                userViewModel.updateWeight(Double.parseDouble(binding.editTextWeight.getText().toString()));
            }
        }
        else if(v.getId() == binding.editTextHeight.getId() && !hasFocus){
            if(binding.editTextHeight.getText() == null || binding.editTextHeight.getText().toString().equals("")){
                userViewModel.updateHeight(0d);
            }else {
                userViewModel.updateHeight(Double.parseDouble(binding.editTextHeight.getText().toString()));
            }
        }
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