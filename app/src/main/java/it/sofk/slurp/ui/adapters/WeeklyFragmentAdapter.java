package it.sofk.slurp.ui.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.sofk.slurp.database.ViewModel;
import it.sofk.slurp.databinding.FoodItemBinding;
import it.sofk.slurp.dto.FoodDTO;
import it.sofk.slurp.ui.extra.FoodHelper;
import it.sofk.slurp.ui.extra.FoodItemResizer;
import it.sofk.slurp.ui.fragments.WeeklyFragment;

public class WeeklyFragmentAdapter extends RecyclerView.Adapter<WeeklyFragmentAdapter.ViewHolder> {

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

    @NonNull
    @Override
    public WeeklyFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FoodItemBinding binding = FoodItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<FoodDTO> list = listDiffer.getCurrentList();
        FoodDTO food = list.get(position);

        if (holder.getAdapterPosition() == 0) {
            holder.setIsRecyclable(false);
            holder.binding.getRoot().setVisibility(View.INVISIBLE);
            holder.binding.getRoot().setMaxHeight(103);
            return;
        }

        FoodHelper foodHelper = FoodHelper.GetFoodHelper(food.getName());

        holder.binding.foodItemTitle.setText(food.getName());
        if (foodHelper != null) {
            holder.binding.foodimg.setBackgroundResource(foodHelper.image);
            holder.binding.ellipse.setPaint(foodHelper.color);
        }
        holder.binding.eatenPortions.setText(String.valueOf(food.getEatenPortions()));
        holder.binding.maxPortions.setText("/" + food.getMaxPortions());

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
        data.add(0, WeeklyFragment.spaceHolder);
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