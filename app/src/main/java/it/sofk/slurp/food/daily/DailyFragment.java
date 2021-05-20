package it.sofk.slurp.food.daily;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.sofk.slurp.R;
import it.sofk.slurp.database.ViewModel;
import it.sofk.slurp.databinding.FragmentDailyBinding;
import it.sofk.slurp.enumeration.Frequency;

public class DailyFragment extends Fragment {

    FragmentDailyBinding binding;
    ViewModel viewModel;
    Adapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Adapter adapter = new Adapter();
        ViewModel viewModel = new ViewModelProvider(this).get(ViewModel.class);
        viewModel.getFoodTypesByFrequency(Frequency.DAILY).observe(requireActivity(),
                data -> adapter.submitData(data));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDailyBinding.inflate(inflater);

        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        binding.dailyRecyclerview.setLayoutManager(layout);
        binding.dailyRecyclerview.setAdapter(adapter);

        return binding.getRoot();
    }
}