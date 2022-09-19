package it.sofk.slurp.ui.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import it.sofk.slurp.databinding.FoodItemBinding;
import it.sofk.slurp.dto.FoodDTO;
import it.sofk.slurp.ui.extra.FoodItemResizer;

public class OccasionallyFragmentAdapter extends RecyclerView.Adapter<OccasionallyFragmentAdapter.ViewHolder> {

    private final Activity activity;

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

    public OccasionallyFragmentAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public OccasionallyFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FoodItemBinding binding = FoodItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodDTO food = listDiffer.getCurrentList().get(position);
        if (position == 0) {
            holder.binding.getRoot().setVisibility(View.INVISIBLE);
            holder.binding.getRoot().setMaxHeight(102);
            return;
        }

        holder.binding.foodItemTitle.setText(food.getName());
        holder.binding.eatenPortions.setText(String.valueOf(food.getEatenPortions()));
        Objects.requireNonNull(holder).binding.maxPortions.setText("/" + food.getMaxPortions());

        holder.binding.getRoot().setOnClickListener((View) -> {
            if (holder.resizer.isExpanded())
                holder.resizer.shrink();
            else
                holder.resizer.expand();
        });

        holder.binding.foodItemPlus.setOnClickListener((View) -> {
            FoodDTO newFood = new FoodDTO(food.getName(),
                    food.getEatenPortions() + 0.5,
                    food.getMaxPortions(), food.getDate());

            if (clickListener != null) clickListener.onPlusClick(newFood);
        });

        holder.binding.foodItemMinus.setOnClickListener((View) -> {
            if (food.getEatenPortions() == 0) return;

            FoodDTO newFood = new FoodDTO(food.getName(),
                    food.getEatenPortions() - 0.5,
                    food.getMaxPortions(), food.getDate());

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
        public final FoodItemResizer resizer;

        public ViewHolder(@NonNull FoodItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.resizer = new FoodItemResizer(binding);
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
