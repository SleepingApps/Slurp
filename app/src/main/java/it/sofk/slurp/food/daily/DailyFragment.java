package it.sofk.slurp.food.daily;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.time.LocalDateTime;
import java.util.Date;

import it.sofk.slurp.R;
import it.sofk.slurp.database.ViewModel;
import it.sofk.slurp.database.entity.FoodInstance;
import it.sofk.slurp.database.entity.FoodType;
import it.sofk.slurp.databinding.FragmentDailyBinding;
import it.sofk.slurp.enumeration.Frequency;

public class DailyFragment extends Fragment implements Adapter.PlusClickListener, Adapter.MinusClickListener {

    FragmentDailyBinding binding;
    ViewModel viewModel;
    Adapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new Adapter();
        adapter.setPlusClickListener(this);
        adapter.setMinusClickListener(this);
        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);
        viewModel.getFoodIstances(Frequency.DAILY).observe(requireActivity(), adapter::submitData);
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

    @Override
    public void onPlusClick(FoodInstance foodInstance) {
        viewModel.update(foodInstance);
    }

    @Override
    public void onMinusClick(FoodInstance foodInstance) {
        viewModel.update(foodInstance);
    }
}