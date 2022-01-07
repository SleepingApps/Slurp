package it.sofk.slurp.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.sofk.slurp.database.entity.FoodInstance;
import it.sofk.slurp.databinding.FoodItemBinding;

public class WeeklyFragmentAdapter extends RecyclerView.Adapter<WeeklyFragmentAdapter.ViewHolder> {

    private final int foregroundColor;
    private final int backgroundColor;

    private ClickListener clickListener;

    private final AsyncListDiffer<FoodInstance> listDiffer = new AsyncListDiffer(this, new DiffUtil.ItemCallback<FoodInstance>() {
        @Override
        public boolean areItemsTheSame(@NonNull FoodInstance oldItem, @NonNull FoodInstance newItem) {
            return oldItem.getDate().equals(newItem.getDate()) && oldItem.getFoodType().equals(newItem.getFoodType());
        }

        @Override
        public boolean areContentsTheSame(@NonNull FoodInstance oldItem, @NonNull FoodInstance newItem) {
            return oldItem.getFoodType().equals(newItem.getFoodType())
                    && oldItem.getDate().equals(newItem.getDate());
        }
    });

    public WeeklyFragmentAdapter(int foregroundColor, int backgroundColor) {
        this.foregroundColor = foregroundColor;
        this.backgroundColor = backgroundColor;
    }

    @NonNull
    @Override
    public WeeklyFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FoodItemBinding binding = FoodItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodInstance foodInstance = listDiffer.getCurrentList().get(position);

        holder.binding.progressCircle.setColors(foregroundColor, backgroundColor);
        holder.binding.foodItemTitle.setText(foodInstance.getFoodType().toUpperCase());

        double progress = 360.0 / 5.0 * foodInstance.getPortionConsumed();
        holder.binding.progressCircle.setProgress(progress);

        holder.binding.foodItemPlus.setOnClickListener((View) -> {
            foodInstance.setPortionConsumed(foodInstance.getPortionConsumed() + 0.5);
            double newProgress = 360.0 / 5.0 * foodInstance.getPortionConsumed();
            holder.binding.progressCircle.setProgress(newProgress);
            holder.binding.eatenPortions.setText(String.valueOf(foodInstance.getPortionConsumed()));
            holder.binding.maxPortions.setText("/" + foodInstance.getPortionConsumed());

            if (clickListener != null) clickListener.onPlusClick(foodInstance);
        });

        holder.binding.foodItemMinus.setOnClickListener((View) -> {
            if (foodInstance.getPortionConsumed() == 0) return;

            foodInstance.setPortionConsumed(foodInstance.getPortionConsumed() - 0.5);
            double newProgress = 360.0 / 5.0 * foodInstance.getPortionConsumed();
            holder.binding.progressCircle.setProgress(newProgress);
            holder.binding.eatenPortions.setText(String.valueOf(foodInstance.getPortionConsumed()));
            holder.binding.maxPortions.setText("/" + foodInstance.getPortionConsumed());

            if (clickListener != null) clickListener.onMinusClick(foodInstance);
        });
    }

    @Override
    public int getItemCount() {
        return listDiffer.getCurrentList().size();
    }

    public void submitData(List<FoodInstance> data) {
        listDiffer.submitList(data);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final FoodItemBinding binding;

        public ViewHolder(@NonNull FoodItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void onPlusClick(FoodInstance foodInstance);
        void onMinusClick(FoodInstance foodInstance);
    }
}
