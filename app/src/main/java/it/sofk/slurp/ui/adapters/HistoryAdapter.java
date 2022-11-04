package it.sofk.slurp.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.sofk.slurp.databinding.HistoryItemBinding;
import it.sofk.slurp.dto.WeekListItem;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{

    private final AsyncListDiffer<WeekListItem> listDiffer = new AsyncListDiffer(this, new DiffUtil.ItemCallback<WeekListItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull WeekListItem oldItem, @NonNull WeekListItem newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull WeekListItem oldItem, @NonNull WeekListItem newItem) {
            return oldItem.getStartDate().equals(newItem.getStartDate())
                    && oldItem.getEndDate().equals(newItem.getEndDate());
        }
    });

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HistoryItemBinding binding = HistoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        WeekListItem week = listDiffer.getCurrentList().get(position);
        holder.binding.textStartDate.setText(week.getStartDate().toString());
        holder.binding.textEndDate.setText(week.getEndDate().toString());
    }

    public void submitData(List<WeekListItem> data) {
        listDiffer.submitList(data);
    }

    @Override
    public int getItemCount() {
        return listDiffer.getCurrentList().size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        public final HistoryItemBinding binding;

        public ViewHolder(@NonNull HistoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
