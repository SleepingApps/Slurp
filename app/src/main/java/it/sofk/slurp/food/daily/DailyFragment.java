package it.sofk.slurp.food.daily;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.sofk.slurp.R;

public class DailyFragment extends Fragment {

    Holder holder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily, container, false);

        holder = new Holder(view);
        return view;
    }

    class Holder {

        RecyclerView recyclerView;

        Holder(View view){
            recyclerView = view.findViewById(R.id.daily_recyclerview);
        }
    }
}