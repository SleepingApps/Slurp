package it.sofk.slurp.ui.adapters;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.sofk.slurp.databinding.FoodItemBinding;
import it.sofk.slurp.dto.ExampleDTO;
import it.sofk.slurp.dto.FoodDTO;
import it.sofk.slurp.ui.extra.FoodHelper;
import it.sofk.slurp.ui.extra.FoodItemResizer;
import it.sofk.slurp.ui.extra.FoodPortion;

public class DailyFragmentAdapter extends RecyclerView.Adapter<DailyFragmentAdapter.ViewHolder> {

    private ClickListener clickListener;

    private final AsyncListDiffer<FoodDTO> foodList = new AsyncListDiffer(this,
            new DiffUtil.ItemCallback<FoodDTO>() {
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

    private final AsyncListDiffer<ExampleDTO> examplesList = new AsyncListDiffer(this,
            new DiffUtil.ItemCallback<ExampleDTO>() {
        @Override
        public boolean areItemsTheSame(@NonNull ExampleDTO oldItem, @NonNull ExampleDTO newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ExampleDTO oldItem, @NonNull ExampleDTO newItem) {
            return oldItem.getExample().equals(newItem.getExample());
        }
    });

    @NonNull
    @Override
    public DailyFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FoodItemBinding binding = FoodItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodDTO food = foodList.getCurrentList().get(position);
        FoodHelper foodHelper = FoodHelper.GetFoodHelper(food.getName());

        holder.binding.foodItemTitle.setText(food.getName());
        holder.binding.foodDesc.setText(Html.fromHtml(FoodPortion.CreateAndGetDescription(food, examplesList.getCurrentList()), Html.FROM_HTML_MODE_COMPACT));

        holder.binding.foodimg.setBackgroundResource(foodHelper.image);
        holder.binding.ellipse.setPaint(foodHelper.color);
        holder.binding.eatenPortions.setText(String.valueOf(food.getEatenPortions()));
        holder.binding.maxPortions.setText("/" + food.getMaxPortions());

        if (holder.resizer.isExpanded()) {
            int completeSize = holder.resizer.getItemCompleteSize();
            holder.binding.getRoot().getLayoutParams().height = completeSize;
        }

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
        return foodList.getCurrentList().size();
    }

    public void submitFood(List<FoodDTO> data) {
        foodList.submitList(data);
    }

    public void submitExamples(List<ExampleDTO> data) {
        examplesList.submitList(data);
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