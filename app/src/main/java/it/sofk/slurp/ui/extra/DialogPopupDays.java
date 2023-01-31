package it.sofk.slurp.ui.extra;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.time.LocalDate;
import java.util.List;

import it.sofk.slurp.R;
import it.sofk.slurp.databinding.PopupDaysBinding;
import it.sofk.slurp.ui.adapters.ChooseDayAdapter;
import it.sofk.slurp.ui.callback.AdapterDialogCallBack;
import it.sofk.slurp.ui.callback.DialogFoodFragmentCallBack;

public class DialogPopupDays extends DialogFragment implements AdapterDialogCallBack {

    private List<LocalDate> days;
    private PopupDaysBinding binding;
    private DialogFoodFragmentCallBack callBack;

    public DialogPopupDays(@NonNull Context context, List<LocalDate> days, DialogFoodFragmentCallBack callback) {
        //assert days.size() == 0;
        this.callBack = callback;
        this.days = days;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = PopupDaysBinding.inflate(inflater);

        binding.recyclerDays.setLayoutManager(new LinearLayoutManager(getContext()));

        ChooseDayAdapter chooseDayAdapter = new ChooseDayAdapter(ContextCompat.getColor(getContext(), R.color.background_popup_item_today), this);
        binding.recyclerDays.setAdapter(chooseDayAdapter);
        chooseDayAdapter.submitData(days);

        return binding.getRoot();
    }


    @Override
    public void onSelectedDayFromAdapter(LocalDate day, int number) {
        this.dismiss();
        callBack.onSelectedDayFromDialog(day, number);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        //devi risolvere questa riga che non funziona

    }

}
