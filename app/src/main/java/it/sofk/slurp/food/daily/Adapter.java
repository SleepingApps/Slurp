package it.sofk.slurp.food.daily;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.sofk.slurp.R;
import it.sofk.slurp.database.entity.FoodIstance;
import it.sofk.slurp.databinding.DailyItemBinding;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private final AsyncListDiffer<FoodIstance> listDiffer = new AsyncListDiffer(this, new DiffUtil.ItemCallback<FoodIstance>() {
        @Override
        public boolean areItemsTheSame(@NonNull FoodIstance oldItem, @NonNull FoodIstance newItem) {
            return oldItem.areItemsTheSame(newItem);
        }
        @Override
        public boolean areContentsTheSame(@NonNull FoodIstance oldItem, @NonNull FoodIstance newItem) {
            return oldItem.areContentsTheSame(newItem);
        }
    });

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DailyItemBinding binding = DailyItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        FoodIstance instance = listDiffer.getCurrentList().get(position);
        holder.binding.dailyFoodname.setText(instance.getFoodType());
        holder.binding.ratingBar.setNumStars(instance.getPortionConsumed());
        holder.binding.dailyMinusbutton.setOnClickListener((View) -> {
            instance.setPortionConsumed(instance.getPortionConsumed() - 0.5);
        });
        holder.binding.dailyPlusbutton.setOnClickListener((View) -> {
            instance.setPortionConsumed(instance.getPortionConsumed() + 0.5);
        });
    }

    @Override
    public int getItemCount() {
        return listDiffer.getCurrentList().size();
    }

    public void submitData(List<FoodIstance> data) {
        listDiffer.submitList(data);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final DailyItemBinding binding;

        public ViewHolder(@NonNull DailyItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
