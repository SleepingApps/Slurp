package it.sofk.slurp.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.sofk.slurp.R;
import it.sofk.slurp.databinding.FragmentOccasionallyBinding;

public class OccasionallyFragment extends Fragment {

    private FragmentOccasionallyBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOccasionallyBinding.inflate(inflater);
        return binding.getRoot();
    }
}