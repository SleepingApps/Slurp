package it.sofk.slurp.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import it.sofk.slurp.database.Repository;
import it.sofk.slurp.dto.WeekListItem;

public class HistoryViewModel extends AndroidViewModel {

    private Repository repository;

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public LiveData<List<WeekListItem>> getWeeks(){
        return repository.getWeeks();
    }
}
