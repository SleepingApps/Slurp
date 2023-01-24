package it.sofk.slurp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.List;

import it.sofk.slurp.databinding.PopupItemBinding;
import it.sofk.slurp.ui.callback.AdapterDialogCallBack;

public class ChooseDayAdapter extends RecyclerView.Adapter<ChooseDayAdapter.ViewHolder> {

    private final AsyncListDiffer<LocalDate> listDiffer = new AsyncListDiffer(this, new DiffUtil.ItemCallback<LocalDate>() {
        @Override
        public boolean areItemsTheSame(@NonNull LocalDate oldItem, @NonNull LocalDate newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull LocalDate oldItem, @NonNull LocalDate newItem) {
            return oldItem.isEqual(newItem);
        }
    });

    private int color;

    private AdapterDialogCallBack callBack;

    public ChooseDayAdapter(int color, AdapterDialogCallBack callBack) {
        this.callBack = callBack;
        this.color = color;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PopupItemBinding binding = PopupItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LocalDate day = listDiffer.getCurrentList().get(position);
        if(day.isEqual(LocalDate.now())){
            holder.binding.layoutDay.getBackground().setTint(color);
        }

        holder.binding.tvDayNumber.setText("Giorno " + (position + 1));
        holder.binding.tvDayDate.setText(day.toString());
    }

    public void submitData(List<LocalDate> data) {
        listDiffer.submitList(data);
    }

    @Override
    public int getItemCount() {
        return listDiffer.getCurrentList().size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final PopupItemBinding binding;

        public ViewHolder(@NonNull PopupItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.layoutDay.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            LocalDate day = listDiffer.getCurrentList().get(this.getAdapterPosition());
            callBack.onSelectedDayFromAdapter(day);
        }
    }

}
