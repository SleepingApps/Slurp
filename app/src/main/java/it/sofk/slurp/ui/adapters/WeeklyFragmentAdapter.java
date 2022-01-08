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
import it.sofk.slurp.dto.FoodDTO;

public class WeeklyFragmentAdapter extends RecyclerView.Adapter<WeeklyFragmentAdapter.ViewHolder> {

    private final int foregroundColor;
    private final int backgroundColor;

    private ClickListener clickListener;

    private final AsyncListDiffer<FoodDTO> listDiffer = new AsyncListDiffer(this, new DiffUtil.ItemCallback<FoodDTO>() {
        @Override
        public boolean areItemsTheSame(@NonNull FoodDTO oldItem, @NonNull FoodDTO newItem) {
            return oldItem.getName().equals(newItem.getName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull FoodDTO oldItem, @NonNull FoodDTO newItem) {
            return oldItem.getMaxPortions() == newItem.getMaxPortions()
                    && oldItem.getEatenPortions() == newItem.getEatenPortions();
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
        FoodDTO food = listDiffer.getCurrentList().get(position);


        holder.binding.foodItemTitle.setText(food.getName());
        holder.binding.eatenPortions.setText(String.valueOf(food.getEatenPortions()));
        holder.binding.maxPortions.setText("/" + food.getMaxPortions());

        holder.binding.progressCircle.setColors(foregroundColor, backgroundColor);
        double progress = 360.0 / 5.0 * food.getEatenPortions();
        holder.binding.progressCircle.setProgress(progress);

        holder.binding.foodItemPlus.setOnClickListener((View) -> {
            FoodDTO newFood = new FoodDTO(food.getName(),
                    food.getEatenPortions() + 0.5,
                    food.getMaxPortions());

            double newProgress = 360.0 / 5.0 * food.getEatenPortions();
            holder.binding.progressCircle.setProgress(newProgress);

            if (clickListener != null) clickListener.onPlusClick(newFood);
        });

        holder.binding.foodItemMinus.setOnClickListener((View) -> {
            if (food.getEatenPortions() == 0) return;

            FoodDTO newFood = new FoodDTO(food.getName(),
                    food.getEatenPortions() - 0.5,
                    food.getMaxPortions());

            double newProgress = 360.0 / food.getMaxPortions() * food.getEatenPortions();
            holder.binding.progressCircle.setProgress(newProgress);

            if (clickListener != null) clickListener.onMinusClick(newFood);
        });
    }

    @Override
    public int getItemCount() {
        return listDiffer.getCurrentList().size();
    }

    public void submitData(List<FoodDTO> data) {
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
        void onPlusClick(FoodDTO food);
        void onMinusClick(FoodDTO food);
    }
}
