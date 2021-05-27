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

import java.util.Arrays;
import java.util.List;

import it.sofk.slurp.R;
import it.sofk.slurp.database.entity.FoodInstance;
import it.sofk.slurp.database.entity.FoodType;
import it.sofk.slurp.databinding.DailyItemBinding;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private final AsyncListDiffer<FoodInstance> listDiffer = new AsyncListDiffer(this, new DiffUtil.ItemCallback<FoodInstance>() {
        @Override
        public boolean areItemsTheSame(@NonNull FoodInstance oldItem, @NonNull FoodInstance newItem) {
            return oldItem == newItem;
        }
        @Override
        public boolean areContentsTheSame(@NonNull FoodInstance oldItem, @NonNull FoodInstance newItem) {
            return oldItem.equals(newItem);
        }
    });

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DailyItemBinding binding = DailyItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodInstance instance = listDiffer.getCurrentList().get(position);
        holder.binding.dailyFoodname.setText(instance.getFoodType());
        holder.binding.ratingBar.setRating((float)instance.getPortionConsumed());

        holder.binding.dailyMinusbutton.setOnClickListener((View) ->
                instance.setPortionConsumed(instance.getPortionConsumed() - 0.5));

        holder.binding.dailyPlusbutton.setOnClickListener((View) ->
                instance.setPortionConsumed(instance.getPortionConsumed() + 0.5));
    }

    @Override
    public int getItemCount() {
        return listDiffer.getCurrentList().size();
    }

    public void submitData(List<FoodInstance> data) {
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
